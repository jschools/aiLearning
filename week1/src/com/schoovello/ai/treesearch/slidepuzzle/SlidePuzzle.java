package com.schoovello.ai.treesearch.slidepuzzle;

import com.schoovello.ai.treesearch.AStarFringe;
import com.schoovello.ai.treesearch.ProblemRunner;
import com.schoovello.ai.treesearch.TreeSearch;

public class SlidePuzzle {
	public static void main(String[] args) {
		SlidePuzzleProblem problem = new SlidePuzzleProblem();
		TreeSearch<SpState, SpAction> algorithm = new TreeSearch<>(problem, new AStarFringe<SpState, SpAction>(problem));

		ProblemRunner.runProblem(problem, algorithm);
	}
}
