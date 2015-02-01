package com.schoovello.ai.treesearch.slidepuzzle;

import static com.schoovello.ai.treesearch.slidepuzzle.SpState.EMPTY;
import static com.schoovello.ai.treesearch.slidepuzzle.SpState.PUZZLE_COLS;
import static com.schoovello.ai.treesearch.slidepuzzle.SpState.PUZZLE_ROWS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.schoovello.ai.treesearch.Heuristic;
import com.schoovello.ai.treesearch.HeuristicProvider;
import com.schoovello.ai.treesearch.Problem;
import com.schoovello.ai.treesearch.SearchNode;
import com.schoovello.ai.treesearch.slidepuzzle.SpAction.Direction;

public class SlidePuzzleProblem implements Problem<SpState, SpAction>, HeuristicProvider<SpState> {

	@Override
	public SpState getInitialState() {
		// @formatter:off
		int[][] puzzle = new int[][] {
				{10, 5, 8},
				{4, 7, 1},
				{18, EMPTY, 3}
		};
		// @formatter:on

		int emptyR = 0;
		int emptyC = 0;
		for (int r = 0; r < SpState.PUZZLE_ROWS; r++) {
			for (int c = 0; c < SpState.PUZZLE_COLS; c++) {
				if (puzzle[r][c] == EMPTY) {
					emptyR = r;
					emptyC = c;
					break;
				}
			}
		}

		return new SpState(puzzle, emptyR, emptyC);
	}

	@Override
	public Heuristic<SpState> generateHeuristic() {
		return new SlidePuzzleHeuristic(getInitialState());
	}

	@Override
	public boolean isGoal(SpState state) {
		int[][] puzzle = state.puzzle;

		final int emptyR = 0;
		final int emptyC = 0;

		int lastValue = -1;
		for (int r = 0; r < SpState.PUZZLE_ROWS; r++) {
			for (int c = 0; c < SpState.PUZZLE_COLS; c++) {
				final int value = puzzle[r][c];

				if (emptyR == r && emptyC == c) {
					if (value != EMPTY) {
						return false;
					}
				} else {
					if (value < lastValue) {
						return false;
					}
					lastValue = value;
				}
			}
		}

		return true;
	}

	@Override
	public double getCost(SpState state, SpAction action) {
		return 1;
	}

	@Override
	public Collection<SearchNode<SpState, SpAction>> expand(SearchNode<SpState, SpAction> expansionNode) {
		final List<SearchNode<SpState, SpAction>> expansion = new ArrayList<>();
		final SpState expansionState = expansionNode.state;
		for (Direction dir : SpAction.Direction.values()) {
			SpAction action = new SpAction(dir, expansionState.emptyR, expansionState.emptyC);
			SpState newState = apply(expansionNode.state, action);

			if (newState != null) {
				expansion.add(new SearchNode<>(newState, expansionNode, action, getCost(expansionState, action)));
			}
		}

		return expansion;
	}

	@Override
	public SpState apply(SpState state, SpAction action) {
		final int[][] newPuzzle = state.copyPuzzle();

		// determine where the tile is coming from
		int fromRow = action.intoRow;
		int fromCol = action.intoCol;
		switch (action.direction) {
		case DOWN:
			fromRow++;
			break;
		case LEFT:
			fromCol--;
			break;
		case RIGHT:
			fromCol++;
			break;
		case UP:
			fromRow--;
			break;
		default:
			break;
		}

		if (!rangeCheck(fromRow, fromCol)) {
			return null;
		}

		final int movingValue = newPuzzle[fromRow][fromCol];
		newPuzzle[action.intoRow][action.intoCol] = movingValue;
		newPuzzle[fromRow][fromCol] = EMPTY;

		return new SpState(newPuzzle, fromRow, fromCol);
	}

	private static boolean rangeCheck(int r, int c) {
		return r >= 0 && r < PUZZLE_ROWS && c >= 0 && c < PUZZLE_COLS;
	}

	private static class SlidePuzzleHeuristic implements Heuristic<SpState> {

		private int[] mLinearGoal;

		public SlidePuzzleHeuristic(SpState initialState) {
			int[][] puzzle = initialState.copyPuzzle();
			mLinearGoal = new int[PUZZLE_ROWS * PUZZLE_COLS];

			int buffPosition = 0;
			for (int r = 0; r < PUZZLE_ROWS; r++) {
				for (int c = 0; c < PUZZLE_COLS; c++) {
					mLinearGoal[buffPosition] = puzzle[r][c];
					buffPosition++;
				}
			}
			Arrays.sort(mLinearGoal);
		}

		@Override
		public double getDistanceToGoal(SpState state) {
			double distance = 0;

			for (int r = 0; r < PUZZLE_ROWS; r++) {
				for (int c = 0; c < PUZZLE_COLS; c++) {
					final int destIdx = Arrays.binarySearch(mLinearGoal, state.puzzle[r][c]);
					int destR = destIdx / PUZZLE_COLS;
					int destC = destIdx % PUZZLE_COLS;
					distance += Math.abs(destR - r) + Math.abs(destC - c);
				}
			}

			return distance;
		}
	}

}
