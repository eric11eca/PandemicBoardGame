package gui.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.TurnController;

@SuppressWarnings("serial")
public class UITurn extends JPanel implements UI {
	private JLabel label;
	private TurnController turnController;

	public UITurn(TurnController turnController) {
		setLayout(new BorderLayout());
		label = new JLabel();
		add(label);
		this.turnController = turnController;
	}

	@Override
	public void update() {
		label.setText(turnController.getStage().getStageText());
	}

}
