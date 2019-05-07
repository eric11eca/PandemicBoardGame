package buttonListeners;

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

import Initialize.Board;
import Initialize.GameSetup;
import cards.PlayerCard;
import panel.GUI;

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
		Map<String, PlayerCard> playerHand = board.currentPlayer.playerData.hand;
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
					board.currentPlayer.discardCard();
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