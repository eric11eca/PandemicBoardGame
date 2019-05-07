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
				board.currentPlayers.get(i).playerData.roleCard = playerHand.get(card).cardName;
				board.currentPlayers.get(i).useEventCard(card);
				gui.updateImage();
				break;
			}
		}

	}

}
