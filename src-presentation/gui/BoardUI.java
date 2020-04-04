package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.swing.JComponent;

import data.Board;
import game.GameState;
import game.city.City;
import game.player.PlayerImpl;
import image.ImageLoader;
import render.Render;
import render.RenderCity;

@SuppressWarnings("serial")
public class BoardUI extends JComponent {
	private Map<City, RenderCity> cityRenderers;

	public BoardUI(Map<City, RenderCity> cityRenderers) {
		this.cityRenderers = cityRenderers;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		drawBoard(g2d);
		g2d.dispose();
	}

	private void drawBoard(Graphics2D g2d) {
		Render render = new Render(g2d);
		Image background = ImageLoader.getInstance().getImage("Main Picture.png");
		g2d.drawImage(background, 0, 0, null);

		// if (!board.infectionRateTracker.isEmpty()) {
		// drawInfect(g2d, 7 - board.infectionRateTracker.size());
		// }

		// drawOutBreaks(g2d, Game.getInstance().getOutbreakMark());

		cityRenderers.values().forEach(r -> r.drawCity(render));

//		String uncureMark = board.messages.getString("UNCURED");
//		g2d.setColor(Color.RED);
//		g2d.setFont(new Font(uncureMark, 12, 12));
//		g2d.drawString(uncureMark, 425, 770);
//		g2d.drawString(uncureMark, 425 + 68, 770);
//		g2d.drawString(uncureMark, 425 + (68 * 2), 770);
//		g2d.drawString(uncureMark, 425 + (68 * 3), 770);

		// int currentPlayerNum = 0;
		// for (Player player : board.currentPlayers) {
		// currentPlayerNum++;
		// drawPlayer(g2d, player.playerData.location,
		// Integer.toString(currentPlayerNum));
		// }

		// drawCards(g2d, board.validPlayerCards.size());
	}

	// TODO move
	private void drawInfect(Graphics2D g2d, int i) {
		g2d.setFont(new Font("X", Font.BOLD, 30));
		g2d.drawString("X", 900 + (i * 50), 55);
	}

	// TODO move
	private void drawCards(Graphics2D g2d, int i) {
		// String cardCount =
		// MessageFormat.format(board.messages.getString("cardsLeft"), i);
		// g2d.setFont(new Font(cardCount, Font.BOLD, 14));
		// g2d.drawString(cardCount, 875, 700);
	}

	// TODO move
	private void drawOutBreaks(Graphics2D g2d, int i) {
		g2d.setFont(new Font("X", Font.BOLD, 30));
		g2d.drawString("X", 50 + ((i % 2) * 50), 395 + (i * 42));
	}

	// TODO move to RenderPlayer
//	private void drawPlayer(Graphics2D g2d, City city, String k) {
//		g2d.setColor(Color.YELLOW);
//		String playerLabel = MessageFormat.format("P {0}", k);
//		g2d.setFont(new Font(playerLabel, Font.BOLD, 12));
//		g2d.drawString(playerLabel, city.x - 50 + (20 * Integer.parseUnsignedInt(k)), city.y - 30);
//	}

}