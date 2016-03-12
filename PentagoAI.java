package pentago;

public class PentagoAI {

	public static void main(String[] args) {
		Board b = new Board();
		b.board[0] = 0b100001000000000000000000000000100001L;
		printBoard(b, 0);
		//setCell(b, 0, 4);
		//printBoard(b, 0);
	}
	
	private static void printBoard(Board b, int color){
		
		String temp = Long.toBinaryString(b.board[color]);
		for (int k = 0; k < 36; k++){
			System.out.print(temp.charAt(k));
			if (k%3==0) System.out.print("|");
			if (k%6==0) System.out.println();
			if (k==18) System.out.println("---+----");
		}
		System.out.println();
	}
	
	private static void setCell(Board b, int color, int pos){
		b.board[color] |= (1L<<pos);
	}
}
