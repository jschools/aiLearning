package com.schoovello.ai.treesearch.slidepuzzle;

import java.util.List;

import com.schoovello.ai.treesearch.AStarFringe;
import com.schoovello.ai.treesearch.TreeSearch;

public class SlidePuzzle {
	public static void main(String[] args) {
		SlidePuzzleProblem problem = new SlidePuzzleProblem();
		TreeSearch<SpState, SpAction> algorithm = new TreeSearch<>(problem, new AStarFringe<SpState, SpAction>(problem));
		List<SpAction> solution = algorithm.solve();

		System.out.println(algorithm.getIterations() + " iterations");

		if (solution == null) {
			System.out.println("NO SOLUTION");
		} else {
			SpState state = problem.getInitialState();
			for (SpAction a : solution) {
				if (a == null) {
					System.out.println("START");
				} else {
					System.out.println(a.describe());
					state = problem.apply(state, a);
				}
				System.out.println(state.describe());
			}
			System.out.println("--------\nActions: " + (solution.size() - 1));
		}
	}
}
