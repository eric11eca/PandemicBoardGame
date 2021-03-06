package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.event.Event;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionEventCard extends Action {

	private List<Player> players;

	public ActionEventCard(PlayerInteraction interaction, List<Player> players) {
		super(null, interaction);
		this.players = players;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectPlayerFrom(playersWithEventCards(), I18n.format("action.event.select_player"), p -> {
			interaction.selectOneCardFrom(getEventCardsFromPlayer(p), I18n.format("action.event.select_event_card"),
					card -> {
						this.performEventCardAction(p, card, completionCallback);
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

	protected void performEventCardAction(Player p, Card card, Runnable completionCallback) {
		Event event = card.getEvent().orElseThrow(RuntimeException::new);
		event.executeEvent(interaction);
		p.discardCard(card);
		completionCallback.run();
	}

}
