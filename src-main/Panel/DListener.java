import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class DListener implements ActionListener  {

	int epidemicNumber;
	JPanel panel;
	InitializeGame game;
	DListener(int x, JPanel y, InitializeGame game){
		epidemicNumber = x;
		panel = y;
		this.game= game;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.SetDifficulty(epidemicNumber, panel);
		
	}

}
