package com.jvpoker;

import java.util.*;
import java.util.logging.Logger;

public class HardAI extends JVAI {	
	private Map<String, Integer> lw = new HashMap<String, Integer>();
	
	public HardAI() {
		//TODO: constructor
	}
	
	private void simGames() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(this.hand.getHand()[0]); cards.add(this.hand.getHand()[1]);
		for(int i=0; i<1000; i++) {
			Deck d = new Deck();
			for(int j=0; j<3; j++) {
				cards.add(d.drawCard());
			}
			this.getHandRank(this.hand, this.hand.getHand().length);
			if(this.handRank > 350) { lw.put("win", lw.get("win").intValue()+1); }
			else { lw.put(("loss"), lw.get("loss").intValue()+1); }
		}
	}
	
	@Override
	protected double getPercentFold(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getPercentRaise(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getPercentCall(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getPercentCheck(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double getPercetnAllIn(Hand hand) {
		// TODO Auto-generated method stub
		return 0;
	}

}
