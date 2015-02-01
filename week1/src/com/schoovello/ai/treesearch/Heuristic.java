package com.schoovello.ai.treesearch;

public interface Heuristic<S extends State> {

	double getDistanceToGoal(S state);

}
