package com.schoovello.ai.treesearch.cannibals;

import com.schoovello.ai.treesearch.AStarFringe;
import com.schoovello.ai.treesearch.ProblemRunner;
import com.schoovello.ai.treesearch.TreeSearch;

public class CannibalsMissionaries {
	public static void main(String[] args) {
		CannibalsMissionariesProblem problem = new CannibalsMissionariesProblem();

		TreeSearch<CmState, CmAction> algorithm = new TreeSearch<>(problem, new AStarFringe<CmState, CmAction>(problem));

		ProblemRunner.runProblem(problem, algorithm);
	}
}
