package com.schoovello.ai.treesearch;

import java.util.List;

public class ProblemRunner {

	public static <S extends State, A extends Action> void runProblem(Problem<S, A> problem, TreeSearch<S, A> algorithm) {
		List<A> solution = algorithm.solve();

		System.out.println(algorithm.getIterations() + " iterations");

		if (solution == null) {
			System.out.println("NO SOLUTION");
		} else {
			S state = problem.getInitialState();
			for (A a : solution) {
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
