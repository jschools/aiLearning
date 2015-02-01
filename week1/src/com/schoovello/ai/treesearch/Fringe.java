package com.schoovello.ai.treesearch;

public interface Fringe<S extends State, A extends Action> {

	boolean isEmpty();

	void add(SearchNode<S, A> node);

	SearchNode<S, A> select();

}
