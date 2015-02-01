package com.schoovello.ai.treesearch;

public interface HeuristicProvider<S extends State> {

	Heuristic<S> generateHeuristic();
}
