package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import Initialize.GameSetup;
import Panel.GUI;

public class DiscardCard {
	private Board board;
	private GUI gui;
	private JPanel panel;
	private int selected = 0;
	private GameSetup gameSetup;

	public DiscardCard(GUI gui, Board board, GameSetup gameSetup){
		this.board=board;
		this.gui=gui;
		this.gameSetup = gameSetup;
	}

	public void pickCardsPrompt() {
		panel = new JPanel();
		ArrayList<JCheckBox> options = new ArrayList<>();
		Map<String, PlayerCard> playerHand = board.currentPlayer.hand;
		Set<String> handNames = playerHand.keySet();
		List<String> cardTobeDiscard = new ArrayList<>();
		
		for (String name : handNames) {
			JCheckBox cardOption = new JCheckBox(name);
			cardOption.addItemListener(new ItemListener() {
			    @Override
			    public void itemStateChanged(ItemEvent e) {
			        if(e.getStateChange() == ItemEvent.SELECTED) {
			            cardOption.setSelected(true);
			        } else {
			            cardOption.setSelected(false);
			        };
			    }
			});
			options.add(cardOption);
			panel.add(cardOption);
		}
		
		JButton comfirm = new JButton("comfirm");
		comfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				for(int i = 0;i<options.size();i++){
					if(options.get(i).isSelected()){
						selected++;
						cardTobeDiscard.add(options.get(i).getText());
					}
				}
				if(options.size()-selected!=7){
					int numberToDiscard = options.size()-7;
					JOptionPane.showMessageDialog(null, 
							"Please only discard "+ numberToDiscard+" cards");
					cardTobeDiscard.clear();
					selected = 0;
				} else {
					board.cardToBeDiscard = cardTobeDiscard;
					System.out.println("player in exception: " + board.currentPlayerIndex);
					System.out.println("hand while drawing: " + board.currentPlayer.hand.keySet().toString());
					board.currentPlayer.discardCard();
					board.currentPlayerIndex++;
					if (board.currentPlayerIndex == board.playernumber){
						board.currentPlayerIndex = 0;
					}
					System.out.println("hand size: " + board.currentPlayer.hand.size());
					System.out.println("hand after drawing: " + board.currentPlayer.hand.keySet().toString());
					gui.removePanel(panel);
					gui.updateImage();
					gameSetup.changePlayer();
				}
			}
		});
		
		panel.add(comfirm);
		gui.addPanel(panel, BorderLayout.CENTER);		
	}
}