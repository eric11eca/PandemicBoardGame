package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import buttonListeners.ContingencyPlannerListener;
import buttonListeners.DiscardCard;
import buttonListeners.DispatcherListener;
import buttonListeners.EventCardListener;
import cards.PlayerCard;
import data.Board;
import data.Board.Roles;
import initialize.GameSetup;

public class GUI {
	public ArrayList<JPanel> panels = new ArrayList<>();
	public Board board;
	public JPanel mainPanel;
	public ArrayList<JLabel> hands = new ArrayList<>();
	
	private JFrame frame;
	private GameSetup gameSetup;
	private DrawingBoard draw;
	private JLabel label = new JLabel();
	private Map<String, String> diseaseColors; 
	
	public GUI(GameSetup gameSetup) {
		frame = new JFrame();
		this.gameSetup = gameSetup;
		frame.setSize(1900, 1900);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		diseaseColors = new HashMap<>();
	}
	
	private void addColorStrings() {
		diseaseColors.put("RED", board.messages.getString("redDiseas"));
		diseaseColors.put("BLUE", board.messages.getString("blueDiseas"));
		diseaseColors.put("BLACK", board.messages.getString("blackDiseas"));
		diseaseColors.put("YELLOW", board.messages.getString("yellowDiseas"));
	}

	private void setPanels(JLabel labelToSet) {
		frame.add(labelToSet, BorderLayout.PAGE_END);
		frame.add(mainPanel, BorderLayout.WEST);
	}

	public void addPanel(JPanel panel) {
		frame.add(panel);
	}

	public void addPanel(JPanel panel, String east) {
		frame.add(panel, east);
		panel.setVisible(true);
		loadBoardImage();
	}

	public void removePanel(JPanel panel) {
		panel.setVisible(false);
		frame.remove(panel);
	}

	public void loadInitialGame() {
		loadBoardImage();
		updateAndDrawBoardInfo();
	}

	private void updateAndDrawBoardInfo() {
		JPanel panel = new JPanel();
		int x = 0;
		
		for (x = 0; x < board.playernumber; x++) {
			int currentPlayer = x+1;
			String role = board.currentPlayers.get(x).playerData.role.toString();
			String playerInfo = MessageFormat.format(
					board.messages.getString("playerInfo"), currentPlayer, role);
			JLabel player = new JLabel(playerInfo);
			
			player.setLocation(25, x * 25);
			player.setSize(250, 20);
			panel.add(player);
			Set<String> hand = new HashSet<String>();
			hand.addAll(board.currentPlayers.get(x).playerData.hand.keySet());
			JComboBox<String> options = new JComboBox<String>(hand.toArray(new String[hand.size()]));
			panel.setLayout(null);
			panel.setSize(200, 200);
			panel.setPreferredSize(new Dimension(800, 800));
			options.setLocation(300, x * 25);
			options.setSize(150, 20);
			panel.add(options);

		}
		
		JLabel events = new JLabel(board.messages.getString("eventCard"));
		events.setLocation(25, x*25);
		events.setSize(250,20);
		panel.add(events);
		
		int currentPlayerIndex = board.currentPlayerIndex+1;
		String playerTurn = MessageFormat
				.format(board.messages.getString("playerTurn"), currentPlayerIndex);
		
		JLabel currentPlayer = new JLabel(playerTurn);
		currentPlayer.setLocation(25,(x+1)*25);
		currentPlayer.setSize(250,20);
		panel.add(currentPlayer);
		
		int actionCount = board.currentPlayer.playerData.action;
		String actionRemain = MessageFormat
				.format(board.messages.getString("action"), actionCount);
		JLabel action = new JLabel(actionRemain);
		action.setLocation(25, (x+2)*25);
		action.setSize(250,20);
		panel.add(action);
		
		JComboBox<String> eventCards = makeEventCardOptions();
		eventCards.setLocation(300, (x) * 25);
		eventCards.setSize(150, 20);
		panel.add(eventCards);
		
		JButton eventButton = new JButton(board.messages.getString("playEventCard"));
		eventButton.addActionListener(new EventCardListener(board, eventCards, this));
		eventButton.setLocation(300, (x+1) * 25);
		eventButton.setSize(150, 20);
		panel.add(eventButton);
		
		JButton specialSkillButton = new JButton(board.messages.getString("useSpecialSkill"));
		specialSkillButton.setLocation(475, x*25);
		specialSkillButton.setSize(150, 20);
		if(board.currentPlayer.playerData.role==Roles.DISPATCHER ){
			specialSkillButton.addActionListener(new DispatcherListener(board,this));
			panel.add(specialSkillButton);
		}else if(board.currentPlayer.playerData.role==Roles.CONTINGENCYPLANNER){
			specialSkillButton.addActionListener(new ContingencyPlannerListener(board, this));
			panel.add(specialSkillButton);
		}
		
		addColorStrings();
		int i=0;
		for(String disease:board.remainDiseaseCube.keySet()){
			int remainDiseaseCubeNum = board.remainDiseaseCube.get(disease);
			String diseaseCubeInfo =  MessageFormat
					.format(diseaseColors.get(disease), remainDiseaseCubeNum);
			JLabel label = new JLabel(diseaseCubeInfo);
			label.setLocation(475, i*25);
			label.setSize(150,20);
			panel.add(label);
			i++;
		}
		panels.add(panel);
		addPanel(panel, BorderLayout.AFTER_LINE_ENDS);
	}

	private JComboBox<String> makeEventCardOptions() {
		ArrayList<String> eventCards = new ArrayList<String>();
		for (int i = 0; i < board.currentPlayers.size(); i++) {
			Map<String, PlayerCard> playerHand = board.currentPlayers.get(i).playerData.hand;
			for (String card : playerHand.keySet()) {
				if (playerHand.get(card).cardType.equals(Board.CardType.EVENTCARD)) {
					eventCards.add(card);
				}
			}
		}
		for (int i=0;i<board.currentPlayers.size();i++){
			if(board.currentPlayers.get(i).playerData.specialEventCard!=null){
				eventCards.add(board.currentPlayers.get(i).playerData.specialEventCard);
			}
		}
		String[] cards = eventCards.toArray(new String[eventCards.size()]);
		JComboBox<String> eventCardsInHands = new JComboBox<String>(cards);
		return eventCardsInHands;
	}

	private void loadBoardImage() {
		try {

			draw = new DrawingBoard(board, frame, label);
			draw.repaint();
			setPanels(label);

		} catch (IOException e) {
			String errorMessage = MessageFormat
					.format(board.messages.getString("fileNotFound"), e.getMessage());
			System.out.println(errorMessage);
		}

	}

	public void updateImage() {
		loadBoardImage();
		for (JPanel panel : panels) {
			removePanel(panel);
		}
		updateAndDrawBoardInfo();
	}

	public void setButtonPanel(JPanel buttonPanel) {
		mainPanel.add(buttonPanel);
	}

	public void gameEnd(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void showPlayerHand() {
		System.out.println(board.currentPlayerIndex);
		DiscardCard pickCardsToBeDiscard = new DiscardCard(this, board, gameSetup);
		pickCardsToBeDiscard.pickCardsPrompt();
	}
}
