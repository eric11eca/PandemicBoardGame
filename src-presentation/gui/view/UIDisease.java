package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Set;

import javax.swing.JComponent;

import game.GameState;
import game.GameColor;
import game.disease.GameCubePool;
import render.Render;
import render.RenderColor;

@SuppressWarnings("serial")
public class UIDisease extends JComponent {
	private final int CUBE_SIZE = 40;
	private Set<GameColor> curedDisease;
	private GameCubePool gameCubePool;

	public UIDisease(Set<GameColor> curedDisease, GameCubePool gameCubePool) {
		super();
		this.curedDisease = curedDisease;
		this.gameCubePool = gameCubePool;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		paintText(g2d);
		int x = 80;
		int y = 10;
		for (GameColor color : GameColor.values()) {
			this.paintDisease(g2d, color, x, y);
			x += CUBE_SIZE * 2;
		}
		g2d.dispose();
	}

	private void paintText(Graphics2D g2d) {
		g2d.drawString("Disease", 10, 50);// TODO i18n support
	}

	private void paintDisease(Graphics2D g2d, GameColor color, int x, int y) {

		boolean cured = curedDisease.contains(color);
		boolean eradicated = gameCubePool.isDiseaseEradicated(color);
		if (cured || eradicated) {
			RenderColor renderColor = new RenderColor(g2d);
			renderColor.setRenderColor(color);
		} else {
			g2d.setColor(Color.DARK_GRAY);
		}
		g2d.fillRect(x, y, CUBE_SIZE, CUBE_SIZE);
		if (eradicated) {
			g2d.setColor(Color.GREEN);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawLine(x, y, x + CUBE_SIZE, y + CUBE_SIZE);
			g2d.drawLine(x, y + CUBE_SIZE, x + CUBE_SIZE, y);
		}
	}

}
