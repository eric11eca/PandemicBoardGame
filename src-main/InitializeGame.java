import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InitializeGame {
	int players = 0;
	int epidemicNumber = 0;
	private GUI gui;
	JPanel buttonPanel;

	InitializeGame() {
		// Build all the objects
		// Call the GUI to select the number of players
		JPanel pnl = new JPanel();
		JButton btn2p = new JButton("2 players");
		JButton btn3p = new JButton("3 players");
		JButton btn4p = new JButton("4 players");
		pnl.add(btn2p);
		pnl.add(btn3p);
		pnl.add(btn4p);
		PlayerListener action1 = new PlayerListener(2, pnl, this);
		PlayerListener action2 = new PlayerListener(3, pnl, this);
		PlayerListener action3 = new PlayerListener(4, pnl, this);
		btn2p.addActionListener(action1);
		btn3p.addActionListener(action2);
		btn4p.addActionListener(action3);
		buttonPanel = new JPanel();
		gui = new GUI();
		gui.addPanel(pnl);

	}

	public void SetPlayers(int playernum, JPanel panelToClose) {
		players = playernum;
		gui.removePanel(panelToClose);
		Difficulty();
	}

	private void Difficulty() {
		JPanel pnl = new JPanel();
		JButton btn2p = new JButton("Easy");
		JButton btn3p = new JButton("Normal");
		JButton btn4p = new JButton("Hard");
		pnl.add(btn2p);
		pnl.add(btn3p);
		pnl.add(btn4p);
		DListener action1 = new DListener(4, pnl, this);
		DListener action2 = new DListener(5, pnl, this);
		DListener action3 = new DListener(6, pnl, this);
		btn2p.addActionListener(action1);
		btn3p.addActionListener(action2);
		btn4p.addActionListener(action3);
		gui.addPanel(pnl);

	}

	public void SetDifficulty(int epidemics, JPanel panelToClose) {
		epidemicNumber = epidemics;
		gui.removePanel(panelToClose);
		createButtons();
		StartGame();

	}

	private void StartGame() {
		// TODO Auto-generated method stub
		gui.loadInitialGame();
	}

	private void createButtons() {
		JButton drive = new JButton("Drive/Ferry");
		JButton flight = new JButton("Direct Flight");
		JButton cFlight = new JButton("Charter Flight");
		JButton sFlight = new JButton("Shuttle Flight");
		JButton buildResearchStation = new JButton("Build Reasearch Station");
		JButton treatDisease = new JButton("Treat Disease");
		JButton shareKnowledge = new JButton("Share Knowledge");
		JButton discoverCure = new JButton("Discover a cure");
		HashMap<String, JButton> buttons = new HashMap<>();
		buttons.put("drive", drive);
		buttons.put("flight", flight);
		buttons.put("cFlight", cFlight);
		buttons.put("sFlight", sFlight);
		buttons.put("buildResearchStation", buildResearchStation);
		buttons.put("treatDisease", treatDisease);
		buttons.put("shareKnowledge", shareKnowledge);
		buttons.put("discoverCure", discoverCure);

		addButtonsToPanel(buttons);
		gui.setButtonPanel(buttonPanel);
	}

	private void addButtonsToPanel(HashMap<String, JButton> buttons) {
		for (String i : buttons.keySet()) {
			buttonPanel.add(buttons.get(i));
		}

	}

}
