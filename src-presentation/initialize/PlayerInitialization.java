package initialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import game.GameColor;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerController;
import game.player.PlayerInteraction;
import initialize.player.AbstractPlayerFactory;
import initialize.player.ContingencyPlannerFactory;
import initialize.player.DispatcherFactory;
import initialize.player.MedicFactory;
import initialize.player.OperationExpertFactory;
import initialize.player.QuarantineSpecialistFactory;
import initialize.player.ResearcherFactory;
import initialize.player.ScientistFactory;

public class PlayerInitialization {

	private int nextID;
	private QuarantineSpecialistFactory quarantineSpecialistFactory;
	private List<AbstractPlayerFactory> allFactories;

	public PlayerInitialization(City startingCity, PlayerInteraction interaction, Deck playerDiscard, CitySet citySet,
			Set<GameColor> curedDiseases, List<Player> players) {
		nextID = 0;
		createFactoryList();
		for (AbstractPlayerFactory factory : allFactories) {
			factory.initializeFactory(startingCity, interaction, playerDiscard, citySet, curedDiseases, players);
		}

	}

	private void createFactoryList() {
		allFactories = new ArrayList<>();
		quarantineSpecialistFactory = new QuarantineSpecialistFactory(nextID());
		allFactories.add(quarantineSpecialistFactory);
		allFactories.add(new ContingencyPlannerFactory(nextID()));
		allFactories.add(new DispatcherFactory(nextID()));
		allFactories.add(new MedicFactory(nextID()));
		allFactories.add(new OperationExpertFactory(nextID()));
		allFactories.add(new ResearcherFactory(nextID()));
		allFactories.add(new ScientistFactory(nextID()));
	}

	private int nextID() {
		return nextID++;
	}

	public Predicate<City> getQuanrantineChecker() {
		return quarantineSpecialistFactory.getQuarantineChecker();
	}

	public PlayerController[] createPlayersWithRandomRoles(int playerCount) {
		Collections.shuffle(allFactories);
		List<AbstractPlayerFactory> selectedFactories = selectFactories(playerCount);
		selectedFactories.forEach(AbstractPlayerFactory::initializePlayer);
		selectedFactories.forEach(AbstractPlayerFactory::initializeSpecialSkill);

		return createControllersFromFactories(selectedFactories);
	}

	private List<AbstractPlayerFactory> selectFactories(int playerCount) {
		List<AbstractPlayerFactory> selectedFactories = new ArrayList<>();
		Iterator<AbstractPlayerFactory> iter = allFactories.iterator();
		for (int i = 0; i < playerCount; i++) {
			selectedFactories.add(iter.next());
		}
		return selectedFactories;
	}

	private PlayerController[] createControllersFromFactories(List<AbstractPlayerFactory> selectedFactories) {
		PlayerController[] controllers = new PlayerController[selectedFactories.size()];
		Iterator<AbstractPlayerFactory> iter = selectedFactories.iterator();
		for (int i = 0; i < controllers.length; i++) {
			controllers[i] = iter.next().createPlayerController();
		}
		return controllers;
	}

}
