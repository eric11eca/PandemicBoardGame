package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;

import Card.PlayerCard;
import Initialize.Board;
import Panel.GUI;

public class FlightListener implements ActionListener {
	
	Board board;
	
	public FlightListener(Board board, GUI gui){
		this.board=board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("charter");
		Map<String, PlayerCard> hand = board.currentPlayer.hand;
		ArrayList<PlayerCard> cities = new ArrayList<>();
		for(String i: hand.keySet()){
			if(hand.get(i).cardType.equals(Board.CardType.CITYCARD)){
				cities.add(hand.get(i));
			}
		}
		String[] cityOp = cities.toArray(new String[cities.size()]);
		
		JComboBox<String> options = new JComboBox<String>(cityOp);
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                confirmCity(evt,options);
            }
		});

	}

	protected void confirmCity(ActionEvent evt, JComboBox<String> options) {
		 String chosenCity = options.getSelectedItem().toString();
		 //Make the buttons and call the methods
		
	}
}
