package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Card.PlayerCard;
import Initialize.Board;
import Panel.GUI;

public class PickCardsToBeDiscard {
	private Board board;
	private GUI gui;
	private JPanel panel;
	private int selected = 0;

	public PickCardsToBeDiscard(GUI gui, Board board){
		this.board=board;
		this.gui=gui;
	}

	public void pickCardsPrompt() {
		panel = new JPanel();
		Map<String, PlayerCard> playerHand = board.currentPlayer.hand;
		Set<String> handNames = playerHand.keySet();
		List<String> cardTobeDiscard = new ArrayList<>();
		
		for (String name : handNames) {
			JCheckBox cardOption = new JCheckBox(name);
			cardOption.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (cardOption.isSelected()) {
						String cardName = cardOption.getText();
			            cardTobeDiscard.add(cardName);
			            selected += 1;
			        } 
				}
			});
			panel.add(cardOption);
		}
		
		int cardNumberToDiscard = board.currentPlayer.hand.size()-7;
		
		JButton comfirm = new JButton("comfirm");
		comfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(cardNumberToDiscard != selected){
					JOptionPane.showMessageDialog(null, 
							"Please only discard "+ cardNumberToDiscard+"cards");
				} else {
					board.cardToBeDiscard = cardTobeDiscard;
					gui.removePanel(panel);
					gui.updateImage();
				}
			}
		});
		
		panel.add(comfirm);
		gui.addPanel(panel, BorderLayout.CENTER);		
	}
}