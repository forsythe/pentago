package pentago;

public class PentagoAI {

	public static final int WHITE = 0, BLACK = 1;
	public static int numPrunes = 0;

	public static void main(String[] args) {
		// Board b = new Board(0b100001010001000100000000010000100001L, 0b010010000100000000100010000010010010L);
		Board b = new Board(0b0L, 0b0L);

		PentagoAI bob = new PentagoAI();
		bob.alphaBeta(b, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		System.out.println("done with " + numPrunes + " prunes");
		
		// System.out.println(b.isTerminalBoard());
		// System.out.println(0b111100_000000_000000_000000_000000_000000L == (0b1111L<<32));
	}

	public int alphaBeta(Board board, int depth, int a, int b, boolean isMaxPlayer) {
		
		if (depth == 0 | board.isTerminalBoard())
			return board.getHeuristicValue();

		if (isMaxPlayer) {
			for (Board child : board.getChildren(WHITE)) {
				if (child != null) {
					a = Math.max(a, alphaBeta(child, depth - 1, a, b, false));
					if (a >= b) {
						//System.out.println("depth: " + depth + ", " + "alpha: " + a + ", beta: " + b + "\tPRUNING***************" );
						numPrunes++;
						break;
					}
				} else {
					break;
				}
			}
			return a;
		} else {
			for (Board child : board.getChildren(BLACK)) {
				if (child != null) {
					b = Math.min(b, alphaBeta(child, depth - 1, a, b, true));
					if (a >= b) {
						//System.out.println("depth: " + depth + ", " + "alpha: " + a + ", beta: " + b + "\tPRUNING***************" );
						numPrunes++;
						break;
					}
				} else {
					break;
				}
			}
			return b;
		}
	}

}
