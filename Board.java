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
		assert temp.length() == 36;

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

	private void setCell(Board b, int color, int pos) {
		b.board[color] |= (1L << pos);
		System.out.println("Adding marble to " + (color == 0 ? "white" : "black") + "'s position " + pos);
	}

	private long getCell(int color, int pos) {
		return (this.board[color] & (1L << pos)) >> pos;

	}

	private static String getStringFromLong(Long b) {
		String temp = Long.toBinaryString(b);
		return String.format("%36s", temp).replace(' ', '0');
		// beware, leading 0s lost when Long-->str, so pad them back
	}
}