package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import cards.PlayerCard;
import data.Board;
import game.city.City;
import gui.GameGUI;

public class EventCardListener implements ActionListener {
	JComboBox<String> cardList;

	private Board board;
	private JComboBox<String> eventCards;
	private GameGUI gui;
	private JPanel panel;

	public EventCardListener(Board board, JComboBox<String> eventCards, GameGUI gui) {
		this.board = board;
		this.eventCards = eventCards;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String card = eventCards.getSelectedItem().toString();
		for (int i = 0; i < board.currentPlayers.size(); i++) {
			Map<String, PlayerCard> playerHand = board.currentPlayers.get(i).playerData.hand;
			if (playerHand.keySet().contains(card)) {
				if (card.equals(board.messages.getString("Airlift"))) {
					performAirlift(i);
				} else if (card.equals(board.messages.getString("OneQuietNight"))) {
					performOneQuietNight(i);
				} else if (card.equals(board.messages.getString("ResilientPopulation"))) {
					performResilientPopulation(i);
				} else if (card.equals(board.messages.getString("GovernmentGrant"))) {
					performGovernmentGrant(i);
				} else {
					performForecast(i);
				}
				// gui.updateImage();
				break;
			}
		}

	}

	private void performForecast(int playerIndex) {
		String[] cards = new String[6];
		for (int i = 0; i < 6; i++) {
			cards[i] = board.validInfectionCards.get(i);
			board.validInfectionCards.remove(i);
		}
		ArrayList<String> orderedCards = new ArrayList<String>();
		cardList = new JComboBox<String>(cards);
		cardList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				orderedCards.add(0, cardList.getSelectedItem().toString());
				cardList.removeItem(cardList.getSelectedItem());
				if (cardList.getItemCount() == 0) {
					board.rearrangeInstruction = orderedCards;
					// TODO broken
					// board.currentPlayers.get(playerIndex).useEventCard(board.messages.getString("Forecast"));
					// gui.removePanel(panel);
					// gui.updateImage();
				}
			}
		});

		JButton button = new JButton(board.messages.getString("Clear"));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				orderedCards.clear();
				cardList.removeAll();
				cardList.setModel((ComboBoxModel<String>) new DefaultComboBoxModel<String>(cards));
			}

		});

		panel = new JPanel();
		panel.add(cardList);
		panel.add(button);
		// gui.addPanel(panel, BorderLayout.CENTER);

	}

	private void performGovernmentGrant(int playerIndex) {
		ArrayList<String> citiesWithoutStations = new ArrayList<String>();
		for (String cityName : board.cities.keySet()) {
			if (!board.currentResearchStation.containsKey(cityName)) {
				citiesWithoutStations.add(cityName);
			}
		}
		String[] options = citiesWithoutStations.toArray(new String[citiesWithoutStations.size()]);
		JComboBox<String> listOfCities = new JComboBox<String>(options);
		listOfCities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.cityWithGrant = listOfCities.getSelectedItem().toString();
				// TODO broken
				// board.currentPlayers.get(playerIndex).useEventCard(board.messages.getString("GovernmentGrant"));
				// gui.removePanel(panel);
				// gui.updateImage();
			}

		});
		panel = new JPanel();
		panel.add(listOfCities);
		// gui.addPanel(panel, BorderLayout.CENTER);
	}

	private void performResilientPopulation(int playerIndex) {
		String[] infections = board.discardInfectionCards.toArray(new String[board.discardInfectionCards.size()]);
		JComboBox<String> listOfInfections = new JComboBox<String>(infections);
		listOfInfections.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.cardRemovedByResilient = listOfInfections.getSelectedItem().toString();
				// TODO broken
				// board.currentPlayers.get(playerIndex).useEventCard(board.messages.getString("ResilientPopulation"));
				// gui.removePanel(panel);
				// gui.updateImage();
			}

		});
		panel = new JPanel();
		panel.add(listOfInfections);
		// gui.addPanel(panel, BorderLayout.CENTER);
	}

	private void performOneQuietNight(int playerIndex) {
		// TODO broken
		// board.currentPlayers.get(playerIndex).useEventCard(board.messages.getString("OneQuietNight"));
	}

	private void performAirlift(int playerIndex) {
		ArrayList<String> cities = new ArrayList<String>();
		for (City city : board.cities.values()) {
			if (!board.currentPlayers.get(playerIndex).playerData.location.equals(city)) {
				cities.add(city.getName());
			}
		}
		String[] players = new String[board.playernumber];
		for (int i = 0; i < players.length; i++) {
			players[i] = Integer.toString(i + 1);
		}
		JComboBox<String> listOfPlayers = new JComboBox<String>(players);
		String[] options = cities.toArray(new String[cities.size()]);
		JComboBox<String> listOfCities = new JComboBox<String>(options);
		listOfCities.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.nameofCityAirlift = listOfCities.getSelectedItem().toString();
				board.idxofPlayerAirlift = Integer.parseInt(listOfPlayers.getSelectedItem().toString()) - 1;
				// TODO broken
				// board.currentPlayers.get(playerIndex).getPlayerAction(Board.ActionName.PLAYEVENTCARD).executeAction();//
				// .useEventCard(board.messages.getString("Airlift"));
				// gui.removePanel(panel);
				// gui.updateImage();
			}

		});
		panel = new JPanel();
		panel.add(listOfPlayers);
		panel.add(listOfCities);
		// gui.addPanel(panel, BorderLayout.CENTER);

	}

}