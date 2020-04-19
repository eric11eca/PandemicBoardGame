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
public class UICardChooser extends JPanel implements UI {
	private UICard[] toChoose;
	private int numberToChoose;
	private Consumer<List<Card>> action;
	private JLabel titleLabel;
	private JLabel countLabel;
	private JButton confirmButton;
	private boolean forceEqual;

	public UICardChooser(String title, int numberToChoose, List<Card> cards, Render render, Consumer<List<Card>> action,
			boolean forceEqual) {
		this.action = action;
		this.forceEqual = forceEqual;
		this.numberToChoose = numberToChoose;
		this.setLayout(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		titleLabel = new JLabel(title);
		countLabel = new JLabel();
		labelPanel.add(titleLabel, BorderLayout.NORTH);
		labelPanel.add(countLabel, BorderLayout.CENTER);
		add(labelPanel, BorderLayout.NORTH);

		toChoose = new UICard[cards.size()];
		JPanel cardPanel = new JPanel(new GridLayout(5, 2));
		int i = 0;
		for (Card c : cards) {
			toChoose[i] = new UICard(c, render);
			toChoose[i].addMouseListener(new SelectCardListener(this, toChoose[i]));
			cardPanel.add(toChoose[i]);
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
		if (numberToChoose != 0)
			countLabel.setText("(" + chosenSize() + "/" + numberToChoose + ")");
		if (forceEqual) {
			confirmButton.setEnabled(chosenSize() == numberToChoose);
		} else {
			confirmButton.setEnabled(chosenSize() <= numberToChoose);
		}

		validate();
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

	public void confirmChoice() {
		action.accept(getChosenCards());
	}

	private List<Card> getChosenCards() {
		List<Card> chosenCard = new ArrayList<>();
		for (UICard cardUI : toChoose) {
			if (cardUI.isSelected())
				chosenCard.add(cardUI.getCard());
		}
		return chosenCard;
	}

}
