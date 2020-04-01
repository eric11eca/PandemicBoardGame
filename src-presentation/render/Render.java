package render;

import java.awt.Color;
import java.awt.Graphics2D;

import data.GameColor;

public class Render {
	private Graphics2D g;
	private RenderColor renderColor;

	public Render(Graphics2D g) {
		this.g = g;
		renderColor = new RenderColor(g);
	}

	public void drawDiseaseCube(int x, int y, GameColor color) {
		renderColor.setRenderColor(color);
		g.fillRect(x, y, 7, 7);
	}

	public void drawResearchStation(int x, int y) {
		g.setColor(new Color(228, 180, 34));
		g.fillRect(x - 5, y - 5, 10, 10);
	}

}
