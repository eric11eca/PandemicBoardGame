package render;

import java.awt.Graphics2D;

import game.player.PlayerController;

public class RenderPlayer {

	private Render render;
	private int id;
	private PlayerController playerController;

	public RenderPlayer(Render render, int id, PlayerController playerController) {
		this.render = render;
		this.id = id;
		this.playerController = playerController;
	}

	public void renderPlayerInfoBoard(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		int ascent = g2d.getFontMetrics().getAscent();
		int height = g2d.getFontMetrics().getHeight();
		// new RenderColor().setPlayerColor(g2d, playerController.getRole());
		g2d.drawString(playerController.getRole().getRoleName(), 0, ascent + height * 1);
		g2d.drawString(playerController.getPlayerCity().getName(), 0, ascent + height * 2);
		g2d.drawString(String.valueOf(playerController.getPlayerHandSize()), 0, ascent + height * 3);
		g2d.dispose();
	}

	public void renderPlayerOnBoard(Graphics2D g) {
		render.renderPlayerAtCity(g, id, playerController.getRole(), playerController.getPlayerCity());
	}
}
