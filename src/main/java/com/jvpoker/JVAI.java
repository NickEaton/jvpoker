package com.jvpoker;

import java.util.*;
import java.util.logging.Logger;

public abstract class JVAI {
	
	//AI's %'s to choose certain moves
	protected int percentFold, percentRaise, percentCall, percentCheck, percentAllIn;
	//The numeric hand rank between 3 (for a 3 high card) and 750 for a royal flush
	protected int handRank;
	//The AI's hand
	protected Hand hand;
	//Logger
	private static Logger log = Logger.getLogger("AILog", JVAI.class.getName());
	
	//The various AI moves
	public enum MOVE { FOLD, RAISE, CALL, CHECK, ALL_IN }
	
	//Methods to implement to get the AI's %'s based on difficulty
	protected abstract int getPercentFold(Hand hand);
	protected abstract int getPercentRaise(Hand hand);
	protected abstract int getPercentCall(Hand hand);
	protected abstract int getPercentCheck(Hand hand);
	protected abstract int getPerCcetnAllIn(Hand hand);

	//@return the move the AI chooses to make
	protected MOVE getMove(Hand hand) {
		final double rand = new Random().nextDouble();
		if(1.0 - getPercentFold(hand) <= rand) {
			log.info("AI folds");
			return MOVE.FOLD;
		}
		else if(1.0 - getPercentFold(hand) - getPercentRaise(hand) <= rand) {
			log.info("AI raises");
			return MOVE.RAISE; 
		}
		else if(1.0 - getPercentFold(hand) - getPercentRaise(hand) - getPercentCall(hand) <= rand) { 
			log.info("AI calls");
			return MOVE.CALL; 
		}
		else if(1.0 - getPercentFold(hand) - getPercentRaise(hand) - getPercentCall(hand) - getPercentCheck(hand) <= rand) { 
			log.info("AI Folds");
			return MOVE.CHECK; 
		}
		
		//Yes, the default move is to go all in
		log.info("AI is all in");
		return MOVE.ALL_IN;
	}
	
	//@return if a hand is a straight draw
	private boolean isStraightDraw(Hand hand) {
		Card[] cards = hand.getHand();
		if(cards.length == 2) {
			return (cards[0].getNumber()+1 == cards[1].getNumber()) ? true : false;
		}
		//TODO: make sure hand is sorted by ascending card value
		else if(cards.length == 5) {
			return (cards[0].getNumber()+1 == cards[1].getNumber() && cards[1].getNumber()+1 == cards[2].getNumber() &&
					cards[2].getNumber()+1 == cards[3].getNumber() && cards[3].getNumber()+1 == cards[4].getNumber()) ? true : false;
		}
		else if(cards.length == 6) {
			return (cards[0].getNumber()+1 == cards[1].getNumber() && cards[1].getNumber()+1 == cards[2].getNumber() &&
					cards[2].getNumber()+1 == cards[3].getNumber() && cards[3].getNumber()+1 == cards[4].getNumber() &&
					cards[4].getNumber()+1 == cards[5].getNumber()) ? true : false;
		}
		return (cards[0].getNumber()+1 == cards[1].getNumber() && cards[1].getNumber()+1 == cards[2].getNumber() &&
				cards[2].getNumber()+1 == cards[3].getNumber() && cards[3].getNumber()+1 == cards[4].getNumber() &&
				cards[4].getNumber()+1 == cards[5].getNumber() && cards[5].getNumber()+1 == cards[6].getNumber()) ? true : false;
	}
	
	//@return if a hand is a flush draw
	private boolean isFlushDraw(Hand hand) {
		Card[] cards = hand.getHand();
		if(cards.length == 2) {
			return (cards[0].getSuite() == cards[1].getSuite()) ? true : false;
		}
		else if(cards.length == 5) {
			return (cards[0].getSuite() == cards[1].getSuite() && cards[0].getSuite() == cards[2].getSuite() && cards[0].getSuite() == cards[3].getSuite() &&
					cards[0].getSuite() == cards[4].getSuite()) ? true : false;
		}
		else if(cards.length == 6) {
			return (cards[0].getSuite() == cards[1].getSuite() && cards[0].getSuite() == cards[2].getSuite() && cards[0].getSuite() == cards[3].getSuite() &&
					cards[0].getSuite() == cards[4].getSuite() && cards[0].getSuite() == cards[5].getSuite()) ? true : false;
		}
		return (cards[0].getSuite() == cards[1].getSuite() && cards[0].getSuite() == cards[2].getSuite() && cards[0].getSuite() == cards[3].getSuite() &&
				cards[0].getSuite() == cards[4].getSuite() && cards[0].getSuite() == cards[5].getSuite() && cards[0].getSuite() == cards[6].getSuite()) ? true : false;
	}
	
	//@return if a hand is a kind draw
	private boolean isKindDraw(Hand hand) {
		Card[] cards = hand.getHand();
		if(cards.length == 2) {
			return (cards[0].getNumber() == cards[0].getNumber()) ? true : false;
		}
		else if(cards.length == 5) {
			return (cards[0].getNumber() == cards[1].getNumber() && cards[0].getNumber() == cards[2].getNumber() && cards[0].getNumber() == cards[3].getNumber() &&
					cards[0].getNumber() == cards[4].getNumber()) ? true : false;
		}
		else if(cards.length == 6) {
			return (cards[0].getNumber() == cards[1].getNumber() && cards[0].getNumber() == cards[2].getNumber() && cards[0].getNumber() == cards[3].getNumber() &&
					cards[0].getNumber() == cards[4].getNumber() && cards[0].getNumber() == cards[5].getNumber()) ? true : false;
		}
		return (cards[0].getNumber() == cards[1].getNumber() && cards[0].getNumber() == cards[2].getNumber() && cards[0].getNumber() == cards[3].getNumber() &&
				cards[0].getNumber() == cards[4].getNumber() && cards[0].getNumber() == cards[5].getNumber() && cards[0].getNumber() == cards[6].getNumber()) ? true : false;
	}
	
	//@return an integer value representing the hand's rank 
	protected void getHandRank(Hand hand, int numCards) {
		if(numCards == 2) {
			if(this.isStraightDraw(this.hand)) {
				this.handRank += 10*(hand.getHand()[0].getNumber() + hand.getHand()[1].getNumber());
			}
			else if(this.isKindDraw(this.hand)) {
				this.handRank += 5*(hand.getHand()[0].getNumber() + hand.getHand()[1].getNumber());
			}
			else if(this.isFlushDraw(this.hand)) {
				this.handRank +=  hand.getHand()[0].getNumber() + hand.getHand()[1].getNumber();
			}
			else {
				this.handRank += (hand.getHand()[0].getNumber() > hand.getHand()[1].getNumber()) ? hand.getHand()[0].getNumber() : hand.getHand()[1].getNumber();
			}
		}
		else if(numCards == 5) {
			Card[] cards = hand.getHand();
			if(this.isStraightDraw(this.hand)) {
				this.handRank += 10*(cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber());
			}
			else if(this.isKindDraw(this.hand)) {
				this.handRank += 5*(cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber());
			}
			else if(this.isFlushDraw(this.hand)) {
				this.handRank += cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber();
			}
			else {
				this.handRank += hand.getHighCard().getNumber();
			}
		}
		else if(numCards == 6) {
			Card[] cards = hand.getHand();
			if(this.isStraightDraw(this.hand)) {
				this.handRank += 10*(cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber() + cards[5].getNumber());
			}
			else if(this.isKindDraw(this.hand)) {
				this.handRank += 5*(cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber() + cards[5].getNumber());
			}
			else if(this.isFlushDraw(this.hand)) {
				this.handRank += cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber() + cards[5].getNumber();
			}
			else {
				this.handRank += hand.getHighCard().getNumber();
			}
		}
		else if(numCards == 7){
			Card[] cards = hand.getHand();
			if(this.isStraightDraw(this.hand)) {
				this.handRank += 10*(cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber() + cards[5].getNumber() + cards[6].getNumber());
			}
			else if(this.isKindDraw(this.hand)) {
				this.handRank += 5*(cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber() + cards[5].getNumber() + cards[6].getNumber());
			}
			else if(this.isFlushDraw(this.hand)) {
				this.handRank += cards[0].getNumber() + cards[1].getNumber() + cards[2].getNumber() + cards[3].getNumber() + cards[4].getNumber() + cards[5].getNumber() + cards[6].getNumber();
			}
			else {
				this.handRank += hand.getHighCard().getNumber();
			}
		}
		else {
			//error, bad param
			log.info("ERROR: illegal parameter at method JVAI.getHandRank (Value must be 2, 5, 6 or 7)");
		}
	}
}