package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.MessageFormat;
import java.util.Map;

import game.GameColor;
import game.city.City;
import game.player.PlayerRole;

public class Render {
	private Map<City, RenderCity> cityRenderers;
	// private Graphics2D g;
	private RenderColor renderColor;

	public Render(Map<City, RenderCity> cityRenderers) {
		this.cityRenderers = cityRenderers;
		// this.g = g;
		renderColor = new RenderColor();
	}

	public void renderCitiesOnBoard(Graphics2D g, int boardWidth) {
		cityRenderers.forEach((city1, renderer1) -> {
			cityRenderers.forEach((city2, renderer2) -> {
				if (city1.isNeighboring(city2)) {
					renderer1.renderNeighborLine(g, renderer2, boardWidth);
				}
			});
		});
		cityRenderers.forEach((city, renderer) -> {
			renderer.renderCityOnBoard(g);
		});
	}

	public void drawDiseaseCube(Graphics2D g, int x, int y, int i, GameColor color) {
		final int OUT_SIZE = 22;
		final int INNER_SIZE = 16;
		final int CUBE_PER_LINE = 6;
		int xx = i % CUBE_PER_LINE;
		int yy = i / CUBE_PER_LINE;
		g.setColor(Color.WHITE);
		g.fillRect(xx * OUT_SIZE + x, yy * OUT_SIZE + y, OUT_SIZE, OUT_SIZE);
		renderColor.setRenderColor(g, color);
		int offset = (OUT_SIZE - INNER_SIZE) / 2;
		g.fillRect(xx * OUT_SIZE + offset + x, yy * OUT_SIZE + offset + y, INNER_SIZE, INNER_SIZE);
	}

	public void renderPlayerAtCity(Graphics2D g, int id, PlayerRole role, City playerCity) {
		g.setColor(Color.YELLOW);
		RenderCity r = cityRenderers.get(playerCity);
		r.renderPlayer(g, id, role);
	}

}
