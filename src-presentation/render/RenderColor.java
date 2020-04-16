package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EnumMap;
import java.util.Map;

import game.GameColor;
import game.player.PlayerRole;

public class RenderColor {
	private Map<GameColor, Color> renderColor;
	private Map<PlayerRole, Color> playerColor;
	private Color nullColor;

	public RenderColor() {
		renderColor = new EnumMap<>(GameColor.class);
		playerColor = new EnumMap<>(PlayerRole.class);
		nullColor = Color.BLACK;
		setupRenderColors();
		setupPlayerColor();
	}

	private void setupPlayerColor() {
		playerColor.put(PlayerRole.CONTINGENCY_PLANNER, Color.CYAN);
		playerColor.put(PlayerRole.DISPATCHER, Color.PINK);
		playerColor.put(PlayerRole.MEDIC, Color.ORANGE);
		playerColor.put(PlayerRole.OPERATION_EXPERT, Color.GREEN.brighter());
		playerColor.put(PlayerRole.QUARANTINE_SPECIALIST, Color.GREEN.darker());
		playerColor.put(PlayerRole.RESEARCHER, new Color(112, 66, 25));
		playerColor.put(PlayerRole.SCIENTIST, new Color(200, 200, 200));
	}

	private void setupRenderColors() {
		renderColor.put(GameColor.BLACK, Color.BLACK);
		renderColor.put(GameColor.BLUE, Color.BLUE);
		renderColor.put(GameColor.RED, Color.RED);
		renderColor.put(GameColor.YELLOW, Color.YELLOW.darker());
	}

	public void setRenderColor(Graphics2D g, GameColor color) {
		g.setColor(renderColor.getOrDefault(color, nullColor));
	}

	public void setPlayerColor(Graphics2D g, PlayerRole role) {
		g.setColor(playerColor.getOrDefault(role, nullColor));
	}
}
