package com.schoovello.ai.treesearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class TreeSearch<S extends State, A extends Action> {

	private Problem<S, A> mProblem;
	private Fringe<S, A> mFringe;
	private int mIterations;

	public TreeSearch(Problem<S, A> problem, Fringe<S, A> fringe) {
		mProblem = problem;
		mFringe = fringe;
	}

	public List<A> solve() {
		mIterations = 0;
		mFringe.add(createInitialNode());

		while (!mFringe.isEmpty()) {
			mIterations++;

			SearchNode<S, A> n = mFringe.select();
			if (mIterations % 50_000 == 0) {
				System.out.println("After " + mIterations + " iterations:");
				System.out.println(n.state.describe());
			}
			if (mIterations >= 300_000) {
				throw new RuntimeException("gave up after " + mIterations + " iterations");
			}

			if (mProblem.isGoal(n.state)) {
				return pathTo(n);
			}
			expand(n);
		}

		return null;
	}

	public int getIterations() {
		return mIterations;
	}

	private SearchNode<S, A> createInitialNode() {
		final S initialState = mProblem.getInitialState();
		return new SearchNode<>(initialState, null, null, 0);
	}

	private void expand(SearchNode<S, A> expansionNode) {
		Collection<SearchNode<S, A>> expansion = mProblem.expand(expansionNode);
		for (SearchNode<S, A> n : expansion) {
			mFringe.add(n);
		}
	}

	private static <A extends Action> List<A> pathTo(SearchNode<?, A> goal) {
		final Stack<A> stack = new Stack<>();
		SearchNode<?, A> n = goal;
		while (n != null) {
			stack.push(n.action);
			n = n.parent;
		}

		List<A> result = new ArrayList<>(stack.size());
		A a;
		while (!stack.empty()) {
			a = stack.pop();
			result.add(a);
		}

		return result;
	}

}
