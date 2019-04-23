package Card;

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

import Initialize.Board;
import Panel.GUI;

public class DiscardCard {
	private Board board;
	private GUI gui;
	private JPanel panel;
	private int selected = 0;

	public DiscardCard(GUI gui, Board board){
		this.board=board;
		this.gui=gui;
	}

	public void pickCardsPrompt() {
		panel = new JPanel();
		ArrayList<JCheckBox> options = new ArrayList<>();
		Map<String, PlayerCard> playerHand = board.currentPlayer.hand;
		Set<String> handNames = playerHand.keySet();
		List<String> cardTobeDiscard = new ArrayList<>();
		
		for (String name : handNames) {
			JCheckBox cardOption = new JCheckBox(name);
			options.add(cardOption);
			panel.add(cardOption);
		}
		
		int cardNumberToDiscard = board.currentPlayer.hand.size()-7;
		
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
					gui.removePanel(panel);
					gui.updateImage();
					
				}
			}
		});
		
		panel.add(comfirm);
		gui.addPanel(panel, BorderLayout.CENTER);		
	}
}