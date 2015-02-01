package com.schoovello.ai.treesearch.slidepuzzle;

import java.util.Arrays;

import com.schoovello.ai.treesearch.State;

public class SpState implements State {

	public static final int EMPTY = -1;

	public static final int PUZZLE_COLS = 3;
	public static final int PUZZLE_ROWS = 3;

	public final int[][] puzzle;
	public final int emptyR;
	public final int emptyC;

	public SpState(int[][] puzzle, int emptyR, int emptyC) {
		this.puzzle = puzzle;
		this.emptyR = emptyR;
		this.emptyC = emptyC;
	}

	@Override
	public String describe() {
		StringBuilder builder = new StringBuilder();
		for (int r = 0; r < PUZZLE_ROWS; r++) {
			for (int c = 0; c < PUZZLE_COLS; c++) {
				final int value = puzzle[r][c];

				String cell = value == EMPTY ? "  " : String.format("%2d", Integer.valueOf(value));
				builder.append(cell).append(' ');
			}
			builder.append('\n');
		}

		return builder.toString();
	}

	public int[][] copyPuzzle() {
		int[][] result = new int[PUZZLE_ROWS][PUZZLE_COLS];
		for (int r = 0; r < PUZZLE_ROWS; r++) {
			System.arraycopy(puzzle[r], 0, result[r], 0, PUZZLE_COLS);
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(puzzle);
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
		SpState other = (SpState) obj;
		if (!Arrays.deepEquals(puzzle, other.puzzle)) {
			return false;
		}
		return true;
	}

}
