package com.schoovello.ai.treesearch.rubiks;

import com.schoovello.ai.treesearch.Action;
import com.schoovello.ai.treesearch.rubiks.RubState.Face;

public class RubAction implements Action {

	/*
	 * Singmaster Notation
	 * Clockwise actions:
	 * F (Front): the side currently facing the solver
	 * B (Back): the side opposite the front
	 * U (Up): the side above or on top of the front side
	 * D (Down): the side opposite the top, underneath the Cube
	 * L (Left): the side directly to the left of the front
	 * R (Right): the side directly to the right of the front
	 *
	 * Counter-clockwise is denoted with a ' appended, e.g. F' means rotate Front CCW
	 */

	public final Face face;
	public final boolean clockwise;

	public RubAction(Face face, boolean clockwise) {
		this.face = face;
		this.clockwise = clockwise;
	}

	@Override
	public String describe() {
		return clockwise ? face.describe() : face.describe() + "'";
	}

}
