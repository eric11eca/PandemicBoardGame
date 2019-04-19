package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;

import Initialize.Board;
import Panel.GUI;

public class TreatDiseaseListener implements ActionListener {
	
	Board board;
	
	public TreatDiseaseListener(Board board, GUI gui){
		this.board=board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Map<String, Integer> colorsmap = board.currentPlayer.location.diseaseCubes;
		ArrayList<String> colors = new ArrayList<>();
		for(String i : colorsmap.keySet()){
			if(colorsmap.get(i) != 0 ){
				colors.add(i);
			}
		}
		String[] colorOptions = colors.toArray(new String[colors.size()]);

		JComboBox<String> options = new JComboBox<String>(colorOptions);
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                confirmRemoveDisease(evt,options);
            }
		});

	}

	protected void confirmRemoveDisease(ActionEvent evt, JComboBox<String> options) {
		// TODO Auto-generated method stub
		
	}

}
