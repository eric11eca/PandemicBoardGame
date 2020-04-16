package render;

import java.util.Map;

import game.city.City;
import game.player.PlayerController;

public class RenderInitialization {
	private Render render;

	public RenderInitialization(Map<City, RenderCity> cityRenderers) {
		render = new Render(cityRenderers);
	}

	public Render getRender() {
		return render;
	}

	public RenderPlayer[] createPlayerRenderers(PlayerController[] playerControllers) {
		RenderPlayer[] playerRenderers = new RenderPlayer[playerControllers.length];
		for (int i = 0; i < playerControllers.length; i++) {
			playerRenderers[i] = new RenderPlayer(render, i, playerControllers[i]);
		}
		return playerRenderers;
	}

	public RenderCity createRenderCity(int x, int y, City city) {
		return new RenderCity(render, x, y, city);
	}
}
