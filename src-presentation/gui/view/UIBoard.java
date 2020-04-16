package gui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.text.MessageFormat;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import data.ImageLoader;
import game.city.City;
import game.player.PlayerController;
import render.Render;
import render.RenderCity;
import render.RenderPlayer;

@SuppressWarnings("serial")
public class UIBoard extends JPanel implements UI {
	private Render render;
	private RenderPlayer[] renderPlayers;

	public UIBoard(Render render, RenderPlayer[] renderPlayers) {
		this.render = render;
		this.renderPlayers = renderPlayers;
		this.setBackground(new Color(51, 103, 153));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		drawBoard(g2d);
		g2d.dispose();
	}

	private void drawBoard(Graphics2D g2d) {

		Image background = ImageLoader.getInstance().getImage("map.png");

		float scaleX = (float) getWidth() / background.getWidth(null);
		float scaleY = (float) getHeight() / background.getHeight(null);
		float scale = Math.min(scaleX, scaleY);
		float offX = (scaleX - scale) * background.getWidth(null) / 2;
		float offY = (scaleY - scale) * background.getHeight(null) / 2;
		g2d.translate(offX, 0);
		g2d.scale(scale, scale);

		g2d.drawImage(background, 0, 0, null);

		render.renderCitiesOnBoard(g2d, background.getWidth(null));

		for (RenderPlayer renderPlayer : renderPlayers) {
			renderPlayer.renderPlayerOnBoard(g2d);
		}

	}

	@Override
	public void update() {
		repaint();
	}

}