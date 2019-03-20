import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerListener implements ActionListener{
	int playerNumber;
	JPanel panel;
	InitializeGame game;
	PlayerListener(int x, JPanel y, InitializeGame game){
		playerNumber = x;
		panel = y;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.SetPlayers(playerNumber, panel);
		
	}
	

}
