package com.schoovello.ai.treesearch.cannibals;

import static com.schoovello.ai.treesearch.cannibals.CmState.Side.LEFT;

import com.schoovello.ai.treesearch.State;

public class CmState implements State {

	public static enum Side {
		LEFT, RIGHT, ;
	}

	public final int cannibalsLeft;
	public final int missionariesLeft;
	public final int cannibalsRight;
	public final int missionariesRight;
	public final Side boatSide;

	public CmState(int cannibalsLeft, int missionariesLeft, int cannibalsRight, int missionariesRight, Side boatSide) {
		this.cannibalsLeft = cannibalsLeft;
		this.missionariesLeft = missionariesLeft;
		this.cannibalsRight = cannibalsRight;
		this.missionariesRight = missionariesRight;
		this.boatSide = boatSide;
	}

	/**
	 * MMMCCC B ~~~~
	 * MM CC    ~~~~ B MC
	 */
	@Override
	public String describe() {
		String mLeft = "";
		for (int m = 0; m < missionariesLeft; m++) {
			mLeft += 'M';
		}
		String cLeft = "";
		for (int c = 0; c < cannibalsLeft; c++) {
			cLeft += 'C';
		}

		String bLeft;
		String bRight;
		if (boatSide == LEFT) {
			bLeft = " B ";
			bRight = "   ";
		} else {
			bLeft = "   ";
			bRight = " B ";
		}

		String mRight = "";
		for (int m = 0; m < missionariesRight; m++) {
			mRight += 'M';
		}
		String cRight = "";
		for (int c = 0; c < cannibalsRight; c++) {
			cRight += 'C';
		}

		return String.format("%3s%3s%3s~~~~%3s%3s%3s", mLeft, cLeft, bLeft, bRight, mRight, cRight);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boatSide == null) ? 0 : boatSide.hashCode());
		result = prime * result + cannibalsLeft;
		result = prime * result + cannibalsRight;
		result = prime * result + missionariesLeft;
		result = prime * result + missionariesRight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CmState other = (CmState) obj;
		if (boatSide != other.boatSide) {
			return false;
		}
		if (cannibalsLeft != other.cannibalsLeft) {
			return false;
		}
		if (cannibalsRight != other.cannibalsRight) {
			return false;
		}
		if (missionariesLeft != other.missionariesLeft) {
			return false;
		}
		if (missionariesRight != other.missionariesRight) {
			return false;
		}
		return true;
	}


}
