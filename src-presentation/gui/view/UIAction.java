package gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.TurnController;
import game.player.action.ActionType;
import gui.GameGUI;

@SuppressWarnings("serial")
public class UIAction extends JPanel implements UI {
	private GameGUI gui;
	private TurnController turnController;
	private Map<ActionType, JButton> actionButtons;
	private JButton endTurnButton;
	private JLabel remainingLabel;
	private JLabel remaining;

	public UIAction(GameGUI gui, TurnController turnController) {
		this.gui = gui;
		this.turnController = turnController;
		JPanel buttonPanel = new JPanel(new GridLayout(2, 6));
		initButtons(buttonPanel);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(800, 100));
		add(buttonPanel, BorderLayout.CENTER);
		JPanel westPanel = new JPanel(new BorderLayout());
		remainingLabel = new JLabel("Remaining Actions");
		westPanel.add(remainingLabel, BorderLayout.NORTH);
		remaining = new JLabel("", SwingConstants.CENTER);
		remaining.setFont(remaining.getFont().deriveFont(80f));
		westPanel.setPreferredSize(new Dimension(200, 100));
		westPanel.add(remaining, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);

	}

	private void initButtons(JPanel buttonPanel) {
		actionButtons = new EnumMap<>(ActionType.class);
		for (ActionType actionType : ActionType.values()) {
			JButton button = createButton(actionType);
			actionButtons.put(actionType, button);
			buttonPanel.add(button);
		}
		endTurnButton = new JButton("End Turn");
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
		gui.update();
	}

	public void updateButtons() {
		actionButtons.forEach((actionType, button) -> {
			button.setEnabled(turnController.canPerformAction(actionType));
		});
	}

	@Override
	public void update() {
		updateButtons();
		if (turnController.isInfectionStage()) {
			remainingLabel.setText("Remaining Infection");
			remaining.setText("" + turnController.getRemainingInfection());
		} else {
			remainingLabel.setText("Remaining Actions");
			remaining.setText("" + turnController.getRemainingActions());
		}

	}
}
