package gui.interaction;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import gui.GameGUI;
import gui.view.UI;

@SuppressWarnings("serial")
public class UIMessage extends JPanel implements UI {
	private GameGUI gui;

	public UIMessage(String message) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
		JTextArea messageArea = new JTextArea();
		messageArea.setText(message);
		messageArea.setEditable(false);
		setLayout(new BorderLayout());
		add(messageArea, BorderLayout.CENTER);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
