package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
		ArrayList<String> discardCards = new ArrayList<String>();
		ArrayList<String> cityCards = new ArrayList<>();
		int incr = 0;
		for (String cardName : handNames) {
			if(board.currentPlayer.hand.get(cardName).cardType==Board.CardType.CITYCARD)
				cityCards.add(cardName);
			incr++;
		}
		String[] cards = cityCards.toArray(new String[cityCards.size()]);
		JCheckBox[] options = new JCheckBox[handNames.size()];
		panel = new JPanel();
		for (int i = 0; i < cards.length; i++) {
			JCheckBox cardOption = new JCheckBox(cards[i] + "(" + playerHand.get(cards[i]).color+")");
			options[i] = cardOption;
			panel.add(cardOption);
		}
		
		JButton comfirm = new JButton("comfirm");
		comfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				for(int i=0;i<options.length;i++){
					if(options[i].isSelected()){
						discardCards.add(getCardName(options[i].getText()));
					}
				}
				String color =board.cities.get(discardCards.get(0)).color;
				int count = 0;
				for(int i = 0;i<discardCards.size();i++){
					if(!board.cities.get(discardCards.get(0)).color.equals(color)){
						JOptionPane.showMessageDialog(null, 
								"Please only discard city cards of the same color.");
						return;
					}
					else{
						count++;
					}
				}
				board.cardToBeDiscard = discardCards;
				cureDisease();
			}
		});
		panel.add(comfirm);
		gui.addPanel(panel, BorderLayout.CENTER);
	}
	
	protected String getCardName(String text) {
		StringBuilder toReturn = new StringBuilder();
		for (int i = 0;i<text.length();i++){
			if (text.charAt(i)!='('){
				toReturn.append(text.charAt(i));
			}
			else{
				break;
			}
		}
		return toReturn.toString();
	}

	protected void cureDisease() {
		board.actionName = "CureDisease";
		gameSetup.oneTurn();
		gui.removePanel(panel);
		gui.updateImage();
	}
}
