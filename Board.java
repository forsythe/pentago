package pentago;

public class Board {
	private static final int WHITE = 0, BLACK = 1;

	long[] board = new long[2]; // 0 for white, 1 for black

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

	public void print(int color) {
		System.out.println((color == 0 ? "white" : "black"));

		String temp = getBinaryStringFromLong(this.board[color]);
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
		System.out.println("white: " + getBinaryStringFromLong(board[WHITE]));
		System.out.println("black: " + getBinaryStringFromLong(board[BLACK]));
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
		// System.out.println("Setting playerboard " + color + "'s bit position: " + pos + " to the value " + value);
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
				for (int k = WHITE; k <= BLACK; k++) {
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
				for (int k = WHITE; k <= BLACK; k++) {
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
				for (int k = WHITE; k <= BLACK; k++) {
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
				for (int k = WHITE; k <= BLACK; k++) {
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
				for (int k = WHITE; k <= BLACK; k++) {
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
				for (int k = WHITE; k <= BLACK; k++) {
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
			//Horizontal masks			
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
			
			//Vertical masks
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
			
			//Forward slash masks "/"
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

			//Backslash masks "\"
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
		if ((board[WHITE] | board[BLACK]) == 0b111111111111111111111111111111111111L)
			return true;

		for (long mask : masks_5_consec)
			if ((mask & board[WHITE]) == mask || (mask & board[BLACK]) == mask)
				return true;

		return false;
	}
}