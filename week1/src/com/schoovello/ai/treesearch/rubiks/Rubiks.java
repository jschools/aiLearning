package com.schoovello.ai.treesearch.rubiks;

import com.schoovello.ai.treesearch.DfsSingleVisitFringe;
import com.schoovello.ai.treesearch.ProblemRunner;
import com.schoovello.ai.treesearch.TreeSearch;


public class Rubiks {
	public static void main(String[] args) {
		RubiksProblem problem = new RubiksProblem();
		TreeSearch<RubState, RubAction> algorithm = new TreeSearch<>(problem, new DfsSingleVisitFringe<RubState, RubAction>());

		ProblemRunner.runProblem(problem, algorithm);
	}
}
