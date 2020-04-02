package gui;

import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LogoUI extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("PANDEMIC", 0, g.getFontMetrics().getHeight());
	}

}
