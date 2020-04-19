package initialize.player;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.GameProperty;
import game.ActionType;
import game.GameColor;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerController;
import game.player.PlayerImpl;
import game.player.PlayerInteraction;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionBuildStation;
import game.player.action.ActionCharterFlight;
import game.player.action.ActionDirectFlight;
import game.player.action.ActionDiscoverCure;
import game.player.action.ActionDrive;
import game.player.action.ActionEventCard;
import game.player.action.ActionGiveKnowledge;
import game.player.action.ActionShuttleFlight;
import game.player.action.ActionTakeKnowledge;
import game.player.action.ActionTreatDisease;

public abstract class AbstractPlayerFactory {

	private City startingCity;
	protected PlayerInteraction interaction;
	protected Deck playerDiscard;
	protected CitySet citySet;
	protected Set<GameColor> curedDiseases;
	protected List<Player> players;

	private Player createdPlayer;
	private Map<ActionType, Action> actionMap;

	public final void initializeFactory(City startingCity, PlayerInteraction interaction, Deck playerDiscard,
			CitySet citySet, Set<GameColor> curedDiseases, List<Player> players) {
		this.startingCity = startingCity;
		this.interaction = interaction;
		this.playerDiscard = playerDiscard;
		this.citySet = citySet;
		this.curedDiseases = curedDiseases;
		this.players = players;
	}

	public final void initializePlayer() {
		createdPlayer = createPlayer();
		actionMap = createActionMapTemplate(createdPlayer);
		players.add(createdPlayer);
	}

	public final void initializeSpecialSkill() {
		addSpecialSkill(createdPlayer, actionMap);
	}

	public final PlayerController createPlayerController() {
		return new PlayerController(createdPlayer, actionMap);
	}

	protected abstract PlayerRole getRole();

	protected Player createPlayer() {
		return createBasicPlayer();
	}

	protected abstract void addSpecialSkill(Player player, Map<ActionType, Action> actionMap);

	protected final Player createBasicPlayer() {
		return new PlayerImpl(getRole(), startingCity, playerDiscard, interaction);
	}

	private Map<ActionType, Action> createActionMapTemplate(Player player) {
		Map<ActionType, Action> actionMap = new EnumMap<>(ActionType.class);
		actionMap.put(ActionType.DRIVE, new ActionDrive(citySet, player, interaction));
		actionMap.put(ActionType.DIRECT_FLIGHT, new ActionDirectFlight(player, interaction));
		actionMap.put(ActionType.CHARTER_FLIGHT, new ActionCharterFlight(citySet, player, interaction));
		actionMap.put(ActionType.SHUTTLE_FLIGHT, new ActionShuttleFlight(citySet, player, interaction));
		actionMap.put(ActionType.BUILD_STATION,
				new ActionBuildStation(player, interaction, /* Need Card= */true, citySet));
		actionMap.put(ActionType.TREAT_DISEASE, new ActionTreatDisease(player, interaction, curedDiseases));
		actionMap.put(ActionType.TAKE_KNOWLEDGE, new ActionTakeKnowledge(player, interaction, players));
		actionMap.put(ActionType.GIVE_KNOWLEDGE, new ActionGiveKnowledge(player, interaction, players));
		final int DISCOVER_CURE_CARDS = GameProperty.getInstance().getInt("DISCOVER_CURE_CARDS");
		actionMap.put(ActionType.DISCOVER_CURE,
				new ActionDiscoverCure(player, interaction, curedDiseases, DISCOVER_CURE_CARDS));
		actionMap.put(ActionType.EVENT, new ActionEventCard(interaction, players));
		return actionMap;
	}
}
