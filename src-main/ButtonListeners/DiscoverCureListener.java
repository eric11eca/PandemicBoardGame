package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import Player.Player;

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
		Player player = board.currentPlayer;
		Map<String, PlayerCard> playerHand = player.hand;
		Set<String> handNames = playerHand.keySet();
		ArrayList<String> cityCards = new ArrayList<>();
		
		for (String cardName : handNames) {
			if(playerHand.get(cardName).cardType.equals(Board.CardType.CITYCARD))
				cityCards.add(cardName);
		}
		
		String[] cards = cityCards.toArray(new String[cityCards.size()]);
		JCheckBox[] options = new JCheckBox[handNames.size()];
		
		panel = new JPanel();
		for (int i = 0; i < cards.length; i++) {
			JCheckBox cardOption = new JCheckBox(cards[i] + 
					"(" + playerHand.get(cards[i]).color+")");
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
			options[i] = cardOption;
			panel.add(cardOption);
		}
		
		JButton comfirm = new JButton("comfirm");
		comfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				for(int i = 0; i < options.length; i++){
					if(options[i].isSelected()){
						String cardName = getCardName(options[i].getText());
						PlayerCard card = player.hand.get(cardName);
						board.cardsToCureDisease.add(card);
					}
				}

				cureDisease();
			}
		});
		panel.add(comfirm);
		gui.addPanel(panel, BorderLayout.CENTER);
		
	}
	
	protected String getCardName(String text) {
		StringBuilder toReturn = new StringBuilder();
		for (int i = 0; i < text.length(); i++){
			if (text.charAt(i) != '('){
				toReturn.append(text.charAt(i));
			}
			else{
				break;
			}
		}
		return toReturn.toString();
	}

	protected void cureDisease() {
		board.actionName = Board.ActionName.CUREDISEASE;
		try{
			gameSetup.oneTurn();
		} catch(RuntimeException e) {
			JOptionPane.showMessageDialog(null, 
			  "Please use city cards with same color to cure a disease.");
		}
		
		gui.removePanel(panel);
		gui.updateImage();
	}
}
