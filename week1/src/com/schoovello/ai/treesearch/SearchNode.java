package com.schoovello.ai.treesearch;

public class SearchNode<S extends State, A extends Action> {

	public S state;
	public SearchNode<S, A> parent;
	public A action;
	public double pathCost;
	public int depth;

	public SearchNode(S state, SearchNode<S, A> parent, A action, double pathCost) {
		this.state = state;
		this.parent = parent;
		this.action = action;
		this.pathCost = parent != null ? parent.pathCost + pathCost : pathCost;
		this.depth = parent != null ? parent.depth + 1 : 0;
	}

}
