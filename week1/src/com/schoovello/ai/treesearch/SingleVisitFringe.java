package com.schoovello.ai.treesearch;

import java.util.HashSet;
import java.util.Set;

public abstract class SingleVisitFringe<S extends State, A extends Action> implements Fringe<S, A> {

	private Set<S> mVisitedStates;

	public SingleVisitFringe() {
		mVisitedStates = new HashSet<>();
	}

	@Override
	public final void add(SearchNode<S, A> node) {
		if (mVisitedStates.contains(node.state)) {
			return;
		}

		mVisitedStates.add(node.state);
		addUnvisitedNode(node);
	}

	protected abstract void addUnvisitedNode(SearchNode<S, A> node);

}
