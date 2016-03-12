package pentago;

public class GameManager {

	public static void main(String[] args) {
		Board b = new Board(0b0L, 0b0L);
		PentagoAI bob = new PentagoAI();
		int ply = 4;

		// Play as player 1
		/*
		 * for (int k = 0; k < 18; k++) {
		 * 
		 * String pos = javax.swing.JOptionPane.showInputDialog("Please enter move position");
		 * String quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant");
		 * String clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
		 * System.out.println("You set pos " + pos + " and rotated quadrant " + quad + (Integer.parseInt(clockwise) == 1 ? " clockwise" : " anticlockwise"));
		 * 
		 * b.occupyCell(0, Integer.parseInt(pos));
		 * b.rotateQuadrant(Integer.parseInt(quad), Integer.parseInt(clockwise) == 1);
		 * b.print();
		 * 
		 * if (hasWinner(b))
		 * break;
		 * 
		 * ScoreObject n = bob.alphaBeta(b, ply, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, false); // +-1 because -maxValue is invalid
		 * System.out.println("Computer sets pos " + n.board.movePos + " and rotates quadrant " + n.board.quadrant + (n.board.moveClockwise ? " clockwise" : " anticlockwise"));
		 * b.occupyCell(1, n.board.movePos);
		 * b.rotateQuadrant(n.board.quadrant, n.board.moveClockwise);
		 * b.print();
		 * 
		 * if (hasWinner(b))
		 * break;
		 * }
		 */

		for (int k = 0; k < 18; k++) {
			ScoreObject n = bob.alphaBeta(b, ply, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, false); // +-1 because -maxValue is invalid
			System.out.println("Computer sets pos " + n.board.movePos + " and rotates quadrant " + n.board.quadrant + (n.board.moveClockwise ? " clockwise" : " anticlockwise"));
			b.occupyCell(1, n.board.movePos);
			b.rotateQuadrant(n.board.quadrant, n.board.moveClockwise);
			b.print();

			if (hasWinner(b))
				break;

			String pos = javax.swing.JOptionPane.showInputDialog("Please enter move position");
			String quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant");
			String clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
			System.out.println("You set pos " + pos + " and rotated quadrant " + quad + (Integer.parseInt(clockwise) == 1 ? " clockwise" : " anticlockwise"));

			b.occupyCell(0, Integer.parseInt(pos));
			b.rotateQuadrant(Integer.parseInt(quad), Integer.parseInt(clockwise) == 1);
			b.print();

			if (hasWinner(b))
				break;

		}

	}

	public static boolean hasWinner(Board b) {
		if (b.hasWinner() >= 0) {
			if (b.hasWinner() == 0)
				System.out.println("WHITE WINS!");
			else if (b.hasWinner() == 1)
				System.out.println("BLACK WINS!");
			else
				System.out.println("tie");
			return true;
		}
		return false;
	}
}
