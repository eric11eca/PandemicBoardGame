package gui.interaction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameColor;
import game.player.Player;
import gui.view.UI;

@SuppressWarnings("serial")
public class UIPlayerChooser extends JPanel implements UI {
	public UIPlayerChooser(String title, List<Player> toChoose, Consumer<Player> action) {
		setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel(title);
		add(titleLabel, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
		for (Player choice : toChoose) {
			JButton button = new JButton(choice.getRole().getRoleName());
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
