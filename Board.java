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

	public void setCell(int color, int pos) {
		this.board[color] |= (1L << pos);
		System.out.println("Adding marble to " + (color == 0 ? "white" : "black") + "'s position " + pos);
	}

	public long getCell(int color, int pos) {
		return (this.board[color] & (1L << pos)) >> pos;
	}

	private static String getStringFromLong(Long b) {
		String temp = Long.toBinaryString(b);
		return String.format("%36s", temp).replace(' ', '0');
		// beware, leading 0s lost when Long-->str, so pad them back
	}

	static long[] horizontal_masks = {
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
			0b000000_000000_000000_000000_000000_011111L
	};

	static long[] vertical_masks = {
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
			0b000000_000001_000001_000001_000001_000001L
	};

	static long[] forwardslash_masks = {
			0b000010_000100_001000_010000_100000_000000L,
			0b000000_000010_000100_001000_010000_100000L,

			0b000001_000010_000100_001000_010000_000000L,
			0b000000_000001_000010_000100_001000_010000L
	};

	static long [] backslash_masks = {
			0b100000_010000_001000_000100_000010_000000L,
			0b000000_100000_010000_001000_000100_000010L,
			
			0b010000_001000_000100_000010_000001_000000L,
			0b000000_010000_001000_000100_000010_000001L,
	};

	public boolean isTerminalBoard() {
		if ((board[WHITE] | board[BLACK]) == 0b111111111111111111111111111111111111L) {
			return true;
		}

	}
}