package com.schoovello.ai.treesearch.cannibals;

import static com.schoovello.ai.treesearch.cannibals.CmState.Side.LEFT;
import static com.schoovello.ai.treesearch.cannibals.CmState.Side.RIGHT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.schoovello.ai.treesearch.Problem;
import com.schoovello.ai.treesearch.SearchNode;
import com.schoovello.ai.treesearch.cannibals.CmState.Side;

public class CannibalsMissionariesProblem implements Problem<CmState, CmAction> {

	private static final int INIT_C = 10;
	private static final int INIT_M = 10;
	private static final int BOAT_CAPACITY = 4;

	@Override
	public CmState getInitialState() {
		return new CmState(INIT_C, INIT_M, 0, 0, LEFT);
	}

	@Override
	public boolean isGoal(CmState state) {
		return state.cannibalsLeft == 0 && state.cannibalsRight == INIT_C &&
				state.missionariesLeft == 0 && state.missionariesRight == INIT_M;
	}

	@Override
	public double getCost(CmState state, CmAction action) {
		return 1;
	}

	@Override
	public CmState apply(CmState state, CmAction action) {
		int newCL = state.cannibalsLeft - action.cannibalsToRight;
		int newCR = state.cannibalsRight + action.cannibalsToRight;
		int newML = state.missionariesLeft - action.missionariesToRight;
		int newMR = state.missionariesRight + action.missionariesToRight;

		if (inRangeInclusive(newCL, 0, INIT_C) &&
				inRangeInclusive(newCR, 0, INIT_C) &&
				inRangeInclusive(newML, 0, INIT_M) &&
				inRangeInclusive(newMR, 0, INIT_M) &&
				(newCL <= newML || newML == 0) &&
				(newCR <= newMR || newMR == 0)) {
			Side newSide = action.cannibalsToRight > 0 || action.missionariesToRight > 0 ? RIGHT : LEFT;
			return new CmState(newCL, newML, newCR, newMR, newSide);
		}

		return null;
	}

	@Override
	public Collection<SearchNode<CmState, CmAction>> expand(SearchNode<CmState, CmAction> expansionNode) {
		List<SearchNode<CmState, CmAction>> expansion = new ArrayList<>();

		for (int m = 0; m < INIT_M; m++) {
			for (int c = 0; c < INIT_C; c++) {
				final int passengerCount = m + c;
				if (passengerCount <= 0 || passengerCount > BOAT_CAPACITY) {
					continue;
				}

				int missionariesRight = m;
				int cannibalsRight = c;

				final Side direction = expansionNode.state.boatSide == LEFT ? RIGHT : LEFT;
				if (direction == LEFT) {
					missionariesRight = -missionariesRight;
					cannibalsRight = -cannibalsRight;
				}

				CmAction action = new CmAction(missionariesRight, cannibalsRight);
				CmState newState = apply(expansionNode.state, action);
				if (newState != null) {
					expansion.add(new SearchNode<>(newState, expansionNode, action, 1));
				}
			}
		}

		return expansion;
	}

	private static boolean inRangeInclusive(int n, int min, int max) {
		return n >= min && n <= max;
	}
}
