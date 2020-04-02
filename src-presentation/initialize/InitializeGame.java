package initialize;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import buttonListeners.BuildResearchStationListener;
import buttonListeners.CharterFlightListener;
import buttonListeners.DifficultyListener;
import buttonListeners.DirectFlightListener;
import buttonListeners.DiscoverCureListener;
import buttonListeners.DriveListener;
import buttonListeners.PlayerListener;
import buttonListeners.ShareKnowledgeListener;
import buttonListeners.ShuttleFlightListener;
import buttonListeners.TreatDiseaseListener;
import data.Board;
import gui.GameGUI;

public class InitializeGame {
	public int players = 0;
	public int epidemicNumber = 0;

	JPanel buttonPanel;
	GameSetup setup;

	private Board board;
	public GameGUI gui;

	public InitializeGame(Board board, GameSetup setup) {
		this.board = board;
		this.setup = setup;
//		String[] language = getLanguages();
//		String[] locale = getLocale();
//		JPanel pnl = new JPanel();
//		
//		JComboBox<String> languageList = new JComboBox<String>(language);
//		JComboBox<String> localeList = new JComboBox<String>(locale);
//		JButton selectLanguage = new JButton("Continue");
//		selectLanguage.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				setup.initializeMessageBundle(languageList.getSelectedItem().toString(), localeList.getSelectedItem().toString());
//				setup.initializeMessageToShow();
//				choosePlayers(pnl);
//			}
//		});
//		
//		pnl.add(languageList);
//		pnl.add(localeList);
//		pnl.add(selectLanguage);
//		gui = new GUI(setup);
//		gui.addPanel(pnl);

	}

	private String[] getLocale() {
		String[] toReturn = { "US", "SP" };
		return toReturn;
	}

	private String[] getLanguages() {
		String[] toReturn = { "en", "sp" };
		return toReturn;
	}

	public void choosePlayers(JPanel panelToRemove) {
		// gui.removePanel(panelToRemove);
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
		// gui.addPanel(pnl);
	}

	public void SetPlayers(int playernum, JPanel panelToClose) {
		players = playernum;
		board.playernumber = playernum;
		// gui.removePanel(panelToClose);
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
		DifficultyListener action1 = new DifficultyListener(4, pnl, this);
		DifficultyListener action2 = new DifficultyListener(5, pnl, this);
		DifficultyListener action3 = new DifficultyListener(6, pnl, this);
		btn2p.addActionListener(action1);
		btn3p.addActionListener(action2);
		btn4p.addActionListener(action3);
		// gui.addPanel(pnl);

	}

	public void SetDifficulty(int epidemics, JPanel panelToClose) {
		epidemicNumber = epidemics;
		board.epidemicCardNum = epidemics;
		// gui.removePanel(panelToClose);
		// createButtons();
		setup.startGameSetup();
	}

	public void startCreationofBoard() {
		StartGame();
	}

	private void StartGame() {
		// gui.board = board;
		// gui.loadInitialGame();
	}

}
