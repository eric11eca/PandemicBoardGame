package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cards.PlayerCard;
import data.Board;
import gui.GUI;
import initialize.GameSetup;
import player.PlayerData;

public class DiscoverCureListener implements ActionListener {
	private Board board;
	private GUI gui;
	private GameSetup gameSetup;
	private JPanel panel;

	public DiscoverCureListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (board.dispatcherCase == -1) {
			System.out.println(board.messages.getString("discoverCure")); 
			PlayerData playerData = board.currentPlayer.playerData;
			Map<String, PlayerCard> playerHand = playerData.hand;
			Set<String> handNames = playerHand.keySet();
			ArrayList<String> cityCards = new ArrayList<>();

			for (String cardName : handNames) {
				if (playerHand.get(cardName).cardType.equals(Board.CardType.CITYCARD))
					cityCards.add(cardName);
			}

			String[] cards = cityCards.toArray(new String[cityCards.size()]);
			JCheckBox[] options = new JCheckBox[handNames.size()];

			panel = new JPanel();
			for (int i = 0; i < cards.length; i++) {
				String checkBox = MessageFormat.format("{0} {1} {2} {3}", 
						cards[i], board.messages.getString("openParentheses"),
						playerHand.get(cards[i]).color, 
						board.messages.getString("closeParentheses"));
				
				JCheckBox cardOption = new JCheckBox(checkBox);
				cardOption.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							cardOption.setSelected(true);
						} else {
							cardOption.setSelected(false);
						}
						;
					}
				});
				options[i] = cardOption;
				panel.add(cardOption);
			}

			JButton confirm = new JButton(board.messages.getString("confirm")); 
			confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					for (int i = 0; i < options.length; i++) {
						if (options[i].isSelected()) {
							String cardName = getCardName(options[i].getText());
							PlayerCard card = playerData.hand.get(cardName);
							board.cardsToCureDisease.add(card);
						}
					}

					cureDisease();
				}
			});
			panel.add(confirm);
			gui.addPanel(panel, BorderLayout.CENTER);
		} else {
			JOptionPane.showMessageDialog(null, board.messages.getString("dispatcherErrorMessage"));
		}
	}

	protected String getCardName(String text) {
		StringBuilder toReturn = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) != '(') {
				toReturn.append(text.charAt(i));
			} else {
				break;
			}
		}
		return toReturn.toString();
	}

	protected void cureDisease() {
		board.actionName = Board.ActionName.CUREDISEASE;
		try {
			gameSetup.oneTurn();
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, board.messages.getString("cureErrorMessage")); 
		}

		gui.removePanel(panel);
		gui.updateImage();
	}
}
