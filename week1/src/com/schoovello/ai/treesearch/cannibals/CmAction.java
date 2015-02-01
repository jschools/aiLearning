package com.schoovello.ai.treesearch.cannibals;

import com.schoovello.ai.treesearch.Action;


public class CmAction implements Action {

	public int missionariesToRight;
	public int cannibalsToRight;

	private CmAction() {
		// reserved for special actions
	}

	public CmAction(int missionariesToRight, int cannibalsToRight) {
		this.missionariesToRight = missionariesToRight;
		this.cannibalsToRight = cannibalsToRight;
	}

	@Override
	public String describe() {
		StringBuilder builder = new StringBuilder();
		if (missionariesToRight != 0) {
			builder.append(Math.abs(missionariesToRight)).append('m');
		}
		if (cannibalsToRight != 0) {
			builder.append(Math.abs(cannibalsToRight)).append('c');
		}
		builder.append(' ');
		builder.append(missionariesToRight > 0 || cannibalsToRight > 0 ? "RIGHT" : "LEFT");

		return builder.toString();
	}

}
