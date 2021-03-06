package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

import data.GameProperty;
import lang.I18n;

@SuppressWarnings("serial")
public class UIInfectionRate extends JComponent implements UI {
	private Supplier<Integer> infectionIndex;

	public UIInfectionRate(Supplier<Integer> infectionIndex) {
		this.infectionIndex = infectionIndex;
		this.setPreferredSize(new Dimension(300, 30));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int infectIndex = infectionIndex.get();
		Graphics2D g2d = (Graphics2D) g.create();
		paintText(g2d);
		final int[] RATES = GameProperty.getInstance().getIntArray("INFECTION_RATES");
		for (int i = 0; i < RATES.length; i++) {
			this.paintInfection(g2d, i, RATES, i <= infectIndex);
		}
		g2d.dispose();
	}

	private void paintText(Graphics2D g2d) {
		g2d.drawString(I18n.format("infection.rate"), 10, g2d.getFontMetrics().getAscent());// TODO i18n support
	}

	private void paintInfection(Graphics2D g2d, int level, int[] rates, boolean reached) {
		final int CUBE_SIZE = 20;
		final int X_OFFSET = 100;
		final int Y_OFFSET = 10;

		int x = X_OFFSET + level * CUBE_SIZE;
		g2d.setColor(reached ? Color.GREEN : Color.LIGHT_GRAY);
		g2d.fillRect(x, Y_OFFSET, CUBE_SIZE, CUBE_SIZE);
		g2d.setColor(reached ? Color.WHITE : Color.BLACK);
		g2d.drawString(String.valueOf(rates[level]), x + CUBE_SIZE / 2, Y_OFFSET + CUBE_SIZE / 2);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(x, Y_OFFSET, CUBE_SIZE, CUBE_SIZE);
	}

	@Override
	public void update() {
		repaint();
	}

}
