package com.schoovello.ai.treesearch.rubiks;


public class Rubiks {
	public static void main(String[] args) {
		RubState s = new RubState();
		System.out.println(s.describe());

		// SlidePuzzleProblem problem = new SlidePuzzleProblem();
		// TreeSearch<SpState, SpAction> algorithm = new TreeSearch<>(problem, new AStarFringe<SpState,
		// SpAction>(problem));
		//
		// ProblemRunner.runProblem(problem, algorithm);
	}
}
