package com.schoovello.ai.treesearch;

import java.util.Stack;

public class DfsSingleVisitFringe<S extends State, A extends Action> extends SingleVisitFringe<S, A> {

	private Stack<SearchNode<S, A>> mData;

	public DfsSingleVisitFringe() {
		mData = new Stack<>();
	}

	@Override
	public boolean isEmpty() {
		return mData.empty();
	}

	@Override
	public void addUnvisitedNode(SearchNode<S, A> node) {
		mData.push(node);
	}

	@Override
	public SearchNode<S, A> select() {
		return !mData.empty() ? mData.pop() : null;
	}

}
