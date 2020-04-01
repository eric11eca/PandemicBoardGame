package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import data.Board;
import game.city.City;
import player.Player;
import render.Render;
import render.RenderCity;

public class DrawingBoard {
	private Map<City, RenderCity> cityRenderers;

	private Board board;
	public Graphics2D graphic;
	JFrame frame;
	public BufferedImage img;
	JLabel label;

	public DrawingBoard(Board board, JFrame frame, JLabel label, Map<City, RenderCity> cityRenderers)
			throws IOException {
		this.board = board;
		this.label = label;
		File picture = new File("Main Picture.png");
		img = ImageIO.read(picture);
		graphic = img.createGraphics();
		this.frame = frame;
		this.cityRenderers = cityRenderers;
	}

	// =========REFACTOR NEEDED============*/
	public void repaint() throws IOException {

		File picture = new File("Main Picture.png");
		img = ImageIO.read(picture);
		graphic = img.createGraphics();

		Render render = new Render(graphic);

		if (!board.infectionRateTracker.isEmpty()) {
			drawInfect(7 - board.infectionRateTracker.size());
		}

		// FIXME: broken drawOutBreaks(board.outbreakMark);
		cityRenderers.values().forEach(r -> r.drawCity(render));

		String uncureMark = board.messages.getString("UNCURED");
		graphic.setColor(Color.RED);
		graphic.setFont(new Font(uncureMark, 12, 12));
		graphic.drawString(uncureMark, 425, 770);
		graphic.drawString(uncureMark, 425 + 68, 770);
		graphic.drawString(uncureMark, 425 + (68 * 2), 770);
		graphic.drawString(uncureMark, 425 + (68 * 3), 770);

		int currentPlayerNum = 0;
		for (Player player : board.currentPlayers) {
			currentPlayerNum++;
			drawPlayer(player.playerData.location, Integer.toString(currentPlayerNum));
		}

		drawCards(board.validPlayerCards.size());
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		frame.setVisible(true);
	}
	// =========REFACTOR NEEDED============*/

	// TODO move
	private void drawInfect(int i) {
		graphic.setFont(new Font("X", Font.BOLD, 30));
		graphic.drawString("X", 900 + (i * 50), 55);
	}

	// TODO move
	private void drawCards(int i) {
		String cardCount = MessageFormat.format(board.messages.getString("cardsLeft"), i);
		graphic.setFont(new Font(cardCount, Font.BOLD, 14));
		graphic.drawString(cardCount, 875, 700);
	}

	// TODO move
	private void drawOutBreaks(int i) {
		graphic.setFont(new Font("X", Font.BOLD, 30));
		graphic.drawString("X", 50 + ((i % 2) * 50), 395 + (i * 42));
	}

	// TODO move to RenderPlayer
	private void drawPlayer(City city, String k) {
		graphic.setColor(Color.YELLOW);
		String playerLabel = MessageFormat.format("P {0}", k);
		graphic.setFont(new Font(playerLabel, Font.BOLD, 12));
		graphic.drawString(playerLabel, city.x - 50 + (20 * Integer.parseUnsignedInt(k)), city.y - 30);
	}

}