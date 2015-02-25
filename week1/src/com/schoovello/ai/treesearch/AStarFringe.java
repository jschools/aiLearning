package com.schoovello.ai.treesearch;

import java.util.PriorityQueue;

public class AStarFringe<S extends State, A extends Action> extends SingleVisitFringe<S, A> {

	private Heuristic<S> mHeuristic;
	private PriorityQueue<CostWrapper> mQueue;

	public AStarFringe(HeuristicProvider<S> provider) {
		mHeuristic = provider.generateHeuristic();
		mQueue = new PriorityQueue<>();
	}

	@Override
	public boolean isEmpty() {
		return mQueue.isEmpty();
	}

	@Override
	public SearchNode<S, A> select() {
		CostWrapper n = mQueue.poll();
		return n != null ? n.searchNode : null;
	}

	@Override
	protected void addUnvisitedNode(SearchNode<S, A> node) {
		CostWrapper n = new CostWrapper();
		n.searchNode = node;
		n.evaluation = mHeuristic.getDistanceToGoal(node.state) + node.pathCost;
		mQueue.add(n);
	}

	private class CostWrapper implements Comparable<CostWrapper> {
		public SearchNode<S, A> searchNode;
		public double evaluation;

		@Override
		public int compareTo(CostWrapper o) {
			return evaluation < o.evaluation ? -1 : (evaluation > o.evaluation ? 1 : 0);
		}
	}

}
