package gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.TurnController;
import game.TurnStage;
import game.player.action.ActionType;
import gui.GameGUI;
import lang.I18n;

@SuppressWarnings("serial")
public class UIAction extends JPanel implements UI {
	private GameGUI gui;
	private TurnController turnController;
	private Map<ActionType, JButton> actionButtons;
	private JButton nextStageButton;
	private JLabel remaining;

	public UIAction(GameGUI gui, TurnController turnController) {
		this.gui = gui;
		this.turnController = turnController;
		JPanel buttonPanel = new JPanel(new GridLayout(2, 6));
		initButtons(buttonPanel);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(800, 100));
		add(buttonPanel, BorderLayout.CENTER);
		remaining = new JLabel("", SwingConstants.CENTER);
		remaining.setFont(remaining.getFont().deriveFont(80f));
		remaining.setPreferredSize(new Dimension(remaining.getFontMetrics(remaining.getFont()).stringWidth("00"), 100));
		add(remaining, BorderLayout.WEST);

	}

	private void initButtons(JPanel buttonPanel) {
		actionButtons = new EnumMap<>(ActionType.class);
		for (ActionType actionType : ActionType.values()) {
			JButton button = createButton(actionType);
			actionButtons.put(actionType, button);
			buttonPanel.add(button);
		}
		nextStageButton = new JButton();
		nextStageButton.addActionListener(e -> endTurn());
		buttonPanel.add(nextStageButton);
	}

	private JButton createButton(ActionType actionType) {
		JButton button = new JButton();
		button.setText(actionType.getName());
		button.addActionListener(e -> performAction(actionType));
		return button;
	}

	public void performAction(ActionType action) {
		turnController.performAction(action);
		gui.update();
	}

	public void endTurn() {
		if (turnController.getStage() == TurnStage.PLAYER_ACTION) {

			int option = JOptionPane.showConfirmDialog(gui.getGameFrame(), I18n.format("end_turn.confirm.text"),
					I18n.format("end_turn.confirm.title"), JOptionPane.OK_CANCEL_OPTION);
			if (option != JOptionPane.OK_OPTION)
				return;

		}
		gui.hidePopup();
		turnController.nextStage();
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
		// remainingLabel.setText(turnController.getStage().getStageText());
		remaining.setText("" + turnController.getRemainingOperations());
		nextStageButton.setText(turnController.getStage().getButtonName());

	}
}
