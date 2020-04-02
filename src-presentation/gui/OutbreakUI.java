package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import game.Game;

@SuppressWarnings("serial")
public class OutbreakUI extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int outbreakLevel = Game.getInstance().getOutbreakMark();
		Graphics2D g2d = (Graphics2D) g.create();
		paintText(g2d);
		for (int i = 0; i <= 8; i++) {
			this.paintOutbreakLevel(g2d, i, i <= outbreakLevel);
		}
		g2d.dispose();
	}

	private void paintText(Graphics2D g2d) {
		g2d.drawString("Outbreak", 10, 50);// TODO i18n support
	}

	private void paintOutbreakLevel(Graphics2D g2d, int level, boolean reached) {
		final int CUBE_SIZE = 40;
		final int X_OFFSET = 80;
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

}
