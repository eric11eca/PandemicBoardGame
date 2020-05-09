package gui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.TurnController;
import game.player.PlayerController;
import gui.GameGUI;
import gui.interaction.UICardChooser;
import lang.I18n;
import render.Render;

@SuppressWarnings("serial")
public class UIPlayer extends JPanel implements UI {

//	private RenderPlayer renderPlayer;

	private JLabel colorLabel;
	private JLabel roleLabel;
	private JLabel cityLabel;
	private JButton handButton;
	private TurnController turnController;
	private PlayerController playerController;
	private Render render;
	private GameGUI gui;

	public UIPlayer(GameGUI gui, Render render, PlayerController playerController, TurnController turnController) {
		this.render = render;
		this.gui = gui;
		this.playerController = playerController;
		this.turnController = turnController;
		// this.renderPlayer = renderPlayer;
		colorLabel = new JLabel(" ");
		colorLabel.setOpaque(true);
		roleLabel = new JLabel();
		cityLabel = new JLabel();
		handButton = new JButton();
		setLayout(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		labelPanel.setOpaque(false);
		labelPanel.add(colorLabel, BorderLayout.NORTH);
		labelPanel.add(roleLabel, BorderLayout.CENTER);
		labelPanel.add(cityLabel, BorderLayout.SOUTH);
		add(labelPanel, BorderLayout.NORTH);
		add(handButton, BorderLayout.CENTER);
		handButton.addActionListener(
				e -> gui.displayPopup(I18n.format("gui.view_hand", playerController.getPlayerHandSize()),
						new UICardChooser(0, playerController.getPlayerHand(), render, list -> gui.hidePopup(), true)));
	}

	@Override
	public void update() {
		colorLabel.setBackground(render.getPlayerColor(playerController.getRole()));
		if (turnController.isPlayerActive(playerController)) {
			setBackground(Color.WHITE);
		} else {
			setBackground(Color.LIGHT_GRAY);
		}
		roleLabel.setText(playerController.getRole().getRoleName());
		cityLabel.setText(I18n.format("gui.location", (playerController.getPlayerCity().getName())));
		handButton.setText(I18n.format("gui.view_hand", playerController.getPlayerHandSize()));
		repaint();
	}
}
