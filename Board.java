package pentago;

public class Board {
	private static final int WHITE = 0, BLACK = 1;

	long[] board = new long[2]; // 0 for white, 1 for black

	public void print(int color) {
		System.out.println((color == 0 ? "white" : "black"));

		String temp = getStringFromLong(this.board[color]);
		assert temp.length() == 36;

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
			if (getCell(WHITE, k) == getCell(BLACK, k)) {
				temp += "O";
			} else if (getCell(WHITE, k) == 1) {
				temp += "W";
			} else {
				temp += "B";
			}
		}
		System.out.println("white: " + getStringFromLong(board[WHITE]));
		System.out.println("black: " + getStringFromLong(board[BLACK]));
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

	private static String getStringFromLong(Long b) {
		String temp = Long.toBinaryString(b);
		return String.format("%36s", temp).replace(' ', '0');
		// beware, leading 0s lost when Long-->str, so pad them back
	}

	// Sets the cell value to 1 for board[color]
	public void occupyCell(int color, int pos) {
		board[color] |= (1L << pos);
		System.out.println("Adding marble to " + (color == 0 ? "white" : "black") + "'s position " + pos);
	}

	// Check if the specified cell is taken with marble
	public long getCell(int color, int pos) {
		return (board[color] & (1L << pos)) >> pos;
	}

	// Sets the cell value to something of our choice
	private void setCell(int color, int pos, long value) {
		System.out.println("Setting playerboard " + color + "'s bit position: " + pos + " to the value " + value);
		board[color] ^= (-value ^ board[color]) & (1L << pos); // Relies on twos complement for long OTHERWISE GG
	}

	public void rotateQuadrant(int quadrant, boolean clockwise) {
		long temp;
		if (clockwise) {
			switch (quadrant) {
			case 1:
				for (int k = WHITE; k <= BLACK; k++) {
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
				for (int k = WHITE; k <= BLACK; k++) {
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
				break;
			case 4:
				break;

			}

		} else {
			switch (quadrant) {
			case 1:
				break;
			}
		}
	}

	static long[] masks = {
			// Horizontal masks
			0b111110_000000_000000_000000_000000_000000L,
			0b011111_000000_000000_000000_000000_000000L,
			0b000000_111110_000000_000000_000000_000000L,
			0b000000_011111_000000_000000_000000_000000L,
			0b000000_000000_111110_000000_000000_000000L,
			0b000000_000000_011111_000000_000000_000000L,
			0b000000_000000_000000_111110_000000_000000L,
			0b000000_000000_000000_011111_000000_000000L,
			0b000000_000000_000000_000000_111110_000000L,
			0b000000_000000_000000_000000_011111_000000L,
			0b000000_000000_000000_000000_000000_111110L,
			0b000000_000000_000000_000000_000000_011111L,

			// Vertical masks
			0b100000_100000_100000_100000_100000_000000L,
			0b010000_010000_010000_010000_010000_000000L,
			0b001000_001000_001000_001000_001000_000000L,
			0b000100_000100_000100_000100_000100_000000L,
			0b000010_000010_000010_000010_000010_000000L,
			0b000001_000001_000001_000001_000001_000000L,

			0b000000_100000_100000_100000_100000_100000L,
			0b000000_010000_010000_010000_010000_010000L,
			0b000000_001000_001000_001000_001000_001000L,
			0b000000_000100_000100_000100_000100_000100L,
			0b000000_000010_000010_000010_000010_000010L,
			0b000000_000001_000001_000001_000001_000001L,

			// Forward slash masks
			0b000010_000100_001000_010000_100000_000000L,
			0b000000_000010_000100_001000_010000_100000L,

			0b000001_000010_000100_001000_010000_000000L,
			0b000000_000001_000010_000100_001000_010000L,

			// Backslash masks
			0b100000_010000_001000_000100_000010_000000L,
			0b000000_100000_010000_001000_000100_000010L,

			0b010000_001000_000100_000010_000001_000000L,
			0b000000_010000_001000_000100_000010_000001L,

	};

	public boolean isTerminalBoard() {
		if ((board[WHITE] | board[BLACK]) == 0b111111111111111111111111111111111111L)
			return true;

		for (long mask : masks)
			if ((mask & board[WHITE]) == mask || (mask & board[BLACK]) == mask)
				return true;

		return false;
	}
}