package Initialize;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		players= playernum;
		board.playernumber=playernum;
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
		//System.out.println(epidemics);
		
		epidemicNumber = epidemics;
		board.epidemicCardNum = epidemics;
		gui.removePanel(panelToClose);
		createButtons();
		setup.startGameSetup();
//		createInfoPanel();
//		StartGame();

	}
	
	public void startCreationofBoard(){
		StartGame();
	}

	
	private void StartGame() {
		gui.board = board;
		gui.loadInitialGame();
	}

	private void createButtons() {
		JButton drive = new JButton("Drive/Ferry");
		drive.setToolTipText("Move to a city connected by a white line to the one you are in.");
		JButton flight = new JButton("Direct Flight");
		flight.setToolTipText("Discard a City card to move to the city named on the card.");
		JButton cFlight = new JButton("Charter Flight");
		cFlight.setToolTipText("Discard the City card that matches the city you are in to move to any city.");
		JButton sFlight = new JButton("Shuttle Flight");
		sFlight.setToolTipText("Move from a city with a research station to any other city that has a research station.");
		JButton buildResearchStation = new JButton("Build Reasearch Station");
		buildResearchStation.setToolTipText("Discard the City card that matches the city you are in to place a research"
				+ " station there.\n Take the research station from the pile next to the board.\n If all"
				+ " 6 research stations have been built,\n take a research station from anywhere"
				+ " on the board.");
		JButton treatDisease = new JButton("Treat Disease");
		treatDisease.setToolTipText("Remove 1 disease cube from the city you are in, placing it in the cube"
				+ " supply next to the board.\n If this disease color has been cured (see"
				+ " Discover a Cure below),\n remove all cubes of that color from the city you"
				+ " are in.\n"
				+ " If the last cube of a cured disease is removed from the board, this disease"
				+ " is eradicated.");
		JButton shareKnowledge = new JButton("Share Knowledge");
		shareKnowledge.setToolTipText("You can do this action in two ways:\n"
				+ " give the City card that matches the city you are in to another player,\n or"
				+ " take the City card that matches the city you are in from another player.\n"
				+ " The other player must also be in the city with you.\n Both of you need to"
				+ " agree to do this.\n"
				+ " If the player who gets the card now has more than 7 cards,\n that player must"
				+ " immediately discard a card or play an Event card.");
		JButton discoverCure = new JButton("Discover a cure");
		discoverCure.setToolTipText("At any research station, discard 5 City cards of the same color from your"
				+ " hand to cure the disease of that color.\n Move the disease’s cure marker to its"
				+ " Cure Indicator.\n"
				+ " If no cubes of this color are on the board, this disease is now eradicated. ");
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
		buttonPanel.setPreferredSize(new Dimension(800,800 ));
		for (String i : buttons.keySet()) {
			
			if(x < 4){
				buttons.get(i).setLocation(x*180, 0 );
				buttons.get(i).setSize(150, 20);
				buttonPanel.add(buttons.get(i));
			}
			else{
				buttons.get(i).setLocation(x*(180) - 720, 40 );
				buttons.get(i).setSize(150, 20);
			
				buttonPanel.add(buttons.get(i));
			}
			x++;
		}

	}

}
