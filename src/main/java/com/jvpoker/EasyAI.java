package com.jvpoker;

import java.util.*;
import java.util.logging.Logger;

public class EasyAI extends JVAI {

	@Override
	protected double getPercentFold(Hand hand) {
		return .15;
	}

	@Override
	protected double getPercentRaise(Hand hand) {
		return .2;
	}

	@Override
	protected double getPercentCall(Hand hand) {
		return .2;
	}

	@Override
	protected double getPercentCheck(Hand hand) {
		return .05;
	}

	@Override
	protected double getPercetnAllIn(Hand hand) {
		return .4;
	}
}
