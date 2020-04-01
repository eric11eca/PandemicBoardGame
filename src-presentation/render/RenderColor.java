package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EnumMap;
import java.util.Map;

import data.GameColor;

public class RenderColor {
	private Graphics2D g;
	private Map<GameColor, Color> renderColor;
	private Color nullColor;

	public RenderColor(Graphics2D g) {
		this.g = g;
		renderColor = new EnumMap<>(GameColor.class);
		nullColor = Color.WHITE;
		setupRenderColors();
	}

	private void setupRenderColors() {
		renderColor.put(GameColor.BLACK, Color.BLACK);
		renderColor.put(GameColor.BLUE, Color.BLUE);
		renderColor.put(GameColor.RED, Color.RED);
		renderColor.put(GameColor.YELLOW, Color.YELLOW);
	}

	public void setRenderColor(GameColor color) {
		g.setColor(renderColor.getOrDefault(color, nullColor));
	}
}
