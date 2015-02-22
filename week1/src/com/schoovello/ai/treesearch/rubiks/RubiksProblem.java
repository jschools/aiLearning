package com.schoovello.ai.treesearch.rubiks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.schoovello.ai.treesearch.Heuristic;
import com.schoovello.ai.treesearch.HeuristicProvider;
import com.schoovello.ai.treesearch.Problem;
import com.schoovello.ai.treesearch.SearchNode;
import com.schoovello.ai.treesearch.rubiks.RubState.Face;

public class RubiksProblem implements Problem<RubState, RubAction>, HeuristicProvider<RubState> {

	private static ThreadLocal<byte[]> TEMP_BUFFER = new ThreadLocal<byte[]>() {
		@Override
		protected byte[] initialValue() {
			return new byte[9];
		}
	};

	private Object mInitialStateLock = new Object();
	private static RubState mInitialState;

	@Override
	public RubState getInitialState() {
		synchronized (mInitialStateLock) {
			if (mInitialState == null) {
				RubState s = new RubState();

				byte[][] faces = s.getFaces();

				Random rand = new Random(123);
				final Face[] allFaces = Face.VALUES;
				final int faceCount = allFaces.length;

				for (int i = 0; i < 50; i++) {
					rotateFace(faces, allFaces[rand.nextInt(faceCount)], rand.nextBoolean());
				}

				mInitialState = s;
			}

			return mInitialState;
		}
	}

	@Override
	public Heuristic<RubState> generateHeuristic() {
		return new RubiksHeuristic(getInitialState());
	}

	@Override
	public boolean isGoal(RubState state) {
		final byte[][] faces = state.getFaces();
		for (Face f : Face.VALUES) {
			final byte[] faceColors = faces[f.ordinal()];
			final byte color = f.getInitialColor();
			for (int i = 1; i < faceColors.length; i++) {
				if (faceColors[i] != color) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public double getCost(RubState state, RubAction action) {
		return 1;
	}

	@Override
	public Collection<SearchNode<RubState, RubAction>> expand(SearchNode<RubState, RubAction> expansionNode) {
		final List<SearchNode<RubState, RubAction>> expansion = new ArrayList<>();
		final RubState expansionState = expansionNode.state;

		for (Face f : Face.VALUES) {
			RubAction cwAction = new RubAction(f, true);
			RubState cwState = apply(expansionState, cwAction);
			SearchNode<RubState, RubAction> cw = new SearchNode<>(cwState, expansionNode, cwAction, getCost(expansionState, cwAction));
			RubAction ccwAction = new RubAction(f, false);
			RubState ccwState = apply(expansionState, ccwAction);
			SearchNode<RubState, RubAction> ccw = new SearchNode<>(ccwState, expansionNode, ccwAction, getCost(expansionState, ccwAction));

			expansion.add(cw);
			expansion.add(ccw);
		}

		return expansion;
	}

	@Override
	public RubState apply(RubState state, RubAction action) {
		byte[][] faces = RubState.copyPuzzle(state);

		rotateFace(faces, action.face, action.clockwise);

		return new RubState(faces);
	}

	private static void rotateFace(byte[][] faces, Face rotatingFace, boolean clockwise) {
		final byte[] buff = TEMP_BUFFER.get();

		final byte[] face = faces[rotatingFace.ordinal()];
		final Face upFace = rotatingFace.upFace();
		final int[] upEdge = rotatingFace.upIndexes();
		final Face rightFace = rotatingFace.rightFace();
		final int[] rightEdge = rotatingFace.rightIndexes();
		final Face leftFace = rotatingFace.leftFace();
		final int[] leftEdge = rotatingFace.leftIndexes();
		final Face downFace = rotatingFace.downFace();
		final int[] downEdge = rotatingFace.downIndexes();

		System.arraycopy(face, 0, buff, 0, 9);

		if (clockwise) {
			// rotate the face itself
			face[0] = buff[6];
			face[1] = buff[3];
			face[2] = buff[0];
			face[3] = buff[7];
			// 4 does not move
			face[5] = buff[1];
			face[6] = buff[8];
			face[7] = buff[5];
			face[8] = buff[2];

			// rotate the edges of the adjacent faces
			copyEdgeToBuffer(faces, upFace, upEdge, buff);
			copyEdgeToEdge(faces, leftFace, leftEdge, upFace, upEdge);
			copyEdgeToEdge(faces, downFace, downEdge, leftFace, leftEdge);
			copyEdgeToEdge(faces, rightFace, rightEdge, downFace, downEdge);
			copyBufferToEdge(faces, buff, rightFace, rightEdge);
		} else {
			// rotate the face itself
			face[0] = buff[2];
			face[1] = buff[5];
			face[2] = buff[8];
			face[3] = buff[1];
			// 4 does not move
			face[5] = buff[7];
			face[6] = buff[0];
			face[7] = buff[3];
			face[8] = buff[6];

			// rotate the edges of the adjacent faces
			copyEdgeToBuffer(faces, upFace, upEdge, buff);
			copyEdgeToEdge(faces, rightFace, rightEdge, upFace, upEdge);
			copyEdgeToEdge(faces, downFace, downEdge, rightFace, rightEdge);
			copyEdgeToEdge(faces, leftFace, leftEdge, downFace, downEdge);
			copyBufferToEdge(faces, buff, leftFace, leftEdge);
		}
	}

	private static void copyEdgeToBuffer(byte[][] faces, Face face, int[] edgeIndexes, byte[] buff) {
		byte[] faceBytes = faces[face.ordinal()];
		for (int i = 0; i < 3; i++) {
			buff[i] = faceBytes[edgeIndexes[i]];
		}
	}

	private static void copyEdgeToEdge(byte[][] faces, Face fromFace, int[] fromEdgeIndexes, Face toFace, int[] toEdgeIndexes) {
		final byte[] fromFaceBytes = faces[fromFace.ordinal()];
		final byte[] toFaceBytes = faces[toFace.ordinal()];

		for (int i = 0; i < 3; i++) {
			toFaceBytes[toEdgeIndexes[i]] = fromFaceBytes[fromEdgeIndexes[i]];
		}
	}

	private static void copyBufferToEdge(byte[][] faces, byte[] buff, Face face, int[] edgeIndexes) {
		byte[] faceBytes = faces[face.ordinal()];
		for (int i = 0; i < 3; i++) {
			faceBytes[edgeIndexes[i]] = buff[i];
		}
	}

	private static class RubiksHeuristic implements Heuristic<RubState> {
		public RubiksHeuristic(RubState initialState) {
			// TODO
		}

		@Override
		public double getDistanceToGoal(RubState state) {
			double distance = 0;

			final byte[][] faces = state.getFaces();
			for (Face f : Face.VALUES) {
				final byte[] face = faces[f.ordinal()];
				final byte faceColor = f.getInitialColor();
				for (int i = 0; i < 9; i++) {
					if (face[i] != faceColor) {
						distance += 1; // a decent guess?
					}
				}
			}

			return distance * distance;
		}
	}

}
