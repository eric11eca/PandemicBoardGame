import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerListener implements ActionListener{
	int playerNumber;
	JPanel panel;
	PlayerListener(int x, JPanel y){
		playerNumber = x;
		panel = y;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Pandemic.SetPlayers(playerNumber, panel);
		
	}
	

}
