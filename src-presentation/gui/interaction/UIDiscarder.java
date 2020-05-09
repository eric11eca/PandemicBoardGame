package gui.interaction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.cards.Card;
import gui.view.UI;
import render.Render;

@SuppressWarnings("serial")
public class UIDiscarder extends JPanel implements UI {
	private UICard[] toChoose;
	private boolean[] chosen;
	private int numberToChoose;
	private JLabel countLabel;

	public UIDiscarder(int numberToChoose, List<Card> cards, Render render) {
		this.numberToChoose = numberToChoose;
		this.setLayout(new BorderLayout());
		countLabel = new JLabel();
		add(countLabel, BorderLayout.NORTH);

		toChoose = new UICard[cards.size()];
		chosen = new boolean[cards.size()];
		JPanel cardPanel = new JPanel();
		int i = 0;
		for (Card c : cards) {
			toChoose[i] = new UICard(c, render);
			if (cards.size() <= 12) {
				toChoose[i].setPreferredSize(new Dimension(150, 100));
			} else {
				toChoose[i].setPreferredSize(new Dimension(100, 75));
			}

			toChoose[i].addMouseListener(new SelectCardListener(this, toChoose[i]));
			cardPanel.add(toChoose[i]);
			i++;
		}
		if (cards.size() <= 12) {
			cardPanel.setPreferredSize(new Dimension(700, 300));
		} else {
			cardPanel.setPreferredSize(new Dimension(700, 700));
		}
		// cardPanel.setMaximumSize(new Dimension(700, 120 * cards.size() / 4));
		// JScrollPane scroll = new JScrollPane(cardPanel);

		// scroll.setPreferredSize(new Dimension(700, 300));
		// scroll.add(cardPanel);
		add(cardPanel, BorderLayout.CENTER);
//		if (numberToChoose > 0) {
//			JPanel buttonPanel = new JPanel();
//			confirmButton = new JButton("OK");
//			confirmButton.addActionListener(e -> confirmChoice());
//			buttonPanel.add(confirmButton);
//			add(buttonPanel, BorderLayout.SOUTH);
//		}
		// setPreferredSize(new Dimension(500, 400));
		update();
	}

	@Override
	public void update() {
		if (chosenSize() > numberToChoose) {
			for (int i = 0; i < toChoose.length; i++) {
				toChoose[i].setSelected(chosen[i]);
			}
		} else {
			for (int i = 0; i < toChoose.length; i++) {
				chosen[i] = toChoose[i].isSelected();
			}
		}
		countLabel.setText("(" + chosenSize() + "/" + numberToChoose + ")");

		// validate();
		repaint();
	}

	private int chosenSize() {
		int chosen = 0;
		for (UICard cardUI : toChoose) {
			if (cardUI.isSelected())
				chosen++;
		}
		return chosen;
	}

	public List<Card> getChosenCards() {
		List<Card> chosenCard = new ArrayList<>();
		for (UICard cardUI : toChoose) {
			if (cardUI.isSelected())
				chosenCard.add(cardUI.getCard());
		}
		return chosenCard;
	}

}
