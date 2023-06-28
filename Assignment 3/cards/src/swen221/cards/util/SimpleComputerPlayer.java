	package swen221.cards.util;

	import java.io.Serializable;
	import java.util.Collections;
	import java.util.HashSet;
	import java.util.List;
	import java.util.Set;

	import swen221.cards.core.Card;
	import swen221.cards.core.Card.Suit;
	import swen221.cards.core.Hand;
	import swen221.cards.core.Player;
	import swen221.cards.core.Trick;

	/**
	 * Implements a simple computer player who plays the highest card available when
	 * the trick can still be won, otherwise discards the lowest card available. In
	 * the special case that the player must win the trick (i.e. this is the last
	 * card in the trick), then the player conservatively plays the least card
	 * needed to win.
	 *
	 * @author David J. Pearce
	 *
	 */
	public class SimpleComputerPlayer extends AbstractComputerPlayer implements Serializable {

		private static final long serialVersionUID = -1250898704328404314L;

		public SimpleComputerPlayer(Player player) {
			super(player);
		}

		/**
		 * If the AI player can potentially win the trick, then it plays the highest eligible card.
		 * • If the AI player cannot win the trick, then it discards the lowest eligible card.
		 * • In the special case that the AI player must win the trick, then it conservatively plays the least
		 * card needed to win.
		 * An important concept for understanding these rules is the ordering of eligible cards. A card is eligible
		 * if it may be played according to the rules of the game (e.g. if the AI player can follow suit then
		 * it must, etc). The highest eligible card is then the card with the highest rank and suit, where the
		 * current suit of trumps (if applicable) is always the highest suit. In the case of two equally ranked
		 * cards of non-trump suit, then the underlying ordering implied by Card.compareTo()
		 * @param trick The trick being played.
		 * @return
		 */
		@Override
		public Card getNextCard(Trick trick) {
			Hand hand = player.getHand();
			List<Card> cardsPlayed = trick.getCardsPlayed();

			// Check if it's the last card in the trick
			boolean isLastCard = cardsPlayed.size() == 3;

			// Get the highest card played in the trick
			Card highestCardInTrick = null;
			if (!cardsPlayed.isEmpty()) {
				Hand playedCards = new Hand(cardsPlayed);
				highestCardInTrick = highestCard(playedCards, trick);
			} else {
				// If it's the first card in the trick, play the highest card in the hand
				return highestCard(hand, trick);
			}

			if (isLastCard) {
				Set<Card> winningCards = getWinningCards(hand, highestCardInTrick, trick);	// Get the winning cards
				Card lowestWinningCard = lowestCard(new Hand(winningCards), trick);	// Get the lowest winning card
				if (lowestWinningCard != null) {
					return lowestWinningCard;									// Play the lowest winning card
				}
			}

			if (hand.matches(highestCardInTrick.suit()).size() > 0) {		// If the hand has cards of the same suit
				Set<Card> winningCards = getWinningCards(hand, highestCardInTrick, trick);
				Card highestWinningCard = highestCard(new Hand(winningCards), trick);
				if (highestWinningCard != null) {
					return highestWinningCard;
				}
			}

			return lowestCard(hand, trick); // Otherwise, play the lowest eligible card
		}

		/**
		 * Returns a set of cards that can potentially win the trick
		 * @param hand
		 * @param highestCardInTrick
		 * @param trick
		 * @return winningCards
		 */
		private Set<Card> getWinningCards(Hand hand, Card highestCardInTrick, Trick trick) {
			Set<Card> winningCards = new HashSet<>();
			Card.Suit trumpSuit = trick.getTrumps();
			for (Card card : hand) {		//for ever card in the hand
				if (card.suit() == highestCardInTrick.suit() && card.compareTo(highestCardInTrick) > 0) {	//if the card is of the same suit and higher than the highest card in the trick
					winningCards.add(card);
				} else if (trumpSuit != null && card.suit() == trumpSuit && card.compareTo(highestCardInTrick) > 0) {	//if the card is a trump card and higher than the highest card in the trick
					winningCards.add(card);
				}
			}
			return winningCards;
		}


		/**
		 * Returns the highest card in the hand
		 * @param hand
		 * @param trick
		 * @return highestCard
		 */
		public Card highestCard(Hand hand, Trick trick) {
			Card highestCard = null;
			Card.Suit leadSuit = null;
			Set<Card> leadCards = Collections.emptySet();
			Card.Suit trumpSuit = trick.getTrumps();
			Set<Card> trumpCards = hand.matches(trumpSuit);
			if(!trick.getCardsPlayed().isEmpty()) {	//if there are cards in the trick
				leadSuit = trick.getCardsPlayed().get(0).suit();
				leadCards = hand.matches(leadSuit);
			}
			//if there is a trump suit, return the highest trump card
			if (trumpSuit != null && !trumpCards.isEmpty()) {
				for (Card c : trumpCards) {
					if (highestCard == null) {highestCard = c;}
					else if (c.compareTo(highestCard) > 0) {highestCard = c;}
				}
			}
			//if there is no trump suit, return the highest card of the lead suit
			else if (!leadCards.isEmpty() && leadSuit != null) {
				for (Card c : leadCards) {
					if (highestCard == null) {highestCard = c;}
					else if (c.compareTo(highestCard) > 0) {highestCard = c;}
				}
			}
			//if there is no lead suit, return the highest card of any suit
			else {
				for (Card c : hand) {
					if (highestCard == null) highestCard = c;
					else if (highestCard.rank().compareTo(c.rank()) < 0) highestCard = c;
					else if (highestCard.rank().compareTo(c.rank()) == 0) {
						if (highestCard.suit().compareTo(c.suit()) < 0) highestCard = c;
					}
				}
			}
			return highestCard;
		}



		/**
		 * Returns the lowest card in the hand
		 * @param hand
		 * @param trick
		 * @return
		 */
		public Card lowestCard(Hand hand, Trick trick) {
			Card.Suit leadSuit = trick.getCardsPlayed().get(0).suit();
			Set<Card> leadCards = hand.matches(leadSuit);
			Card.Suit trumpSuit = trick.getTrumps();
			Set<Card> trumpCards = hand.matches(trumpSuit);
			Card lowestCard = null;
			if (!leadCards.isEmpty()) {			//if we have a card that is the same suit as the first card played, return the lowest card of that suit
				for (Card c : leadCards) {
					if (lowestCard == null) {lowestCard = c;}
					else if (c.compareTo(lowestCard) < 0) {lowestCard = c;}
				}
			}
			else if (leadCards.isEmpty() && !trumpCards.isEmpty()) {		//if we don't have a card that is the same suit as the first card played, but we have a trump card, return the lowest trump card
				for (Card c : trumpCards) {
					if (lowestCard == null) {lowestCard = c;}
					else if (c.compareTo(lowestCard) < 0) {lowestCard = c;}
				}
			}
			else {
				//if we don't have a card that is the same suit as the first card played, and we don't have a trump card, return the lowest card in the hand
				for (Card c : hand) {
					if (lowestCard == null) {lowestCard = c;}
					else if (c.compareTo(lowestCard) < 0) {lowestCard = c;}
				}

			}
			return lowestCard;
		}
	}

