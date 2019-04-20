package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import Initialize.Board;

public class GUI {
	JFrame frame;
	JPanel buttonPanel;
	public JPanel panel;
	public Board board;
	public JPanel mainPanel;
	DrawingBoard draw;
	JLabel label = new JLabel();
	
	static final int SCREEN_HEIGHT = 1080;
	static final int SCREEN_WIDTH = 1920;
	static final double WIDTH_SCALE = 0.4;
	private static final double HEIGHT_SCALE = 1.8;
	
	public static final String WINING_MESSAGE = "\n CONGRADULATIONS, \n YOU WIN!";
	public static final String LOSING_MESSAGE = "\n SORRY, YOU LOSE, \n WNNA TRY AGAIN?";

	public GUI() {
		frame = new JFrame();
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
	
	public void updateImage(){
		loadBoardImage();
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public void gameEnd(String message) {
		JPanel messageBoard = new JPanel();
		final JTextArea cs = new JTextArea();

		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Console");
		border.setTitleJustification(TitledBorder.LEFT);
		cs.setBorder(border);
		cs.setEditable(false);
		cs.setPreferredSize(new Dimension((int) (WIDTH_SCALE * SCREEN_WIDTH), (int) (HEIGHT_SCALE * SCREEN_HEIGHT)));
		cs.setFont(cs.getFont().deriveFont(48f));
		messageBoard.add(cs);
		cs.append(message);
		cs.setForeground(Color.RED);
		addPanel(messageBoard, BorderLayout.EAST);
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
	}

}
