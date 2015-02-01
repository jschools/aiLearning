package com.schoovello.ai.treesearch.slidepuzzle;

import com.schoovello.ai.treesearch.Action;

public class SpAction implements Action {

	public static enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		;
	}

	public Direction direction;
	public int intoRow;
	public int intoCol;

	public SpAction(Direction dir, int emptyR, int emptyC) {
		this.direction = dir;
		this.intoRow = emptyR;
		this.intoCol = emptyC;
	}

	@Override
	public String describe() {
		return String.format("%s into (%2d, %2d)", direction.name(), Integer.valueOf(intoCol), Integer.valueOf(intoRow));
	}

}
