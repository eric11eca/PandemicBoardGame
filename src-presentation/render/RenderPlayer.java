package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import game.TurnController;
import game.city.City;
import game.player.PlayerController;

public class RenderPlayer {

	private int id;
	private PlayerController playerController;
	private TurnController turnController;

	public RenderPlayer(int id, PlayerController playerController, TurnController turnController) {
		this.id = id;
		this.playerController = playerController;
		this.turnController = turnController;
	}

	public void renderPlayerOnBoard(Graphics2D g2d, Map<City, RenderCity> renderCities, Render render) {
		RenderCity renderCity = renderCities.get(playerController.getPlayerCity());
		renderCity.renderPlayer(g2d, id, this, render);
	}

	public void renderPlayerAt(Graphics2D g2d, int x, int y, int size, Render render) {

		if (turnController.isPlayerActive(playerController)) {
			g2d.setColor(Color.WHITE);
		} else {
			g2d.setColor(Color.GRAY);
		}
		g2d.fillRect(x + 2, y - 10, size - 4, size + 10);
		g2d.setColor(render.getPlayerColor(playerController.getRole()));
		g2d.fillOval(x, y - size, size, size);
	}

}
