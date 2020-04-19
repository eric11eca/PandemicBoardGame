package gui.interaction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameColor;
import gui.view.UI;

@SuppressWarnings("serial")
public class UIColorChooser extends JPanel implements UI {
	public UIColorChooser(Set<GameColor> toChoose, Consumer<GameColor> action) {
		setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel("Choose a color:");
		add(titleLabel, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		for (GameColor choice : toChoose) {
			JButton button = new JButton(choice.toString());
			button.addActionListener(e -> action.accept(choice));
			buttonPanel.add(button);
		}
		add(buttonPanel, BorderLayout.CENTER);
	}

	@Override
	public void update() {
		repaint();
	}
}
