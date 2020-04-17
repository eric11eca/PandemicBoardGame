package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MessageUI extends JPanel {
	private GameGUI gui;

	public MessageUI(String message) {
		JTextArea messageArea = new JTextArea(message);
		messageArea.setText(message);
		messageArea.setEditable(false);
		setLayout(new BorderLayout());
		add(messageArea, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		JButton okButton = new JButton();
		buttonPanel.add(okButton);
	}

}
