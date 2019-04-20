package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class DiscoverCureListener implements ActionListener {
	private Board board;
	private GUI gui;
	private GameSetup gameSetup;
	private JPanel panel;
	
	public DiscoverCureListener(Board board, GUI gui, GameSetup gameSetup){
		this.board=board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Discovering a cure");
		Map<String, PlayerCard> playerHand = board.currentPlayer.hand;
		Set<String> handNames = playerHand.keySet();
		
		JPanel messageBoard = new JPanel();
		ArrayList<String> messages = new ArrayList<>();
		
		String intro0 = MessageFormat.format("\n you are a {0}", board.currentPlayer.role);
		String intro1 = "\n Select 5 (4 if you are a scientist) cards with the same color";
		String intro2 = "\n then clik confirm button to cure a disease.";
		String intro3 = "\n Here are the card names and their colors: ";
		messages.add(intro0);
		messages.add(intro1);
		messages.add(intro2);
		messages.add(intro3);
		for(String name : handNames) {
			String msg =  MessageFormat.format("\n {0}, {1}", 
					name, playerHand.get(name).color);
			messages.add(msg);
		}
		
		gui.displayMessage(messages, messageBoard);
		
		String[] cards = new String[handNames.size()];
		int incr = 0;
		for (String cardName : handNames) {
			cards[incr] = cardName;
			incr++;
		}
		
		panel = new JPanel();
		for (int i = 0; i < cards.length; i++) {
			JCheckBox cardOption = new JCheckBox(cards[i]);
			cardOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (cardOption.isSelected()) {
						String cardName = cardOption.getText();
			        	PlayerCard card = board.currentPlayer.hand.get(cardName);
			            board.cardsToCureDisease.add(card);
			        } 
				}
			});
			panel.add(cardOption);
		}
		
		JButton comfirm = new JButton("comfirm");
		comfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gui.removePanel(messageBoard);
				cureDisease();
			}
		});
		panel.add(comfirm);
		gui.addPanel(panel, BorderLayout.CENTER);
	}
	
	protected void cureDisease() {
		board.actionName = "CureDisease";
		gameSetup.oneTurn();
		if(board.gameEnd) {
			if(board.playerWin) {
				gui.gameEnd(GUI.WINING_MESSAGE);
			} else if (board.playerLose){
				gui.gameEnd(GUI.LOSING_MESSAGE);
			}
		}
		gui.removePanel(panel);
		gui.updateImage();
	}
}
