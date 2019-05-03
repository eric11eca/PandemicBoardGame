package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import ButtonListeners.DiscardCard;
import Initialize.Board;
import Initialize.GameSetup;

public class GUI {
	JFrame frame;
	JPanel buttonPanel;
	public ArrayList<JPanel> panels = new ArrayList<>();
	public Board board;
	public JPanel mainPanel;
	DrawingBoard draw;
	public int test = 0;
	JLabel label = new JLabel();
	public ArrayList<JLabel> hands = new ArrayList<>();
	GameSetup gameSetup;
	
	static final int SCREEN_HEIGHT = 1080;
	static final int SCREEN_WIDTH = 1920;
	static final double WIDTH_SCALE = 0.4;
	private static final double HEIGHT_SCALE = 1.8;
	
	public static final String WINING_MESSAGE = "\n CONGRADULATIONS, \n YOU WIN!";
	public static final String LOSING_MESSAGE = "\n SORRY, YOU LOSE, \n WNNA TRY AGAIN?";

	public GUI(GameSetup gameSetup) {
		frame = new JFrame();
		this.gameSetup = gameSetup;
		frame.setSize(1900, 1900);
		frame.show();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void setPanels(JLabel labelToSet) {
		mainPanel = new JPanel();
		frame.add(labelToSet, BorderLayout.PAGE_END);
		mainPanel.add(buttonPanel);
		frame.add(mainPanel, BorderLayout.WEST);
	}

	public void addPanel(JPanel panel) {
		frame.add(panel);
	}

	public void addPanel(JPanel panel, String east) {
		frame.add(panel, east);
		loadBoardImage();
	}

	public void removePanel(JPanel panel) {
		panel.setVisible(false);
		frame.remove(panel);
	}

	public void loadInitialGame() {
		loadBoardImage();
		updateAndDrawHand();

	}

	private void updateAndDrawHand() {
		for(int x = 0; x < board.playernumber ; x++){
			updateAndDrawaHand(x, "Hand of player "+ (x+1) );
		}
		
	}

	private void loadBoardImage() {
		try {
			
			draw = new DrawingBoard(board, frame, label);
			draw.repaint();
			setPanels(label);

		} catch (IOException e) {
			System.out.println("File not found " + e.getMessage());
		}

	}
	
	public void updateAndDrawaHand(int playerNumber, String playerName){
		Set<String> hand = new HashSet<String>();
		hand.add(playerName);
		hand.addAll(board.currentPlayers.get(playerNumber).playerData.hand.keySet());
		JComboBox<String> options = new JComboBox<String>(hand.toArray(new String[hand.size()]));
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(200, 200);
		panel.setPreferredSize(new Dimension(800, 800));
		options.setLocation(300, playerNumber *25);
		options.setSize(150, 20);
		panel.add(options);
		panels.add(panel);
		addPanel(panel, BorderLayout.AFTER_LINE_ENDS);
	}
	
	public void updateImage(){
		loadBoardImage();
		for(JPanel panel: panels){
			removePanel(panel);
		}
		updateAndDrawHand();
		test++;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public void gameEnd(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void displayMessage(ArrayList<String> messages, JPanel messageBoard) {
		final JTextArea cs = new JTextArea();

		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Console");
		border.setTitleJustification(TitledBorder.LEFT);
		cs.setBorder(border);
		cs.setEditable(false);
		cs.setPreferredSize(new Dimension((int) (WIDTH_SCALE * SCREEN_WIDTH), (int) (HEIGHT_SCALE * SCREEN_HEIGHT)));
		cs.setFont(cs.getFont().deriveFont(28f));
		messageBoard.add(cs);
		for(int i = 0; i < messages.size(); i++) {
			cs.append(messages.get(i));
		}
		cs.setForeground(Color.RED);
		addPanel(messageBoard, BorderLayout.EAST);
		messages.clear();
	}
	
	public void showPlayerHand(){
		System.out.println(board.currentPlayerIndex);
		DiscardCard pickCardsToBeDiscard = new DiscardCard(this, board, gameSetup);
		pickCardsToBeDiscard.pickCardsPrompt();
	}

}
