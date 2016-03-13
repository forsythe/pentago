package pentago;

import java.util.HashSet;

public class Board {
	private static final int P_MAX = 0, P_MIN = 1;

	public long[] board = new long[2]; // 0 for P_MAX, 1 for P_MIN
	public int movePos = -1, quadrant = -1;
	public boolean moveClockwise = false;

	public Board(long maxBoard, long minBoard) { // For custom games
		board[0] = maxBoard;
		board[1] = minBoard;
	}

	/*
	 * Board representation: [35, 34, 33, .... 2, 1, 0]
	 * 
	 * 35| 34| 33||| 32| 31| 30
	 * 29| 28| 27||| 26| 25| 24
	 * 23| 22| 21||| 20| 19| 18
	 * ===========+============
	 * 17| 16| 15||| 14| 13| 12
	 * 11| 10| 9 ||| 8 | 7 | 6
	 * 5 | 4 | 3 ||| 2 | 1 | 0
	 */

	public void print(int player) {

		String temp = getBinaryStringFromLong(this.board[player]);

		System.out.println(temp);
		for (int k = 0; k < 36; k++) {
			System.out.print(temp.charAt(k));
			if ((k + 1) % 3 == 0)
				System.out.print("|");
			if ((k + 1) % 6 == 0)
				System.out.println();
			if (k == 17)
				System.out.println("---+----");
		}
		System.out.println();
	}

	public void print() {

		String temp = "";

		for (int k = 35; k >= 0; k--) {
			if (getCell(P_MAX, k) == getCell(P_MIN, k))
				temp += " ";
			else if (getCell(P_MAX, k) == 1)
				temp += "W";
			else
				temp += "B";
		}

		for (int k = 0; k < 36; k++) {
			System.out.print(temp.charAt(k));
			if ((k + 1) % 3 == 0)
				System.out.print("|");
			if ((k + 1) % 6 == 0)
				System.out.println();
			if (k == 17)
				System.out.println("---+----");
		}
		System.out.println();
	}

	private static String getBinaryStringFromLong(Long b) {
		String temp = Long.toBinaryString(b);
		return String.format("%36s", temp).replace(' ', '0');
		// beware, leading 0s lost when Long-->str, so pad them back
	}

	// Sets the cell value to 1 for board[player]
	public void occupyCell(int player, int pos) {
		board[player] |= (1L << pos);
		// System.out.println("Adding marble to " + (player == 0 ? "P_MAX" : "P_MIN") + "'s position " + pos);
	}

	// Check if the specified cell is taken with marble
	public long getCell(int player, int pos) {
		return (board[player] & (1L << pos)) >> pos;
	}

	// Sets the cell value to something of our choice
	private void setCell(int player, int pos, long value) {
		// System.out.println("Setting playerboard " + player + "'s bit position: " + pos + " to the value " + value);
		board[player] ^= (-value ^ board[player]) & (1L << pos); // Relies on twos complement for long OTHERWISE GG
	}

	public void rotateQuadrant(int quadrant, boolean clockwise) {
		// System.out.println("Rotated quadrant " + quadrant + (clockwise? " clockwise":" counterclockwise"));
		long temp;
		if (clockwise) {
			switch (quadrant) {
			case 1:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 24);
					setCell(k, 24, getCell(k, 31));
					setCell(k, 31, getCell(k, 26));
					setCell(k, 26, getCell(k, 19));
					setCell(k, 19, temp);
					temp = getCell(k, 18);
					setCell(k, 18, getCell(k, 30));
					setCell(k, 30, getCell(k, 32));
					setCell(k, 32, getCell(k, 20));
					setCell(k, 20, temp);
				}
				break;
			case 2:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 27);
					setCell(k, 27, getCell(k, 34));
					setCell(k, 34, getCell(k, 29));
					setCell(k, 29, getCell(k, 22));
					setCell(k, 22, temp);
					temp = getCell(k, 21);
					setCell(k, 21, getCell(k, 33));
					setCell(k, 33, getCell(k, 35));
					setCell(k, 35, getCell(k, 23));
					setCell(k, 23, temp);
				}
				break;
			case 3:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 9);
					setCell(k, 9, getCell(k, 16));
					setCell(k, 16, getCell(k, 11));
					setCell(k, 11, getCell(k, 4));
					setCell(k, 4, temp);
					temp = getCell(k, 3);
					setCell(k, 3, getCell(k, 15));
					setCell(k, 15, getCell(k, 17));
					setCell(k, 17, getCell(k, 5));
					setCell(k, 5, temp);
				}
				break;
			case 4:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 6);
					setCell(k, 6, getCell(k, 13));
					setCell(k, 13, getCell(k, 8));
					setCell(k, 8, getCell(k, 1));
					setCell(k, 1, temp);
					temp = getCell(k, 0);
					setCell(k, 0, getCell(k, 12));
					setCell(k, 12, getCell(k, 14));
					setCell(k, 14, getCell(k, 2));
					setCell(k, 2, temp);
				}
				break;
			}

		} else {
			switch (quadrant) {
			case 1:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 24);
					setCell(k, 24, getCell(k, 19));
					setCell(k, 19, getCell(k, 26));
					setCell(k, 26, getCell(k, 31));
					setCell(k, 31, temp);
					temp = getCell(k, 18);
					setCell(k, 18, getCell(k, 20));
					setCell(k, 20, getCell(k, 32));
					setCell(k, 32, getCell(k, 30));
					setCell(k, 30, temp);
				}
				break;
			case 2:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 27);
					setCell(k, 27, getCell(k, 22));
					setCell(k, 22, getCell(k, 29));
					setCell(k, 29, getCell(k, 34));
					setCell(k, 34, temp);
					temp = getCell(k, 21);
					setCell(k, 21, getCell(k, 23));
					setCell(k, 23, getCell(k, 35));
					setCell(k, 35, getCell(k, 33));
					setCell(k, 33, temp);
				}
				break;
			case 3:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 9);
					setCell(k, 9, getCell(k, 4));
					setCell(k, 4, getCell(k, 11));
					setCell(k, 11, getCell(k, 16));
					setCell(k, 16, temp);
					temp = getCell(k, 3);
					setCell(k, 3, getCell(k, 5));
					setCell(k, 5, getCell(k, 17));
					setCell(k, 17, getCell(k, 15));
					setCell(k, 15, temp);
				}
				break;
			case 4:
				for (int k = P_MAX; k <= P_MIN; k++) {
					temp = getCell(k, 6);
					setCell(k, 6, getCell(k, 1));
					setCell(k, 1, getCell(k, 8));
					setCell(k, 8, getCell(k, 13));
					setCell(k, 13, temp);
					temp = getCell(k, 0);
					setCell(k, 0, getCell(k, 2));
					setCell(k, 2, getCell(k, 14));
					setCell(k, 14, getCell(k, 12));
					setCell(k, 12, temp);
				}
				break;
			}
		}
	}

	static long[] masks_5_consec = {
			// Horizontal masks
			0b111110_000000_000000_000000_000000_000000L,
			0b011111_000000_000000_000000_000000_000000L,
			0b111110_000000_000000_000000_000000L,
			0b011111_000000_000000_000000_000000L,
			0b111110_000000_000000_000000L,
			0b011111_000000_000000_000000L,
			0b111110_000000_000000L,
			0b011111_000000_000000L,
			0b111110_000000L,
			0b011111_000000L,
			0b111110L,
			0b011111L,

			// Vertical masks
			0b100000_100000_100000_100000_100000_000000L,
			0b10000_010000_010000_010000_010000_000000L,
			0b1000_001000_001000_001000_001000_000000L,
			0b100_000100_000100_000100_000100_000000L,
			0b10_000010_000010_000010_000010_000000L,
			0b1_000001_000001_000001_000001_000000L,

			0b100000_100000_100000_100000_100000L,
			0b10000_010000_010000_010000_010000L,
			0b1000_001000_001000_001000_001000L,
			0b100_000100_000100_000100_000100L,
			0b10_000010_000010_000010_000010L,
			0b1_000001_000001_000001_000001L,

			// Forward slash masks "/"
			0b10_000100_001000_010000_100000_000000L,
			0b10_000100_001000_010000_100000L,

			0b1_000010_000100_001000_010000_000000L,
			0b1_000010_000100_001000_010000L,

			// Backslash masks "\"
			0b100000_010000_001000_000100_000010_000000L,
			0b100000_010000_001000_000100_000010L,

			0b10000_001000_000100_000010_000001_000000L,
			0b10000_001000_000100_000010_000001L,
	};

	static long[] masks_4_consec = {
			// Horizontal masks
			0b111100_000000_000000_000000_000000_000000L, // MAYBE: replace with 0b1111L<<32, if no performance hit
			0b011110_000000_000000_000000_000000_000000L, // 0b1111L<<31
			0b001111_000000_000000_000000_000000_000000L,
			0b111100_000000_000000_000000_000000L,
			0b011110_000000_000000_000000_000000L,
			0b001111_000000_000000_000000_000000L,
			0b111100_000000_000000_000000L,
			0b011110_000000_000000_000000L,
			0b001111_000000_000000_000000L,
			0b111100_000000_000000L,
			0b011110_000000_000000L,
			0b001111_000000_000000L,
			0b111100_000000L,
			0b011110_000000L,
			0b001111_000000L,
			0b111100L,
			0b011110L,
			0b001111L,

			// Vertical masks
			0b100000_100000_100000_100000_000000_000000L,
			0b100000_100000_100000_100000_000000L,
			0b100000_100000_100000_100000L,

			0b010000_010000_010000_010000_000000_000000L,
			0b010000_010000_010000_010000_000000L,
			0b010000_010000_010000_010000L,

			0b001000_001000_001000_001000_000000_000000L,
			0b001000_001000_001000_001000_000000L,
			0b001000_001000_001000_001000L,

			0b000100_000100_000100_000100_000000_000000L,
			0b000100_000100_000100_000100_000000L,
			0b000100_000100_000100_000100L,

			0b000010_000010_000010_000010_000000_000000L,
			0b000010_000010_000010_000010_000000L,
			0b000010_000010_000010_000010L,

			0b000001_000001_000001_000001_000000_000000L,
			0b000001_000001_000001_000001_000000L,
			0b000001_000001_000001_000001L,

			// Forward slash masks "/"
			0b000100_001000_010000_100000_000000_000000L,
			0b000100_001000_010000_100000_000000L,
			0b000100_001000_010000_100000L,

			0b000010_000100_001000_010000_000000_000000L,
			0b000010_000100_001000_010000_000000L,
			0b000010_000100_001000_010000L,

			0b000001_000010_000100_001000_000000_000000L,
			0b000001_000010_000100_001000_000000L,
			0b000001_000010_000100_001000L,

			// Backslash masks "\"
			0b100000_010000_001000_000100_000000_000000L,
			0b100000_010000_001000_000100_000000L,
			0b100000_010000_001000_000100L,

			0b010000_001000_000100_000010_000000_000000L,
			0b010000_001000_000100_000010_000000L,
			0b010000_001000_000100_000010L,

			0b001000_000100_000010_000001_000000_000000L,
			0b001000_000100_000010_000001_000000L,
			0b001000_000100_000010_000001L
	};

	static long[] masks_3_consec = {
			// Horizontal masks
			0b111000_000000_000000_000000_000000_000000L,
			0b011100_000000_000000_000000_000000_000000L,
			0b001110_000000_000000_000000_000000_000000L,
			0b000111_000000_000000_000000_000000_000000L,
			0b111000_000000_000000_000000_000000L,
			0b011100_000000_000000_000000_000000L,
			0b001110_000000_000000_000000_000000L,
			0b000111_000000_000000_000000_000000L,
			0b111000_000000_000000_000000L,
			0b011100_000000_000000_000000L,
			0b001110_000000_000000_000000L,
			0b000111_000000_000000_000000L,
			0b111000_000000_000000L,
			0b011100_000000_000000L,
			0b001110_000000_000000L,
			0b000111_000000_000000L,
			0b111000_000000L,
			0b011100_000000L,
			0b001110_000000L,
			0b000111_000000L,
			0b111000L,
			0b011100L,
			0b001110L,
			0b000111L,

			// Vertical masks
			0b100000_100000_100000_000000_000000_000000L,
			0b100000_100000_100000_000000_000000L,
			0b100000_100000_100000_000000L,
			0b100000_100000_100000L,

			0b010000_010000_010000_000000_000000_000000L,
			0b010000_010000_010000_000000_000000L,
			0b010000_010000_010000_000000L,
			0b010000_010000_010000L,

			0b001000_001000_001000_000000_000000_000000L,
			0b001000_001000_001000_000000_000000L,
			0b001000_001000_001000_000000L,
			0b001000_001000_001000L,

			0b000100_000100_000100_000000_000000_000000L,
			0b000100_000100_000100_000000_000000L,
			0b000100_000100_000100_000000L,
			0b000100_000100_000100L,

			0b000010_000010_000010_000000_000000_000000L,
			0b000010_000010_000010_000000_000000L,
			0b000010_000010_000010_000000L,
			0b000010_000010_000010L,

			0b000001_000001_000001_000000_000000_000000L,
			0b000001_000001_000001_000000_000000L,
			0b000001_000001_000001_000000L,
			0b000001_000001_000001L,

			// Forward slash masks "/"
			0b001000_010000_100000_000000_000000_000000L,
			0b001000_010000_100000_000000_000000L,
			0b001000_010000_100000_000000L,
			0b001000_010000_100000L,

			0b000100_001000_010000_000000_000000_000000L,
			0b000100_001000_010000_000000_000000L,
			0b000100_001000_010000_000000L,
			0b000100_001000_010000L,

			0b000010_000100_001000_000000_000000_000000L,
			0b000010_000100_001000_000000_000000L,
			0b000010_000100_001000_000000L,
			0b000010_000100_001000L,

			0b000001_000010_000100_000000_000000_000000L,
			0b000001_000010_000100_000000_000000L,
			0b000001_000010_000100_000000L,
			0b000001_000010_000100L,

			// Backslash masks "\"
			0b100000_010000_001000_000000_000000_000000L,
			0b100000_010000_001000_000000_000000,
			0b100000_010000_001000_000000L,
			0b100000_010000_001000L,

			0b010000_001000_000100_000000_000000_000000L,
			0b010000_001000_000100_000000_000000L,
			0b010000_001000_000100_000000L,
			0b010000_001000_000100L,

			0b001000_000100_000010_000000_000000_000000L,
			0b001000_000100_000010_000000_000000L,
			0b001000_000100_000010_000000L,
			0b001000_000100_000010L,

			0b000100_000010_000001_000000_000000_000000L,
			0b000100_000010_000001_000000_000000L,
			0b000100_000010_000001_000000L,
			0b000100_000010_000001L
	};

	public boolean isTerminalBoard() {
		if ((board[P_MAX] | board[P_MIN]) == 0b111111111111111111111111111111111111L)
			return true;

		for (int k = 0; k < masks_5_consec.length; k++)
			if ((masks_5_consec[k] & board[P_MAX]) == masks_5_consec[k] ||
					(masks_5_consec[k] & board[P_MIN]) == masks_5_consec[k])
				return true;

		return false;
	}

	public int hasWinner() { // -1 no winner, 0 P_MAX, 1 P_MIN, 2 tie

		boolean P_MAXWin = false, P_MINWin = false;

		for (long mask : masks_5_consec) {
			if ((mask & board[P_MAX]) == mask) {
				P_MAXWin = true;
				break;
			}
		}

		for (long mask : masks_5_consec) {
			if ((mask & board[P_MIN]) == mask) {
				P_MINWin = true;
				break;
			}
		}

		if (P_MAXWin && P_MINWin)
			return 2;
		else if (P_MAXWin != P_MINWin)
			return P_MAXWin ? 0 : 1;
		else
			return -1;
	}

	private final int WEIGHT_5_CONSEC = 100_000;
	private final int WEIGHT_4_CONSEC = 1000;
	private final int WEIGHT_3_CONSEC = 100;
	private final int WEIGHT_CENTER = 5;

	public int getHeuristicValue() { // always in perspective of P_MAX player
		int P_MAXScore = 0;

		for (long mask : masks_5_consec) {
			if ((mask & board[P_MAX]) == mask)
				P_MAXScore += WEIGHT_5_CONSEC;

			if ((mask & board[P_MIN]) == mask)
				P_MAXScore -= WEIGHT_5_CONSEC;
		}

		for (long mask : masks_4_consec) {
			if ((mask & board[P_MAX]) == mask)
				P_MAXScore += WEIGHT_4_CONSEC;
			if ((mask & board[P_MIN]) == mask)
				P_MAXScore -= WEIGHT_4_CONSEC;
		}

		for (long mask : masks_3_consec) {
			if ((mask & board[P_MAX]) == mask)
				P_MAXScore += WEIGHT_3_CONSEC;
			if ((mask & board[P_MIN]) == mask)
				P_MAXScore -= WEIGHT_3_CONSEC;
		}

		P_MAXScore += WEIGHT_CENTER * (getCell(P_MAX, 25) + getCell(P_MAX, 28) + getCell(P_MAX, 10) + getCell(P_MAX, 7));
		P_MAXScore -= WEIGHT_CENTER * (getCell(P_MIN, 25) + getCell(P_MIN, 28) + getCell(P_MIN, 10) + getCell(P_MIN, 7));

		// System.out.println(P_MAXScore);
		return P_MAXScore;
	}

	public HashSet<Board> getChildren(int player) { // if player = 0, get's the moves that 0 can put
		HashSet<Board> children = new HashSet<Board>();

		for (int k = 0; k < 36; k++) {
			if (getCell(P_MAX, k) == getCell(P_MIN, k)) { // only equal when 0==0 (aka empty spot)
				for (int quadrant = 1; quadrant <= 4; quadrant++) {

					// rotate clockwise
					Board temp = new Board(this.board[P_MAX], this.board[P_MIN]);
					temp.setCell(player, k, 1);
					temp.rotateQuadrant(quadrant, true);
					temp.movePos = k;
					temp.quadrant = quadrant;
					temp.moveClockwise = true;
					children.add(temp);

					// rotate counterclockwise
					temp = new Board(this.board[P_MAX], this.board[P_MIN]);
					temp.setCell(player, k, 1);
					temp.rotateQuadrant(quadrant, false);
					temp.movePos = k;
					temp.quadrant = quadrant;
					temp.moveClockwise = false;
					children.add(temp);
				}
			}
		}
		return children;
	}

	@Override
	public int hashCode() {
		long f = board[P_MAX] << 36 + board[P_MIN];
		return 37 * 3 + (int) (f ^ (f >>> 32));
		// https://stackoverflow.com/questions/113511/best-implementation-for-hashcode-method
	}

	public boolean equals(Object obj) {
		Board b = (Board) obj;
		return (b.board[P_MIN] == board[P_MIN] && b.board[P_MAX] == board[P_MAX]);
	}
}