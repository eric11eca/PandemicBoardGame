package gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.ActionType;
import game.TurnController;
import gui.GameGUI;

@SuppressWarnings("serial")
public class UIAction extends JPanel implements UI {
	private GameGUI gui;
	private TurnController turnController;
	private Map<ActionType, JButton> actionButtons;
	private JButton endTurnButton;
	private JLabel remainingActions;

	public UIAction(GameGUI gui, TurnController turnController) {
		this.gui = gui;
		this.turnController = turnController;
		JPanel buttonPanel = new JPanel(new GridLayout(2, 6));
		initButtons(buttonPanel);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(800, 100));
		add(buttonPanel, BorderLayout.CENTER);
		remainingActions = new JLabel();
		add(remainingActions, BorderLayout.NORTH);
		;
	}

	private void initButtons(JPanel buttonPanel) {
		actionButtons = new EnumMap<>(ActionType.class);
		for (ActionType actionType : ActionType.values()) {
			JButton button = createButton(actionType);
			actionButtons.put(actionType, button);
			buttonPanel.add(button);
		}
		endTurnButton = new JButton();
		endTurnButton.addActionListener(e -> endTurn());
		buttonPanel.add(endTurnButton);
	}

	private JButton createButton(ActionType actionType) {
		JButton button = new JButton();
		button.setText(actionType.toString());
		button.addActionListener(e -> performAction(actionType));
		return button;
	}

	public void performAction(ActionType action) {
		turnController.performAction(action);
		gui.update();
	}

	public void endTurn() {
		turnController.endTurn();
		turnController.startTurn();
		gui.update();
	}

	public void updateButtons() {
		actionButtons.forEach((actionType, button) -> {
			button.setEnabled(turnController.canContinueAction() && turnController.canPerformAction(actionType));
		});
	}

	@Override
	public void update() {
		updateButtons();
		remainingActions.setText("Remaining Actions: " + turnController.getRemainingActions());
	}
}
