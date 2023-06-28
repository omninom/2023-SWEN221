// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.cards.core;

/**
 * Represents a single card in the game.
 *
 * @author David J. Pearce
 *
 */
public class Card implements Comparable<Card> {

	/**
	 * Represents a card suit.
	 *
	 * @author David J. Pearce
	 *
	 */
	public enum Suit {
		/**
		 * The suit of hearts.
		 */
		HEARTS,
		/**
		 * The suit of clubs.
		 */
		CLUBS,
		/**
		 * The suit of diamonds.
		 */
		DIAMONDS,
		/**
		 * The suit of spades.
		 */
		SPADES;
	}

	/**
	 * Represents the different card "numbers".
	 *
	 * @author David J. Pearce
	 *
	 */
	public enum Rank {
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		JACK,
		QUEEN,
		KING,
		ACE;
	}

	// =======================================================
	// Card stuff
	// =======================================================

	private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
	private Rank rank; // 2 <= number <= 14 (ACE)

	/**
	 * Construct a card in the given suit, with a given number
	 *
	 * @param suit
	 *            --- between 0 (HEARTS) and 3 (SPADES)
	 * @param number
	 *            --- between 2 and 14 (ACE)
	 */
	public Card(Suit suit, Rank number) {
		this.suit = suit;
		this.rank = number;
	}

	/**
	 * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
	 *
	 * @return The suite of this card.
	 */
	public Suit suit() {
		return suit;
	}

	/**
	 * Get the number of this card, between 2 and 14 (ACE).
	 *
	 * @return The rank of this card.
	 */
	public Rank rank() {
		return rank;
	}

	private static String[] suits = { "Hearts","Clubs","Diamonds","Spades"};
	private static String[] ranks = { "2 of ", "3 of ", "4 of ",
			"5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ", "Jack of ",
			"Queen of ", "King of ", "Ace of " };

	@Override
	public String toString() {
		return ranks[rank.ordinal()] + suits[suit.ordinal()];
	}

	@Override
	/**
	 * Compare two cards to see which is larger. This is based on the suit and
	 * rank of the cards.
	 *  Hearts < Clubs < Diamonds < Spades.
	 *  For picture cards we have that: Ace > King > Queen > Jack > 10.
	 */
	public int compareTo(Card o) {
		return this.hashCode() - o.hashCode(); //this is a bit of a hack, but it works
	}

	public boolean equals(Object o) {
		return this.hashCode() == o.hashCode();
	}

	public int hashCode() {
		return this.suit.ordinal() * 50 + this.rank.ordinal();	// 50 is arbitrary, just to increase range to make sure the hashcode is unique
	}
}
