package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

import lang.I18n;

@SuppressWarnings("serial")
public class UIOutbreak extends JComponent implements UI {
	private Supplier<Integer> outbreakLevel;

	public UIOutbreak(Supplier<Integer> outbreakLevel) {
		this.outbreakLevel = outbreakLevel;
		this.setPreferredSize(new Dimension(300, 30));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int outbreak = outbreakLevel.get();
		Graphics2D g2d = (Graphics2D) g.create();
		paintText(g2d);
		for (int i = 0; i <= 8; i++) {
			this.paintOutbreakLevel(g2d, i, i <= outbreak);
		}
		g2d.dispose();
	}

	private void paintText(Graphics2D g2d) {
		g2d.drawString(I18n.format("outbreak"), 10, g2d.getFontMetrics().getAscent());// TODO i18n support
	}

	private void paintOutbreakLevel(Graphics2D g2d, int level, boolean reached) {
		final int CUBE_SIZE = 20;
		final int X_OFFSET = 100;
		final int Y_OFFSET = 10;
		int x = X_OFFSET + level * CUBE_SIZE;
		g2d.setColor(reached ? Color.RED : Color.LIGHT_GRAY);
		g2d.fillRect(x, Y_OFFSET, CUBE_SIZE, CUBE_SIZE);
		g2d.setColor(reached ? Color.WHITE : Color.BLACK);
		g2d.drawString(String.valueOf(level), x + CUBE_SIZE / 2, Y_OFFSET + CUBE_SIZE / 2);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(x, Y_OFFSET, CUBE_SIZE, CUBE_SIZE);
	}

	@Override
	public void update() {
		repaint();
	}

}
