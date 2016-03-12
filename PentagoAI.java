package pentago;

public class PentagoAI {

	public static final int WHITE = 0, BLACK = 1;

	public static void main(String[] args) {
		Board b = new Board();
		 b.board[0] = 0b100001010001000100000000010000100001L;
		 b.board[1] = 0b010010000100000000100010000010010010L;
		// b.print(WHITE);
		// b.print(BLACK);
		/*
		b.occupyCell(WHITE, 14);
		b.occupyCell(BLACK, 6);
		b.occupyCell(WHITE, 0);
		*/
		b.print();


		b.rotateQuadrant(4, false);
		b.rotateQuadrant(4, true);
		b.rotateQuadrant(3, false);
		b.rotateQuadrant(3, true);
		b.rotateQuadrant(2, false);
		b.rotateQuadrant(2, true);
		b.rotateQuadrant(1, false);
		b.rotateQuadrant(1, true);

		b.print();
		//System.out.println(b.isTerminalBoard());
		 //System.out.println(0b111100_000000_000000_000000_000000_000000L == (0b1111L<<32));
	}

}
