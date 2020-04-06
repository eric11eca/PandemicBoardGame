package initialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import data.GameProperty;
import game.ActionType;
import game.GameColor;
import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerController;
import game.player.PlayerImpl;
import game.player.PlayerInteraction;
import game.player.action.Action;
import game.player.action.ActionBuildStation;
import game.player.action.ActionCharterFlight;
import game.player.action.ActionDirectFlight;
import game.player.action.ActionDiscoverCure;
import game.player.action.ActionDispatcherMove;
import game.player.action.ActionDrive;
import game.player.action.ActionEventCard;
import game.player.action.ActionGiveKnowledge;
import game.player.action.ActionShuttleFlight;
import game.player.action.ActionSkillContingencyPlanner;
import game.player.action.ActionSkillDispatcher;
import game.player.action.ActionSkillOperationsExpert;
import game.player.action.ActionTakeKnowledge;
import game.player.action.ActionTreatDisease;
import game.player.action.ActionTreatDiseaseMedic;
import game.player.special.ContingencyPlanner;
import game.player.special.DispatchedPlayer;
import game.player.special.Medic;
import game.player.special.QuarantineChecker;
import game.player.special.Researcher;

public class PlayerFactory {
	interface PlayerControllerInitializer extends Supplier<PlayerController> {
	}

	interface PlayerControllerInitializerFactory extends Supplier<PlayerControllerInitializer> {
	}

	private City startingCity;
	private PlayerInteraction interaction;
	private Deck<Card> playerDiscard;
	private CitySet citySet;
	private Set<GameColor> curedDiseases;
	private List<Player> players;

	public PlayerFactory(City startingCity, PlayerInteraction interaction, Deck<Card> playerDiscard, CitySet citySet,
			Set<GameColor> curedDiseases, List<Player> players) {
		super();
		this.startingCity = startingCity;
		this.interaction = interaction;
		this.playerDiscard = playerDiscard;
		this.citySet = citySet;
		this.curedDiseases = curedDiseases;
		this.players = players;
		init();
	}

	private int id;
	private boolean controllerInstantiated;
	private Predicate<City> quarantineChecker;

	private void init() {
		id = 0;
		controllerInstantiated = false;
		quarantineChecker = c -> false;
	}

	private int nextID() {
		return id++;
	}

	public Predicate<City> getQuanrantineChecker() {
		return quarantineChecker;
	}

	public PlayerController[] createPlayersWithRandomRoles(int playerCount) {
		List<PlayerControllerInitializerFactory> factoryMethods = getFactoriesForAllRolesInRandomOrder();
		List<PlayerControllerInitializer> inGameRoles = createPlayerControllerInitializerWithCount(playerCount,
				factoryMethods);
		return instantiatePlayerControllers(inGameRoles);
	}

	private List<PlayerControllerInitializer> createPlayerControllerInitializerWithCount(int playerCount,
			List<PlayerControllerInitializerFactory> factoryMethods) {
		List<PlayerControllerInitializer> inGameRoles = new ArrayList<>();
		for (int i = 0; i < playerCount; i++) {
			inGameRoles.add(factoryMethods.get(i).get());
		}
		return inGameRoles;
	}

	private List<PlayerControllerInitializerFactory> getFactoriesForAllRolesInRandomOrder() {
		List<PlayerControllerInitializerFactory> factoryMethods = new ArrayList<>();
		factoryMethods.add(this::createScientistFactory);
		factoryMethods.add(this::createMedicFactory);
		factoryMethods.add(this::createContingencyPlannerFactory);
		factoryMethods.add(this::createDispatcherFactory);
		factoryMethods.add(this::createOperationsExpertFactory);
		factoryMethods.add(this::createQuarantineSpecialistFactory);
		factoryMethods.add(this::createResearcherFactory);
		Collections.shuffle(factoryMethods);
		return factoryMethods;
	}

	private PlayerController[] instantiatePlayerControllers(List<PlayerControllerInitializer> initializers) {
		PlayerController[] controllers = new PlayerController[initializers.size()];
		int i = 0;
		for (Supplier<PlayerController> factory : initializers) {
			controllers[i] = factory.get();
		}
		return controllers;
	}

	private PlayerControllerInitializer createScientistFactory() {
		return createPlayerHelper(this::createPlayer, this::addSpecialSkillScientist);
	}

	private PlayerControllerInitializer createMedicFactory() {
		return createPlayerHelper(this::createPlayerMedic, this::addSpecialSkillMedic);
	}

	private PlayerControllerInitializer createContingencyPlannerFactory() {
		return createPlayerHelper(this::createPlayerContingencyPlanner, this::addSpecialSkillContingencyPlanner);
	}

	private PlayerControllerInitializer createDispatcherFactory() {
		return createPlayerHelper(this::createPlayer, this::addSpecialSkillDispatcher);
	}

	private PlayerControllerInitializer createOperationsExpertFactory() {
		return createPlayerHelper(this::createPlayer, this::addSpecialSkillOperationsExpert);
	}

	private PlayerControllerInitializer createQuarantineSpecialistFactory() {
		return createPlayerHelper(this::createPlayer, this::addSpecialSkillQuarantineSpecialist);
	}

	private PlayerControllerInitializer createResearcherFactory() {
		return createPlayerHelper(this::createPlayerResearcher, (p, a) -> {
		});
	}

	private PlayerControllerInitializer createPlayerHelper(Supplier<Player> playerMethod,
			BiConsumer<Player, Map<ActionType, Action>> specialActionMethod) {
		assert !controllerInstantiated;
		final Player player = playerMethod.get();
		final Map<ActionType, Action> actionMap = createActionMapTemplate(player);
		players.add(player);

		return () -> {
			specialActionMethod.accept(player, actionMap);
			controllerInstantiated = true;
			return new PlayerController(player, actionMap);
		};
	}

	private void addSpecialSkillScientist(Player player, Map<ActionType, Action> actionMap) {
		final int DISCOVER_CURE_CARDS = GameProperty.getInstance().getInt("DISCOVER_CURE_CARDS_SCIENTIST");
		actionMap.put(ActionType.DISCOVER_CURE,
				new ActionDiscoverCure(player, interaction, curedDiseases, DISCOVER_CURE_CARDS));
	}

	private void addSpecialSkillMedic(Player player, Map<ActionType, Action> actionMap) {
		actionMap.put(ActionType.TREAT_DISEASE, new ActionTreatDiseaseMedic(player, interaction));
	}

	private void addSpecialSkillContingencyPlanner(Player player, Map<ActionType, Action> actionMap) {
		actionMap.put(ActionType.SPECIAL_SKILL,
				new ActionSkillContingencyPlanner((ContingencyPlanner) player, interaction, playerDiscard));
	}

	private void addSpecialSkillDispatcher(Player dispatcher, Map<ActionType, Action> actionMap) {
		Map<Player, Action> dispatchedDriveActions = new HashMap<>();
		Map<Player, Action> dispatchedDirectFlightActions = new HashMap<>();
		Map<Player, Action> dispatchedCharterFlightActions = new HashMap<>();
		Map<Player, Action> dispatchedShuttleFlightActions = new HashMap<>();
		List<Player> allPlayers = new ArrayList<>();
		allPlayers.addAll(players);
		allPlayers.add(dispatcher);
		allPlayers.forEach(player -> {
			Player dispatchedPlayer = new DispatchedPlayer(dispatcher, player);
			dispatchedDriveActions.put(player, new ActionDrive(citySet, dispatchedPlayer, interaction));
			dispatchedDirectFlightActions.put(player, new ActionDirectFlight(dispatchedPlayer, interaction));
			dispatchedCharterFlightActions.put(player, new ActionCharterFlight(citySet, dispatchedPlayer, interaction));
			dispatchedShuttleFlightActions.put(player, new ActionShuttleFlight(citySet, dispatchedPlayer, interaction));
		});
		actionMap.put(ActionType.DRIVE, new ActionDispatcherMove(dispatchedDriveActions, interaction));
		actionMap.put(ActionType.DIRECT_FLIGHT, new ActionDispatcherMove(dispatchedDirectFlightActions, interaction));
		actionMap.put(ActionType.CHARTER_FLIGHT, new ActionDispatcherMove(dispatchedCharterFlightActions, interaction));
		actionMap.put(ActionType.SHUTTLE_FLIGHT, new ActionDispatcherMove(dispatchedShuttleFlightActions, interaction));

		actionMap.put(ActionType.SPECIAL_SKILL, new ActionSkillDispatcher(allPlayers, interaction));
	}

	private void addSpecialSkillOperationsExpert(Player player, Map<ActionType, Action> actionMap) {
		actionMap.put(ActionType.BUILD_STATION,
				new ActionBuildStation(player, interaction, /* Need Card= */false, citySet));
		actionMap.put(ActionType.SPECIAL_SKILL, new ActionSkillOperationsExpert(player, interaction, citySet));
	}

	private void addSpecialSkillQuarantineSpecialist(Player player, Map<ActionType, Action> actionMap) {
		this.quarantineChecker = new QuarantineChecker(player);
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

	private Player createPlayerResearcher() {
		return new Researcher(createPlayer());
	}

	private Player createPlayerMedic() {
		return new Medic(createPlayer(), curedDiseases);
	}

	private Player createPlayerContingencyPlanner() {
		return new ContingencyPlanner(createPlayer());
	}

	private Player createPlayer() {
		return new PlayerImpl(nextID(), startingCity, playerDiscard, interaction);
	}

}
