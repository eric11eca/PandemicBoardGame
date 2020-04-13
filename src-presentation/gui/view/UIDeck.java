package gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class UIDeck extends JComponent {
	private String name;
	private Color color;
	private Supplier<Integer> deckSize;

	public UIDeck(String name, Color color, Supplier<Integer> deckSize) {
		super();
		this.name = name;
		this.color = color;
		this.deckSize = deckSize;
		this.setToolTipText(name);
		setPreferredSize(new Dimension(200, 100));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.BLACK);
		g2d.drawString(name, 0, g2d.getFontMetrics().getAscent());
		g2d.drawString(String.valueOf(deckSize.get()), 0,
				g2d.getFontMetrics().getAscent() + g2d.getFontMetrics().getHeight());
		g2d.dispose();
	}
}
