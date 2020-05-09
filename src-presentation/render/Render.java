package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EnumMap;
import java.util.Map;

import game.GameColor;
import game.city.City;
import game.player.PlayerRole;

public class Render {
	private Map<City, RenderCity> renderCities;
	private RenderPlayer[] renderPlayers;
	private Map<GameColor, Color> renderColor;
	private Map<PlayerRole, Color> playerColor;
	private Color nullColor;

	public Render(Map<City, RenderCity> cityRenderers, RenderPlayer[] renderPlayers) {
		this.renderCities = cityRenderers;
		this.renderPlayers = renderPlayers;
		renderColor = new EnumMap<>(GameColor.class);
		playerColor = new EnumMap<>(PlayerRole.class);
		nullColor = Color.BLACK;
		setupRenderColors();
		setupPlayerColor();
	}

	public void renderCitiesOnBoard(Graphics2D g, int boardWidth) {
		renderCities.forEach((city1, renderer1) -> {
			renderCities.forEach((city2, renderer2) -> {
				if (city1.isNeighboring(city2)) {
					renderer1.renderNeighborLine(g, renderer2, boardWidth);
				}
			});
		});
		renderCities.forEach((city, renderer) -> {
			renderer.renderCityOnBoard(g, this);
		});
	}

	public void renderPlayersOnBoard(Graphics2D g) {
		for (RenderPlayer renderPlayer : renderPlayers) {
			renderPlayer.renderPlayerOnBoard(g, renderCities, this);
		}
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

	public Color getRenderColor(GameColor color) {
		return renderColor.getOrDefault(color, nullColor);
	}

	public Color getPlayerColor(PlayerRole role) {
		return playerColor.getOrDefault(role, nullColor);
	}

}
