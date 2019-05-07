package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import Card.PlayerCard;
import Initialize.Board;
import Panel.GUI;

public class EventCardListener implements ActionListener {

	Board board;
	JComboBox<String> eventCards;
	GUI gui;
	JPanel panel;

	public EventCardListener(Board board, JComboBox<String> eventCards, GUI gui) {
		this.board = board;
		this.eventCards = eventCards;
		this.gui=gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String card = eventCards.getSelectedItem().toString();
		for (int i = 0; i < board.currentPlayers.size(); i++) {
			Map<String, PlayerCard> playerHand = board.currentPlayers.get(i).playerData.hand;
			if (playerHand.keySet().contains(card)) {
				board.currentPlayers.get(i).playerData.specialEventCard = playerHand.get(card);
				if(card.equals("Airlift")){
					performAirlift(i);
				}
				else if(card.equals("One Quiet Night")){
					performOneQuietNight(i);
				}
				else if(card.equals("Resilient Population")){
					performResilientPopulation(i);
				}
				else if(card.equals("Government Grant")){
					performGovernmentGrant(i);
				}
				else{
					performForecast(i);
				}
				gui.updateImage();
				break;
			}
		}

	}

	private void performForecast(int playerIndex) {
		// TODO Auto-generated method stub
		
	}

	private void performGovernmentGrant(int playerIndex) {
		ArrayList<String> citiesWithoutStations = new ArrayList<String>();
		for(String cityName:board.cities.keySet()){
			if(!board.currentResearchStation.containsKey(cityName)){
				citiesWithoutStations.add(cityName);
			}
		}
		String[] options = citiesWithoutStations.toArray(new String[citiesWithoutStations.size()]);
		JComboBox<String> listOfCities = new JComboBox<String>(options);
		listOfCities.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(playerIndex);
				System.out.println(eventCards.getSelectedItem().toString());
				System.out.println(board.currentPlayers.get(playerIndex).playerData.hand.keySet());
				board.cityWithGrant = listOfCities.getSelectedItem().toString();
				System.out.println(board.cityWithGrant);
				board.currentPlayers.get(playerIndex).useEventCard("Government Grant");
				System.out.println(board.cityWithGrant);
				gui.removePanel(panel);
				gui.updateImage();
				for(String cityName:board.cities.keySet()){
					if(board.cities.get(cityName).researchStation){
						System.out.println(cityName);
					}
				}
			}
			
		});
		panel = new JPanel();
		panel.add(listOfCities);
		gui.addPanel(panel, BorderLayout.CENTER);
	}

	private void performResilientPopulation(int playerIndex) {
		// TODO Auto-generated method stub
		
	}

	private void performOneQuietNight(int playerIndex) {
		board.currentPlayers.get(playerIndex).useEventCard("OneQuietNight");
	}

	private void performAirlift(int playerIndex) {
		// TODO choose a city to travel to
		ArrayList<String> cities = new ArrayList<String>();
		for(String cityName:board.cities.keySet()){
			if(!board.currentPlayers.get(playerIndex).playerData.location.cityName.equals(cityName)){
				cities.add(cityName);
			}
		}
		String[] players = new String[board.playernumber];
		for(int i=0;i<players.length;i++){
			players[i]=Integer.toString(i+1);
		}
		JComboBox<String> listOfPlayers = new JComboBox<String>(players);
		String[] options = cities.toArray(new String[cities.size()]);
		JComboBox<String> listOfCities = new JComboBox<String>(options);
		listOfCities.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.nameofCityAirlift = listOfCities.getSelectedItem().toString();
				board.idxofPlayerAirlift = Integer.parseInt(listOfPlayers.getSelectedItem().toString());
				board.currentPlayers.get(playerIndex).useEventCard("Airlift");
				gui.removePanel(panel);
				gui.updateImage();
			}
			
		});
		panel = new JPanel();
		panel.add(listOfPlayers);
		panel.add(listOfCities);
		gui.addPanel(panel, BorderLayout.CENTER);
		
	}

}
