package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import ButtonListeners.DiscardCard;
import ButtonListeners.EventCardListener;
import Card.PlayerCard;
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
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
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
		updateAndDrawHand();

	}

	private void updateAndDrawHand() {
		JPanel panel = new JPanel();
		int x = 0;
		for (x = 0; x < board.playernumber; x++) {
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
		JComboBox<String> eventCards = makeEventCardOptions();
		eventCards.setLocation(300, (x + 1) * 25);
		eventCards.setSize(150, 20);
		panel.add(eventCards);
		JButton eventButton = new JButton("Play Event Card");
		eventButton.addActionListener(new EventCardListener(board, eventCards));
		eventButton.setLocation(300, (x + 2) * 25);
		eventButton.setSize(150, 20);
		panel.add(eventButton);
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
			System.out.println("File not found " + e.getMessage());
		}

	}

	public void updateImage() {
		loadBoardImage();
		for (JPanel panel : panels) {
			removePanel(panel);
		}
		updateAndDrawHand();
		test++;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
		mainPanel.add(buttonPanel);
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
		for (int i = 0; i < messages.size(); i++) {
			cs.append(messages.get(i));
		}
		cs.setForeground(Color.RED);
		addPanel(messageBoard, BorderLayout.EAST);
		messages.clear();
	}

	public void showPlayerHand() {
		System.out.println(board.currentPlayerIndex);
		DiscardCard pickCardsToBeDiscard = new DiscardCard(this, board, gameSetup);
		pickCardsToBeDiscard.pickCardsPrompt();
	}

}
