package com.schoovello.ai.treesearch;

import java.util.Collection;

public interface Problem<S extends State, A extends Action> {

	S getInitialState();

	boolean isGoal(S state);

	double getCost(S state, A action);

	Collection<SearchNode<S, A>> expand(SearchNode<S, A> expansionNode);

	S apply(S state, A action);
}
