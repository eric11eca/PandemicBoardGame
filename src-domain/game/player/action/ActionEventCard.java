package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.event.Event;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionEventCard extends Action {

	private List<Player> players;

	public ActionEventCard(Player player, PlayerInteraction interaction, List<Player> players) {
		super(player, interaction);
		this.players = players;
	}

	@Override
	public void perform() {
		interaction.selectPlayerFrom(playersWithEventCards(), p -> {
			interaction.selectOneCardFrom(getEventCardsFromPlayer(p), card -> {
				this.performEventCardAction(p, card);
			});
		});
	}

	@Override
	public boolean canPerform() {
		return !playersWithEventCards().isEmpty();
	}

	protected List<Player> playersWithEventCards() {
		List<Player> list = new ArrayList<>();
		for (Player p : players) {
			if (!getEventCardsFromPlayer(p).isEmpty())
				list.add(p);
		}
		return list;
	}

	protected List<Card> getEventCardsFromPlayer(Player p) {
		return p.getFilteredHand(card -> card.getEvent().isPresent());
	}

	protected void performEventCardAction(Player p, Card card) {
		Event event = card.getEvent().orElseThrow(RuntimeException::new);
		event.executeEvent();
		p.discardCard(card);
	}

}
