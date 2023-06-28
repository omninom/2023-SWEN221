// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.cards.core;

import java.util.*;

/**
 * Represents a hand of cards held by a player. As the current round proceeds,
 * the number of cards in the hand will decrease. When the round is over, new
 * cards will be delt and added to this hand.
 *
 * @author David J. Pearce
 *
 */
public class Hand implements Iterable<Card> {	//removed Cloneable as it is not needed
	final private SortedSet<Card> cards = new TreeSet<>();

	/**
	 * Construct a new hand with no cards.
	 */
	public Hand() {}

	/**
	 * Construct a new hand with a given set of cards.
	 *
	 * @param cards The cards to add to the hand.
	 */
	public Hand(Collection<Card> cards) {
		this.cards.addAll(cards);
	}
	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	/**
	 * Check whether a given card is contained in this hand, or not.
	 *
	 * @param card The card to check for.
	 * @return <code>true</code> if the card is contained in this hand;
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(Card card) {
		return cards.contains(card);
	}

	/**
	 * Return all cards in this hand which match the given suit.
	 *
	 * @param suit The suit to match.
	 * @return The set of matching cards (if any).
	 */
	public Set<Card> matches(Card.Suit suit) {
		HashSet<Card> r = new HashSet<>();
		for(Card c : cards) {
			if(c.suit() == suit) {
				r.add(c);
			}
		}
		return r;
	}


	/**
	 * Add a card to the hand.
	 * @param card The card to be added.
	 */
	public void add(Card card) {
		cards.add(card);
	}

	/**
	 * Remove a card from the hand.
	 * @param card The card to be removed.
	 */
	public void remove(Card card) {
		cards.remove(card);
	}

	/**
	 * Get number of cards in this hand.
	 *
	 * @return The size of this hand.
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Remove all cards from this hand.
	 */
	public void clear() {
		cards.clear();
	}

	}

