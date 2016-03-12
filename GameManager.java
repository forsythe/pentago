package pentago;

public class GameManager {

	public static void main(String[] args) {
		// Board b = new Board(0b100001010001000100000000010000100001L, 0b010010000100000000100010000010010010L);
		Board b = new Board(0b0L, 0b0L);
		PentagoAI bob = new PentagoAI();

		/*b.occupyCell(0, 31);
		b.occupyCell(0, 25);
		b.occupyCell(0, 19);
		b.occupyCell(0, 13);*/

		//b.print();
		
		for (int k = 0; k < 35; k++){
			String pos = javax.swing.JOptionPane.showInputDialog("Please enter move position");
			String quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant");
			String clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
			b.occupyCell(0, Integer.parseInt(pos));
			b.rotateQuadrant(Integer.parseInt(quad), Integer.parseInt(clockwise)==1);
			b.print();
			
			ScoreObject n = bob.alphaBeta(b, 2, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, false); // +-1 because -maxValue is invalid
			System.out.println("Computer sets pos " + n.board.movePos + " and rotates quadrant " + n.board.quadrant + (n.board.moveClockwise?" clockwise" : "anticlockwise"));
			b.occupyCell(1,  n.board.movePos);
			b.rotateQuadrant(n.board.quadrant, n.board.moveClockwise);
			b.print();
		}

		System.out.println("done with " + bob.numPrunes + " prunes");

		// System.out.println(b.isTerminalBoard());
		// System.out.println(0b111100_000000_000000_000000_000000_000000L == (0b1111L<<32));
	}
}
