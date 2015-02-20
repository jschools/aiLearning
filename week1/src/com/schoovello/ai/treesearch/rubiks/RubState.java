package com.schoovello.ai.treesearch.rubiks;

import static com.schoovello.ai.treesearch.rubiks.RubState.Color.BLUE;
import static com.schoovello.ai.treesearch.rubiks.RubState.Color.GREEN;
import static com.schoovello.ai.treesearch.rubiks.RubState.Color.ORANGE;
import static com.schoovello.ai.treesearch.rubiks.RubState.Color.RED;
import static com.schoovello.ai.treesearch.rubiks.RubState.Color.WHITE;
import static com.schoovello.ai.treesearch.rubiks.RubState.Color.YELLOW;

import java.util.Arrays;

import com.schoovello.ai.treesearch.State;
import com.schoovello.util.AnsiColorString;

public class RubState implements State {

	public static final int FACE_SIZE = 9;
	public static final int FACE_COUNT = Face.values().length;

	private byte[][] mFaces;

	public RubState() {
		mFaces = new byte[FACE_COUNT][FACE_SIZE];
		for (Face f : Face.values()) {
			Arrays.fill(mFaces[f.ordinal()], f.getInitialColor());
		}
	}

	public RubState(byte[][] faces) {
		mFaces = faces;
	}

	public byte[][] getFaces() {
		return mFaces;
	}

	@Override
	public String describe() {
		StringBuilder builder = new StringBuilder();

		// @formatter:off
		//
		//	    FFF
		//      FFF
		//      FFF
		//	LLL DDD RRR UUU
		//  LLL DDD RRR UUU
		//	LLL DDD RRR UUU
		//	    BBB
		//	    BBB
		//	    BBB
		//
		// @formatter:on

		// FRONT
		for (int row = 0; row < 3; row++) {
			builder.append("    ");
			for (int col = 0; col < 3; col++) {
				byte color = mFaces[Face.FRONT.ordinal()][row * 3 + col];
				describeTile(builder, color);
			}
			builder.append('\n');
		}

		// LEFT DOWN RIGHT UP
		final Face[] faces = { Face.LEFT, Face.DOWN, Face.RIGHT, Face.UP };
		for (int row = 0; row < 3; row++) {
			for (Face face : faces) {
				for (int faceCol = 0; faceCol < 3; faceCol++) {
					int faceIdx = row * 3 + faceCol;
					byte color = mFaces[face.ordinal()][faceIdx];
					describeTile(builder, color);
				}
				builder.append(' ');
			}
			builder.append('\n');
		}

		// BACK
		for (int row = 0; row < 3; row++) {
			builder.append("    ");
			for (int col = 0; col < 3; col++) {
				byte color = mFaces[Face.BACK.ordinal()][row * 3 + col];
				describeTile(builder, color);
			}
			builder.append('\n');
		}

		builder.append(AnsiColorString.RESET);

		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(mFaces);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RubState other = (RubState) obj;
		if (!Arrays.deepEquals(mFaces, other.mFaces)) {
			return false;
		}
		return true;
	}

	public static byte[][] copyPuzzle(RubState state) {
		byte[][] result = new byte[FACE_COUNT][FACE_SIZE];
		for (int f = 0; f < FACE_COUNT; f++) {
			for (int i = 0; i < FACE_SIZE; i++) {
				result[f][i] = result[f][i];
			}
		}
		return result;
	}

	public static void describeTile(StringBuilder builder, byte color) {
		builder.append(getAnsiColor(color));
		builder.append(getColorChar(color));
	}

	public static char getColorChar(byte color) {
		return (char) 0x2588;
	}

	public static String getAnsiColor(byte color) {
		switch (color) {
		case WHITE:
			return AnsiColorString.GRAY;
		case RED:
			return AnsiColorString.RED;
		case GREEN:
			return AnsiColorString.GREEN;
		case BLUE:
			return AnsiColorString.BLUE;
		case YELLOW:
			return AnsiColorString.YELLOW;
		case ORANGE:
			return AnsiColorString.ORANGE;
		default:
			return AnsiColorString.RESET;
		}
	}

	public static interface Color {
		public static final byte WHITE = 0;
		public static final byte RED = 1;
		public static final byte GREEN = 2;
		public static final byte BLUE = 3;
		public static final byte YELLOW = 4;
		public static final byte ORANGE = 5;
	}

	public static enum Face {
		FRONT,
		BACK,
		LEFT,
		RIGHT,
		UP,
		DOWN,
		;

		private static final int[] EDGE_TOP = { 2, 1, 0 };
		private static final int[] EDGE_LEFT = { 0, 3, 6 };
		private static final int[] EDGE_RIGHT = { 8, 5, 2 };
		private static final int[] EDGE_BOTTOM = { 6, 7, 8 };

		public String describe() {
			return String.valueOf(name().charAt(0));
		}

		public byte getInitialColor() {
			switch (this) {
			case FRONT:
				return WHITE;
			case BACK:
				return YELLOW;
			case LEFT:
				return GREEN;
			case RIGHT:
				return BLUE;
			case UP:
				return ORANGE;
			case DOWN:
				return RED;
			default:
				return WHITE;
			}
		}

		public Face upFace() {
			switch (this) {
			case FRONT:
				return UP;
			case BACK:
				return UP;
			case LEFT:
				return UP;
			case RIGHT:
				return UP;
			case UP:
				return BACK;
			case DOWN:
				return FRONT;
			default:
				return null;
			}
		}

		public int[] upIndexes() {
			switch (this) {
			case FRONT:
				return EDGE_BOTTOM;
			case BACK:
				return EDGE_TOP;
			case LEFT:
				return EDGE_LEFT;
			case RIGHT:
				return EDGE_RIGHT;
			case UP:
				return EDGE_TOP;
			case DOWN:
				return EDGE_BOTTOM;
			default:
				return null;
			}
		}

		public Face leftFace() {
			switch (this) {
			case FRONT:
				return LEFT;
			case BACK:
				return RIGHT;
			case LEFT:
				return BACK;
			case RIGHT:
				return FRONT;
			case UP:
				return LEFT;
			case DOWN:
				return LEFT;
			default:
				return null;
			}
		}

		public int[] leftIndexes() {
			switch (this) {
			case FRONT:
				return EDGE_RIGHT;
			case BACK:
				return EDGE_RIGHT;
			case LEFT:
				return EDGE_RIGHT;
			case RIGHT:
				return EDGE_RIGHT;
			case UP:
				return EDGE_TOP;
			case DOWN:
				return EDGE_BOTTOM;
			default:
				return null;
			}
		}

		public Face rightFace() {
			switch (this) {
			case FRONT:
				return RIGHT;
			case BACK:
				return LEFT;
			case LEFT:
				return BACK;
			case RIGHT:
				return FRONT;
			case UP:
				return RIGHT;
			case DOWN:
				return RIGHT;
			default:
				return null;
			}
		}

		public int[] rightIndexes() {
			switch (this) {
			case FRONT:
				return EDGE_LEFT;
			case BACK:
				return EDGE_LEFT;
			case LEFT:
				return EDGE_LEFT;
			case RIGHT:
				return EDGE_LEFT;
			case UP:
				return EDGE_TOP;
			case DOWN:
				return EDGE_BOTTOM;
			default:
				return null;
			}
		}

		public Face downFace() {
			switch (this) {
			case FRONT:
				return DOWN;
			case BACK:
				return DOWN;
			case LEFT:
				return DOWN;
			case RIGHT:
				return DOWN;
			case UP:
				return FRONT;
			case DOWN:
				return BACK;
			default:
				return null;
			}
		}

		public int[] downIndexes() {
			switch (this) {
			case FRONT:
				return EDGE_TOP;
			case BACK:
				return EDGE_BOTTOM;
			case LEFT:
				return EDGE_LEFT;
			case RIGHT:
				return EDGE_RIGHT;
			case UP:
				return EDGE_TOP;
			case DOWN:
				return EDGE_BOTTOM;
			default:
				return null;
			}
		}

	}
}
