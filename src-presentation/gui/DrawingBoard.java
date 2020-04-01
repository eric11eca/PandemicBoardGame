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
import data.CityOLD;
import player.Player;

public class DrawingBoard {
	private Map<String, Color> nameToColor;
	private Board board;
	public Graphics2D graphic;
	JFrame frame;
	public BufferedImage img;
	JLabel label;

	public DrawingBoard(Board board, JFrame frame, JLabel label) throws IOException {
		this.board = board;
		this.label = label;
		nameToColor = new HashMap<>();
		nameToColor.put("RED", Color.RED);
		nameToColor.put("YELLOW", Color.YELLOW);
		nameToColor.put("BLUE", Color.BLUE);
		nameToColor.put("BLACK", Color.BLACK);
		File picture = new File("Main Picture.png");
		img = ImageIO.read(picture);
		graphic = img.createGraphics();
		this.frame = frame;
	}

	public void repaint() throws IOException {
		File picture = new File("Main Picture.png");
		img = ImageIO.read(picture);
		graphic = img.createGraphics();
		if(!board.infectionRateTracker.isEmpty()) {
			drawInfect(7 - board.infectionRateTracker.size());
		}
		
		drawOutBreaks(board.outbreakMark);
		for (CityOLD city : board.cities.values()) {
			if (city.researchStation) {
				drawResearch(city);
			}
			drawCubes(city);

		}
		String uncureMark = board.messages.getString("UNCURED");
		graphic.setColor(Color.RED);
		graphic.setFont(new Font(uncureMark, 12, 12));
		graphic.drawString(uncureMark, 425, 770);
		graphic.drawString(uncureMark, 425 + 68, 770);
		graphic.drawString(uncureMark, 425 + (68*2), 770);
		graphic.drawString(uncureMark, 425+ (68*3), 770);
		
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

	public void drawInfect(int i) {
		graphic.setFont(new Font("X", Font.BOLD, 30));
		graphic.drawString("X", 900 + (i * 50), 55);
	}

	public void drawCards(int i) {
		String cardCount = MessageFormat.format(board.messages.getString("cardsLeft"), i);
		graphic.setFont(new Font(cardCount, Font.BOLD, 14));
		graphic.drawString(cardCount, 875, 700);
	}

	public void drawOutBreaks(int i) {
		graphic.setFont(new Font("X", Font.BOLD, 30));
		graphic.drawString("X", 50 + ((i % 2) * 50), 395 + (i * 42));
	}

	public void drawPlayer(CityOLD city, String k) {
		graphic.setColor(Color.YELLOW);
		String playerLabel = MessageFormat.format("P {0}", k);
		graphic.setFont(new Font(playerLabel, Font.BOLD, 12));
		graphic.drawString(playerLabel, city.x -50 + (20 * Integer.parseUnsignedInt(k)), city.y - 30);
	}

	public void drawResearch(CityOLD city) {
		graphic.setColor(new Color(228, 180, 34));
		graphic.fillRect(city.x - 5, city.y - 5, 10, 10);
	}

	public void drawCubes(CityOLD city) {
		for (String colorname : city.diseaseCubes.keySet()) {
			Color color = nameToColor.get(colorname);
			int cubes = city.diseaseCubes.get(colorname);
			while (cubes > 0) {
				if (color.equals(Color.RED)) {
					drawcube(city.x - 20, city.y - 12 +  (10 * cubes), color);
				} else if (color.equals(Color.YELLOW)) {
					drawcube(city.x + 20, city.y - 12 +  (10 * cubes), color);
				} else if (color.equals(Color.BLACK)) {
					drawcube(city.x - 12 +(10 * cubes), city.y - 20 , color);
				} else if (color.equals(Color.BLUE)) {
					drawcube(city.x - 12 +(10 * cubes), city.y + 20, color);
				} else {
					System.out.println("Critical Failure in drawing board draw cubes");
					return;
				}
				cubes--;
			}
		}
	}

	public void drawcube(int x, int y, Color color) {
		graphic.setColor(color);
		graphic.fillRect(x, y, 7, 7);
	}

}