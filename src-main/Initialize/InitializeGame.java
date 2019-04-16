package Initialize;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ButtonListeners.BuildResearchStationListener;
import ButtonListeners.CharterFlightListener;
import ButtonListeners.DListener;
import ButtonListeners.DiscoverCureListener;
import ButtonListeners.DriveListener;
import ButtonListeners.FlightListener;
import ButtonListeners.PlayerListener;
import ButtonListeners.ShareKnowledgeListener;
import ButtonListeners.ShuttleFlightListener;
import ButtonListeners.TreatDiseaseListener;
import Panel.*;

public class InitializeGame {
	public int players = 0;
	public int epidemicNumber = 0;
	private GUI gui;
	JPanel buttonPanel;
	private Board board;
	GameSetup setup;

	public InitializeGame(Board board, GameSetup setup) {
		this.board = board;
		this.setup = setup;
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
		board.playernumber = playernum;
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
		// System.out.println(epidemics);

		epidemicNumber = epidemics;
		board.epidemicCardNum = epidemics;
		gui.removePanel(panelToClose);
		createButtons();
		setup.startGameSetup();
		// createInfoPanel();
		// StartGame();

	}

	public void startCreationofBoard() {
		// createInfoPanel();
		StartGame();
	}

	private void createInfoPanel() {
		gui.panel = new JPanel();
		gui.panel.setLayout(null);
		gui.panel.setSize(1600, 800);
		gui.panel.setPreferredSize(new Dimension(1000, 800));
		int x = 0;
		String Pstring = "";
		while (x < players) {
			Pstring = Pstring + "P" + (x + 1) + ", Atlanta.  ";
			x++;
		}
		String Cstring = "";
		for (int z = 3; z > 0; z--) {
			for (int y = 0; y < 3; y++) {
				Cstring = Cstring + board.validInfectionCard.remove(0) + ": " + z + " cubes. ";
			}
		}
		JLabel label = new JLabel("Current locations: Reasearch stations: Atlanta.  " + Pstring);
		/*
		 * ("KEY: p# is player #" +
		 * "   R is research station     B,R,G and b are Black Red Green and Blue cubes"
		 * );
		 */

		label.setSize(600, 200);
		label.setLocation(0, 0);
		JLabel cubelabel = new JLabel(Cstring);
		/*
		 * ("KEY: p# is player #" +
		 * "   R is research station     B,R,G and b are Black Red Green and Blue cubes"
		 * );
		 */

		cubelabel.setSize(1200, 200);
		cubelabel.setLocation(0, 30);
		board.infectionRate = 2;
		JLabel outbreaksandinfection = new JLabel("outbreaks = " + board.outbreakCount + ".  infection rate = "
				+ board.infectionRate + ". PlayerDeck has " + board.validPlayerCard.size() + " left.");
		outbreaksandinfection.setSize(500, 200);
		outbreaksandinfection.setLocation(600, 0);
		gui.panel.add(cubelabel);
		gui.panel.add(outbreaksandinfection);
		gui.panel.add(label);

	}

	private void StartGame() {
		// TODO Auto-generated method stub
		gui.board = board;
		gui.loadInitialGame();
	}

	private void createButtons() {
		JButton drive = new JButton("Drive/Ferry");
		drive.setToolTipText("Move to a city connected by a white line to the one you are in.");
		DriveListener driveListener = new DriveListener(board);
		drive.addActionListener(driveListener);

		JButton flight = new JButton("Direct Flight");
		flight.setToolTipText("Discard a City card to move to the city named on the card.");
		FlightListener flightListener = new FlightListener(board);
		flight.addActionListener(flightListener);

		JButton cFlight = new JButton("Charter Flight");
		cFlight.setToolTipText("Discard the City card that matches the city you are in to move to any city.");
		CharterFlightListener cFlightListener = new CharterFlightListener(board);
		drive.addActionListener(cFlightListener);

		JButton sFlight = new JButton("Shuttle Flight");
		sFlight.setToolTipText(
				"Move from a city with a research station to any other city that has a research station.");
		ShuttleFlightListener sFlightListener = new ShuttleFlightListener(board);
		sFlight.addActionListener(sFlightListener);

		JButton buildResearchStation = new JButton("Build Reasearch Station");
		buildResearchStation.setToolTipText("Discard the City card that matches the city you are in to place a research"
				+ " station there.\n Take the research station from the pile next to the board.\n If all"
				+ " 6 research stations have been built,\n take a research station from anywhere" + " on the board.");
		BuildResearchStationListener buildResearchStationListener = new BuildResearchStationListener(board);
		buildResearchStation.addActionListener(buildResearchStationListener);

		JButton treatDisease = new JButton("Treat Disease");
		treatDisease.setToolTipText("Remove 1 disease cube from the city you are in, placing it in the cube"
				+ " supply next to the board.\n If this disease color has been cured (see"
				+ " Discover a Cure below),\n remove all cubes of that color from the city you" + " are in.\n"
				+ " If the last cube of a cured disease is removed from the board, this disease" + " is eradicated.");
		TreatDiseaseListener treatDiseaseListener = new TreatDiseaseListener(board);
		treatDisease.addActionListener(treatDiseaseListener);

		JButton shareKnowledge = new JButton("Share Knowledge");
		shareKnowledge.setToolTipText("You can do this action in two ways:\n"
				+ " give the City card that matches the city you are in to another player,\n or"
				+ " take the City card that matches the city you are in from another player.\n"
				+ " The other player must also be in the city with you.\n Both of you need to" + " agree to do this.\n"
				+ " If the player who gets the card now has more than 7 cards,\n that player must"
				+ " immediately discard a card or play an Event card.");
		ShareKnowledgeListener shareKnowledgeListener = new ShareKnowledgeListener(board);
		shareKnowledge.addActionListener(shareKnowledgeListener);

		JButton discoverCure = new JButton("Discover a cure");
		discoverCure.setToolTipText("At any research station, discard 5 City cards of the same color from your"
				+ " hand to cure the disease of that color.\n Move the disease’s cure marker to its"
				+ " Cure Indicator.\n"
				+ " If no cubes of this color are on the board, this disease is now eradicated. ");
		DiscoverCureListener discoverCureListener = new DiscoverCureListener(board);
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

	}

}
