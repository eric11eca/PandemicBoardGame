package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;

import Card.PlayerCard;
import Initialize.Board;
import Panel.GUI;

public class EventCardListener implements ActionListener {

	Board board;
	JComboBox<String> eventCards;
	GUI gui;

	public EventCardListener(Board board, JComboBox<String> eventCards, GUI gui) {
		this.board = board;
		this.eventCards = eventCards;
		this.gui=gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < board.currentPlayers.size(); i++) {
			Map<String, PlayerCard> playerHand = board.currentPlayers.get(i).playerData.hand;
			String card = eventCards.getSelectedItem().toString();
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

	private void performForecast(int i) {
		// TODO Auto-generated method stub
		
	}

	private void performGovernmentGrant(int i) {
		// TODO Show list of cities without stations, and user chooses one
		
	}

	private void performResilientPopulation(int i) {
		// TODO Auto-generated method stub
		
	}

	private void performOneQuietNight(int i) {
		board.currentPlayers.get(i).useEventCard("OneQuietNight");
	}

	private void performAirlift(int i) {
		// TODO choose a city to travel to
		
	}

}
