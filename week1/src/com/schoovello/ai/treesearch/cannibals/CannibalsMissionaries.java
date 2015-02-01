package com.schoovello.ai.treesearch.cannibals;

import java.util.List;

import com.schoovello.ai.treesearch.BfsSingleVisitFringe;
import com.schoovello.ai.treesearch.Problem;
import com.schoovello.ai.treesearch.TreeSearch;

public class CannibalsMissionaries {
	public static void main(String[] args) {
		Problem<CmState, CmAction> problem = new CannibalsMissionariesProblem();

		TreeSearch<CmState, CmAction> algorithm = new TreeSearch<>(problem, new BfsSingleVisitFringe<CmState, CmAction>());
		List<CmAction> solution = algorithm.solve();

		if (solution == null) {
			System.out.println("NO SOLUTION");
		} else {
			CmState state = problem.getInitialState();
			for (CmAction a : solution) {
				if (a == null) {
					System.out.println("START");
				} else {
					System.out.println(a.describe());
					state = problem.apply(state, a);
				}
				System.out.println(state.describe());
			}
		}
	}
}
