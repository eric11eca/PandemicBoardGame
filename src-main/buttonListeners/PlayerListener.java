package buttonListeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Initialize.*;

public class PlayerListener implements ActionListener{
	int playerNumber;
	JPanel panel;
	InitializeGame game;
	public PlayerListener(int x, JPanel y, InitializeGame game){
		playerNumber = x;
		panel = y;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.SetPlayers(playerNumber, panel);
		
	}
	

}
