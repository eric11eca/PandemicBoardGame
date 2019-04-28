package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Initialize.Board;
import Initialize.City;
import Player.Player;

public class DrawingBoard {
	private Map<String, Color> nameToColor;
	private Board board;
	public Graphics2D g;
	JFrame frame;
	public BufferedImage img;
	JLabel label;

	public DrawingBoard(Board board, JFrame f, JLabel label) throws IOException {
		this.board = board;
		this.label = label;
		nameToColor = new HashMap<>();
		nameToColor.put("RED", Color.RED);
		nameToColor.put("YELLOW", Color.YELLOW);
		nameToColor.put("BLUE", Color.BLUE);
		nameToColor.put("BLACK", Color.BLACK);
		File picture = new File("Main Picture.png");
		img = ImageIO.read(picture);
		g = img.createGraphics();
		frame = f;
	}

	public void repaint() throws IOException {
		File picture = new File("Main Picture.png");
		img = ImageIO.read(picture);
		g = img.createGraphics();
		if(!board.infectionRateTrack.isEmpty()) {
			drawInfect(7 - board.infectionRateTrack.size());
		}
		
		drawOutBreaks(board.outbreakMark);
		for (City city : board.cities.values()) {
			if (city.researchStation) {
				drawResearch(city);
			}
			drawCubes(city);

		}
		g.setColor(Color.RED);
		g.setFont(new Font("UNCURED", 12, 12));
		g.drawString("UNCURED", 425, 770);
		g.drawString("UNCURED", 425 + 68, 770);
		g.drawString("UNCURED", 425 + (68*2), 770);
		g.drawString("UNCURED", 425+ (68*3), 770);
		
		int currentPlayerNum = 0;
		for (Player player : board.currentPlayers) {
			currentPlayerNum++;
			
			drawPlayer(player.location, Integer.toString(currentPlayerNum));
		}

		drawCards(board.validPlayerCard.size());
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		frame.setVisible(true);
		frame.show();
	}

	public void drawInfect(int i) {
		g.setFont(new Font("X", Font.BOLD, 30));
		g.drawString("X", 900 + (i * 50), 55);
	}

	public void drawCards(int i) {
		g.setFont(new Font(i + " cards left", Font.BOLD, 14));
		g.drawString(i + " cards left", 875, 700);

	}

	public void drawOutBreaks(int i) {
		g.setFont(new Font("X", Font.BOLD, 30));
		g.drawString("X", 50 + ((i % 2) * 50), 395 + (i * 42));

	}

	public void drawPlayer(City city, String k) {
		g.setColor(Color.YELLOW);
		g.setFont(new Font("P" + k, Font.BOLD, 12));
		g.drawString("P" + k, city.x -50 + (20 * Integer.parseUnsignedInt(k)), city.y - 30);

	}

	public void drawResearch(City city) {
		g.setColor(new Color(228, 180, 34));
		g.fillRect(city.x - 5, city.y - 5, 10, 10);

	}

	public void drawCubes(City city) {
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
		g.setColor(color);
		g.fillRect(x, y, 7, 7);
	}

}