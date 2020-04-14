package gui.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.ActionType;
import game.TurnController;

@SuppressWarnings("serial")
public class UIAction extends JPanel {
	private TurnController turnController;
	private Map<ActionType, JButton> actionButtons;
	private JButton endTurnButton;

	public UIAction(TurnController turnController) {
		this.turnController = turnController;
		initButtons();
		this.setLayout(new GridLayout(2, 6));
		this.setPreferredSize(new Dimension(800, 100));
	}

	private void initButtons() {
		actionButtons = new EnumMap<>(ActionType.class);
		for (ActionType actionType : ActionType.values()) {
			JButton button = createButton(actionType);
			actionButtons.put(actionType, button);
			add(button);
		}
		endTurnButton = new JButton();
		endTurnButton.addActionListener(e -> endTurn());
		add(endTurnButton);

	}

	private JButton createButton(ActionType actionType) {
		JButton button = new JButton();
		button.setText(actionType.toString());
		button.addActionListener(e -> performAction(actionType));
		return button;
	}

	public void performAction(ActionType action) {
		turnController.performAction(action);
	}

	public void endTurn() {
		turnController.endTurn();
		turnController.startTurn();
	}
}
