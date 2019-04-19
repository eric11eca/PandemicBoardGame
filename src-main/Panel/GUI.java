package Panel;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Initialize.Board;

public class GUI {
	JFrame frame;
	JPanel buttonPanel;
	public JPanel panel;
	public Board board;
	public JPanel mainPanel;

	public GUI() {
		frame = new JFrame();
		frame.setSize(1900, 1900);
		frame.show();
	}

	private void setPanels(JLabel label) {
		mainPanel = new JPanel();
		frame.add(label, BorderLayout.PAGE_END);
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
			JLabel label = new JLabel();
			DrawingBoard draw = new DrawingBoard(board, frame, label);
			draw.repaint();
			setPanels(label);

		} catch (IOException e) {
			System.out.println("File not found " + e.getMessage());
		}

	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

}
