package gui.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import data.ImageLoader;
import game.TurnController;
import render.Render;

@SuppressWarnings("serial")
public class UIBoard extends JPanel implements UI {
	private Render render;
	// private JComponent popup;
	private TurnController turnController;

	public UIBoard(Render render, TurnController turnController) {
		this.render = render;
		this.turnController = turnController;
		// this.renderPlayers = renderPlayers;
		this.setBackground(new Color(51, 103, 153));
		// this.addMouseListener(new BoardMouseListener(this));
	}

//	@Override
//	public void doLayout() {
//		super.doLayout();
//		// if (popup != null) {
//		// popup.setBounds(getPopupArea());
//		// popup.validate();
//		// }
//
//	}

	// public Rectangle getPopupArea() {
	// if (popup == null)
	// return new Rectangle(0, 0, 0, 0);
	// int popupWidth = getWidth() / 3;
	// int popupHeight = getHeight();
	// int popupX = (getWidth() - popupWidth) / 2;
	// int popupY = (getHeight() - popupHeight) / 2;
	// return new Rectangle(popupX, popupY, popupWidth, popupHeight);
	// }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		drawBoard(g2d);
		g2d.dispose();
	}

	private void drawBoard(Graphics2D g2d) {

		Image background = ImageLoader.getInstance().getImage("map2.png");

		float scaleX = (float) getWidth() / background.getWidth(null);
		float scaleY = (float) getHeight() / background.getHeight(null);
		float scale = Math.min(scaleX, scaleY);
		float offX = (scaleX - scale) * background.getWidth(null) / 2;
		float offY = (scaleY - scale) * background.getHeight(null) / 2;
		g2d.translate(offX, offY);
		g2d.scale(scale, scale);

		g2d.drawImage(background, 0, 0, null);

		render.renderCitiesOnBoard(g2d, background.getWidth(null));
		if (turnController.isNextInfectionStageSkipped()) {
			g2d.setColor(Color.BLACK);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
			g2d.fillRect(0, 0, background.getWidth(null), background.getHeight(null));
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		render.renderPlayersOnBoard(g2d);

	}

	@Override
	public void update() {
		validate();
		repaint();
	}

//	public void displayMessage(JComponent message) {
//		// System.out.println(message);
//		if (popup != null)
//			this.remove(popup);
//		popup = message;
//		if (popup != null)
//			this.add(popup);
//		update();
//	}
//
//	public void hideMessage() {
//		displayMessage(null);
//	}
//
//	@Override
//	public void repaint() {
//		super.repaint();
//		if (popup != null)
//			popup.repaint();
//	}

}