package gui.interaction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.cards.Card;
import gui.view.UI;
import render.Render;

@SuppressWarnings("serial")
public class UICardArranger extends JPanel implements UI {
	private UICard[] toArrange;
	private Consumer<List<Card>> action;
	private JLabel titleLabel;
	private JButton confirmButton;
	private JPanel cardPanel;

	public UICardArranger(List<Card> cards, Render render, Consumer<List<Card>> action) {
		this.action = action;
		this.setLayout(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel("Please arrange. The first card will be on top of the deck");
		labelPanel.add(titleLabel, BorderLayout.NORTH);
		add(labelPanel, BorderLayout.NORTH);

		toArrange = new UICard[cards.size()];
		cardPanel = new JPanel(new GridLayout(8, 1));
		int i = 0;
		for (Card c : cards) {
			toArrange[i] = new UICard(c, render);
			toArrange[i].addMouseListener(new SelectCardListener(this, toArrange[i]));
			cardPanel.add(toArrange[i]);
			i++;
		}
		add(cardPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		confirmButton = new JButton("OK");
		confirmButton.addActionListener(e -> confirmChoice());
		buttonPanel.add(confirmButton);
		add(buttonPanel, BorderLayout.SOUTH);
		update();
	}

	@Override
	public void update() {
		List<Integer> chosens = new ArrayList<>();
		for (int i = 0; i < toArrange.length; i++) {
			if (toArrange[i].isSelected()) {
				chosens.add(i);
			}
		}
		assert chosens.size() <= 2;
		if (chosens.size() == 2) {
			UICard temp = toArrange[chosens.get(0)];
			toArrange[chosens.get(0)] = toArrange[chosens.get(1)];
			toArrange[chosens.get(1)] = temp;
			for (int i = 0; i < toArrange.length; i++) {
				cardPanel.remove(toArrange[i]);
			}
			for (int i = 0; i < toArrange.length; i++) {
				cardPanel.add(toArrange[i]);
			}
			for (int i = 0; i < toArrange.length; i++) {
				toArrange[i].setSelected(false);
			}
		}

		validate();
		repaint();
	}

	public void confirmChoice() {
		action.accept(getArrangedCards());
	}

	private List<Card> getArrangedCards() {
		List<Card> chosenCard = new ArrayList<>();
		for (UICard cardUI : toArrange) {
			chosenCard.add(cardUI.getCard());
		}
		return chosenCard;
	}

}
