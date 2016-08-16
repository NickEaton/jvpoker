package com.jvpoker;

import java.util.*;

public abstract class JVAI {
	
	//AI's %'s to choose certain moves
	protected int percentFold, percentRaise, percentCall, percentCheck, percentAllIn;
	//The numeric hand rank between 0-100
	protected int handRank;
	
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
			return MOVE.FOLD;
		}
		else if(1.0 - getPercentFold(hand) - getPercentRaise(hand) <= rand) { 
			return MOVE.RAISE; 
		}
		else if(1.0 - getPercentFold(hand) - getPercentRaise(hand) - getPercentCall(hand) <= rand) { 
			return MOVE.CALL; 
		}
		else if(1.0 - getPercentFold(hand) - getPercentRaise(hand) - getPercentCall(hand) - getPercentCheck(hand) <= rand) { 
			return MOVE.CHECK; 
		}
		return MOVE.ALL_IN;
	}
	
	//@return an integer value representing the hand's rank between 0-10
	protected int getHandRank(Hand hand, int numCards) {
		if(numCards == 2) {
			
		}
		
		return 0;
	}
}
