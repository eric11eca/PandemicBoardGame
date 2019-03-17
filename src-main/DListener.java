import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class DListener implements ActionListener  {

	int epidemicNumber;
	JPanel panel;
	DListener(int x, JPanel y){
		epidemicNumber = x;
		panel = y;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Pandemic.SetDifficulty(epidemicNumber, panel);
		
	}

}
