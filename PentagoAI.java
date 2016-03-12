package pentago;

public class PentagoAI {

	public static final int WHITE = 0, BLACK = 1;

	public static void main(String[] args) {
		Board b = new Board();
		//b.board[0] = 0b100001000000000000000000000000100001L;
		//b.board[1] = 0b010010000000000000000000000000010010L;
		//b.print(WHITE);
		//b.print(BLACK);
		b.board[0] = 0L;
		b.board[1] = 0L;
		b.print();
		b.setCell(WHITE, 1);
		b.print();
		b.setCell(BLACK,  24);
		b.print();

	}

}
