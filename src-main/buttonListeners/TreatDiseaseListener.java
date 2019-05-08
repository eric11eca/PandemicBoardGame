package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import initialize.Board;
import initialize.GameSetup;
import panel.GUI;

public class TreatDiseaseListener implements ActionListener {
	
	Board board;
	private GameSetup gameSetup;
	private GUI gui;
	private JPanel panel;
	
	public TreatDiseaseListener(Board board, GUI gui, GameSetup gameSetup ){
		this.board=board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Map<String, Integer> colorsmap = board.currentPlayer.playerData.location.diseaseCubes;
		ArrayList<String> colors = new ArrayList<>();
		for(String i : colorsmap.keySet()){
			if(colorsmap.get(i) != 0 ){
				colors.add(i);
			}
		}
		colors.add("Cancel");
		if(colorsmap.size() == 0){
			return;
		}
		String[] colorOptions = colors.toArray(new String[colors.size()]);

		JComboBox<String> options = new JComboBox<String>(colorOptions);
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                confirmRemoveDisease(options);
            }
		});
		panel = new JPanel();
		panel.add(options);
		gui.addPanel(panel, BorderLayout.CENTER);

	}

	protected void confirmRemoveDisease(JComboBox<String> options) {
		 String chosenCity = options.getSelectedItem().toString();
		 if(chosenCity.equals("Cancel")){
			 gui.removePanel(panel);
			 return;
		 }
		 int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to treat this disease", "Are you sure you want to treat this disease", JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				board.diseaseBeingTreated = chosenCity;
				board.actionName = Board.ActionName.TREATDISEASE;
				gameSetup.oneTurn();
				gui.removePanel(panel);
				gui.updateImage();
			} else {

			}
		
	}
}
