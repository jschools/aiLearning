package com.schoovello.ai.treesearch;

import java.util.LinkedList;

public class BfsSingleVisitFringe<S extends State, A extends Action> extends SingleVisitFringe<S, A> {

	private LinkedList<SearchNode<S, A>> mData;

	public BfsSingleVisitFringe() {
		mData = new LinkedList<>();
	}

	@Override
	public boolean isEmpty() {
		return mData.isEmpty();
	}

	@Override
	public void addUnvisitedNode(SearchNode<S, A> node) {
		mData.addFirst(node);
	}

	@Override
	public SearchNode<S, A> select() {
		return !mData.isEmpty() ? mData.removeLast() : null;
	}

}
