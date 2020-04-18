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
		labelPanel.add(colorLabel, BorderLayout.NORTH);
		labelPanel.add(roleLabel, BorderLayout.CENTER);
		labelPanel.add(cityLabel, BorderLayout.SOUTH);
		add(labelPanel, BorderLayout.NORTH);
		add(handButton, BorderLayout.CENTER);
		handButton.addActionListener(e -> gui.displayPopup(new UICardChooser("Player Hands", 0,
				playerController.getPlayerHand(), render, list -> gui.hidePopup(), true)));
	}

	// @Override
	// protected void paintComponent(Graphics g) {
	// super.paintComponent(g);
	// renderPlayer.renderPlayerInfoBoard((Graphics2D) g);
//		Graphics2D g2d = (Graphics2D) g.create();
//		int ascent = g2d.getFontMetrics().getAscent();
//		int height = g2d.getFontMetrics().getHeight();
//		g2d.drawString("Player " + playerNumber, 0, ascent);
//		g2d.drawString(location.get().getName(), 0, ascent + height);
//		g2d.drawString(String.valueOf(handSize.get()), 0, ascent + height * 2);
//		g2d.dispose();
	// }

	@Override
	public void update() {
		colorLabel.setBackground(render.getPlayerColor(playerController.getRole()));
		if (turnController.isPlayerActive(playerController)) {
			setBackground(Color.WHITE);
		} else {
			setBackground(Color.GRAY);
		}
		roleLabel.setText(playerController.getRole().getRoleName());
		cityLabel.setText("Location: " + playerController.getPlayerCity().getName());
		handButton.setText("View Hand [" + playerController.getPlayerHandSize() + "]");
		repaint();
	}
}
