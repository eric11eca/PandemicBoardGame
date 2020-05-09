package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Function;

import javax.swing.JComponent;

import game.GameColor;
import lang.I18n;
import render.Render;

@SuppressWarnings("serial")
public class UIDisease extends JComponent implements UI {
	private final int CUBE_SIZE = 20;
	private Function<GameColor, Boolean> isDiseaseCured;
	private Function<GameColor, Boolean> isDiseaseEradicated;
	private Function<GameColor, Integer> diseaseCount;
	private Render render;

	public UIDisease(Render render, Function<GameColor, Boolean> isDiseaseCured,
			Function<GameColor, Boolean> isDiseaseEradicated, Function<GameColor, Integer> diseaseCount) {
		this.render = render;
		this.isDiseaseCured = isDiseaseCured;
		this.isDiseaseEradicated = isDiseaseEradicated;
		this.diseaseCount = diseaseCount;
		this.setPreferredSize(new Dimension(400, 30));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		paintText(g2d);
		int x = 100;
		int y = 10;
		for (GameColor color : GameColor.values()) {
			this.paintDisease(g2d, color, x, y);
			x += CUBE_SIZE * 2;
		}
		g2d.dispose();
	}

	private void paintText(Graphics2D g2d) {
		g2d.drawString(I18n.format("disease"), 10, g2d.getFontMetrics().getAscent());// TODO i18n support
	}

	private void paintDisease(Graphics2D g2d, GameColor color, int x, int y) {

		boolean cured = isDiseaseCured.apply(color);
		boolean eradicated = isDiseaseEradicated.apply(color);
		if (cured || eradicated) {
			g2d.setColor(render.getRenderColor(color));
		} else {
			g2d.setColor(Color.DARK_GRAY);
		}
		g2d.fillRect(x, y, CUBE_SIZE, CUBE_SIZE);
		g2d.setColor(Color.BLACK);
		g2d.drawString(diseaseCount.apply(color) + "", x, y - 1);
		if (eradicated) {
			g2d.setColor(Color.GREEN);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawLine(x, y, x + CUBE_SIZE, y + CUBE_SIZE);
			g2d.drawLine(x, y + CUBE_SIZE, x + CUBE_SIZE, y);
		}
	}

	@Override
	public void update() {
		repaint();
	}

}
