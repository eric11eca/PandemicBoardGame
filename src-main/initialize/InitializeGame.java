package initialize;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import buttonListeners.BuildResearchStationListener;
import buttonListeners.CharterFlightListener;
import buttonListeners.DListener;
import buttonListeners.DirectFlightListener;
import buttonListeners.DiscoverCureListener;
import buttonListeners.DriveListener;
import buttonListeners.PlayerListener;
import buttonListeners.ShareKnowledgeListener;
import buttonListeners.ShuttleFlightListener;
import buttonListeners.TreatDiseaseListener;
import data.Board;
import panel.GUI;

public class InitializeGame {
	public int players = 0;
	public int epidemicNumber = 0;
	
	JPanel buttonPanel;
	GameSetup setup;
	
	private Board board;
	public GUI gui;

	public InitializeGame(Board board, GameSetup setup) {
		this.board = board;
		this.setup = setup;
		// Build all the objects
		// Call the GUI to select the number of players
		JPanel pnl = new JPanel();
		JButton btn2p = new JButton(board.messages.getString("2Players"));
		JButton btn3p = new JButton(board.messages.getString("3Players"));
		JButton btn4p = new JButton(board.messages.getString("4Players"));
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
		gui = new GUI(setup);
		gui.addPanel(pnl);

	}

	public void SetPlayers(int playernum, JPanel panelToClose) {
		players = playernum;
		board.playernumber = playernum;
		gui.removePanel(panelToClose);
		Difficulty();
	}

	private void Difficulty() {
		JPanel pnl = new JPanel();
		JButton btn2p = new JButton(board.messages.getString("easy"));
		JButton btn3p = new JButton(board.messages.getString("normal"));
		JButton btn4p = new JButton(board.messages.getString("hard"));
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
		board.epidemicCardNum = epidemics;
		gui.removePanel(panelToClose);
		createButtons();
		setup.startGameSetup();
	}
	
	public void startCreationofBoard(){
		StartGame();
	}

	
	private void StartGame() {
		gui.board = board;
		gui.loadInitialGame();
	}

	private void createButtons() {
		JButton drive = new JButton(board.messages.getString("drive"));
		DriveListener driveListener = new DriveListener(board, gui, setup);
		drive.addActionListener(driveListener);

		JButton flight = new JButton(board.messages.getString("directFlight"));
		DirectFlightListener flightListener = new DirectFlightListener(board, gui, setup);
		flight.addActionListener(flightListener);

		JButton cFlight = new JButton(board.messages.getString("charterFlight"));
		CharterFlightListener cFlightListener = new CharterFlightListener(board, gui, setup);
		cFlight.addActionListener(cFlightListener);

		JButton sFlight = new JButton(board.messages.getString("shuttleFlight"));
		ShuttleFlightListener sFlightListener = new ShuttleFlightListener(board, gui,setup);
		sFlight.addActionListener(sFlightListener);

		JButton buildResearchStation = new JButton(board.messages.getString("buildResearchStation"));
		BuildResearchStationListener buildResearchStationListener = new BuildResearchStationListener(board, gui, setup);
		buildResearchStation.addActionListener(buildResearchStationListener);

		JButton treatDisease = new JButton(board.messages.getString("treatDisease"));
		TreatDiseaseListener treatDiseaseListener = new TreatDiseaseListener(board, gui, setup);
		treatDisease.addActionListener(treatDiseaseListener);

		JButton shareKnowledge = new JButton(board.messages.getString("shareKnowledge"));
		ShareKnowledgeListener shareKnowledgeListener = new ShareKnowledgeListener(board, gui, setup);
		shareKnowledge.addActionListener(shareKnowledgeListener);

		JButton discoverCure = new JButton(board.messages.getString("discover"));
		DiscoverCureListener discoverCureListener = new DiscoverCureListener(board, gui, setup);
		discoverCure.addActionListener(discoverCureListener);

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
		int x = 0;
		buttonPanel.setLayout(null);
		buttonPanel.setSize(800, 800);
		buttonPanel.setPreferredSize(new Dimension(800, 800));
		for (String i : buttons.keySet()) {

			if (x < 4) {
				buttons.get(i).setLocation(x * 180, 0);
				buttons.get(i).setSize(150, 20);
				buttonPanel.add(buttons.get(i));
			} else {
				buttons.get(i).setLocation(x * (180) - 720, 40);
				buttons.get(i).setSize(150, 20);

				buttonPanel.add(buttons.get(i));
			}
			x++;
		}
		buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}
}
