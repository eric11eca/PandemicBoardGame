package render;

import java.awt.Graphics2D;

import game.GameColor;
import game.city.City;

public class RenderCity {
	private int x;
	private int y;
	private City city;

	public RenderCity(int x, int y, City city) {
		this.x = x;
		this.y = y;
		this.city = city;
	}

	public void drawCity(Render render) {
		if (city.hasResearchStation()) {
			drawResearchStation(render);
		}
		drawCubes(render);
	}

	private void drawResearchStation(Render render) {
		render.drawResearchStation(x, y);
	}

	public void drawPlayer(Graphics2D g2d, int p) {
		g2d.drawString(String.valueOf(p), x - 50 + (20 * p), y - 30);
	}

	private void drawCubes(Render render) {
		for (int c = city.getDiseaseCubeCount(GameColor.RED); c > 0; c--) {
			render.drawDiseaseCube(x - 20, y - 12 + (10 * c), GameColor.RED);
		}
		for (int c = city.getDiseaseCubeCount(GameColor.YELLOW); c > 0; c--) {
			render.drawDiseaseCube(x + 20, y - 12 + (10 * c), GameColor.YELLOW);
		}
		for (int c = city.getDiseaseCubeCount(GameColor.BLACK); c > 0; c--) {
			render.drawDiseaseCube(x - 12 + (10 * c), y - 20, GameColor.BLACK);
		}
		for (int c = city.getDiseaseCubeCount(GameColor.BLUE); c > 0; c--) {
			render.drawDiseaseCube(x - 12 + (10 * c), y + 20, GameColor.BLUE);
		}
//		for (GameColor gameColor:GameColor.values()) {
//			
//			while (cubes > 0) {
//				if (color.equals(Color.RED)) {
//					
//				} else if (color.equals(Color.YELLOW)) {
//					render.drawDiseaseCube(, color);
//				} else if (color.equals(Color.BLACK)) {
//					render.drawDiseaseCube(,  color);
//				} else if (color.equals(Color.BLUE)) {
//					render.drawDiseaseCube( color);
//				} else {
//					System.out.println("Critical Failure in drawing board draw cubes");
//					return;
//				}
//				cubes--;
//			}
//		}
	}
}
