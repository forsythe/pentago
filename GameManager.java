package pentago;

public class GameManager {
	final String[] NAMES = { "human", "computer" };
	final int MAX_PLAYER = 0, MIN_PLAYER = 1;
	final int ply = 3;

	public static void main(String[] args) {
		GameManager game = new GameManager();
		game.play(1, 1);
	}

	public void play(int p1, int p2) {// (0, 1) is human v computer, (1, 1) computer v computer
		Board b = new Board(0b0L, 0b0L);

		PentagoAI AIbrain = new PentagoAI(ply);

		System.out.println(NAMES[p1] + " v. " + NAMES[p2]);

		if (p1 == 0 && p2 == 1) { // human white v computer black
			for (int k = 0; k < 18; k++) {
				doUserInput(b, MAX_PLAYER);
				if (hasWinnerOrTie(b))
					break;
				doAIMove(b, AIbrain, MIN_PLAYER);
				if (hasWinnerOrTie(b))
					break;
			}
		} else if (p1 == 1 && p2 == 0) { // computer white v human black
			for (int k = 0; k < 18; k++) {

				doAIMove(b, AIbrain, MAX_PLAYER);

				if (hasWinnerOrTie(b))
					break;

				doUserInput(b, MIN_PLAYER);

				if (hasWinnerOrTie(b))
					break;
			}
		} else if (p1 == 0 && p2 == 0) { // human white v human white
			for (int k = 0; k < 18; k++) {

				doUserInput(b, MAX_PLAYER);

				if (hasWinnerOrTie(b))
					break;

				doUserInput(b, MIN_PLAYER);

				if (hasWinnerOrTie(b))
					break;
			}
		} else { // computer white v computer black
			for (int k = 0; k < 18; k++) {

				doAIMove(b, AIbrain, MAX_PLAYER);

				if (hasWinnerOrTie(b))
					break;

				doAIMove(b, AIbrain, MIN_PLAYER);

				if (hasWinnerOrTie(b))
					break;
			}
		}
	}

	public void doAIMove(Board b, PentagoAI robot, int player) {
		long startTime = System.nanoTime();
		ScoreObject n = robot.alphaBeta(b, player); // +-1 because -maxValue is invalid
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; // divide by 1000000 to get milliseconds.
		System.out.println("Computer (" + (player == MAX_PLAYER ? "white"
				: "black") + ") set pos " + n.board.movePos + " and rotates quadrant " + n.board.quadrant + (n.board.moveClockwise ? " clockwise" : " anticlockwise"));
		b.occupyCell(player == MAX_PLAYER ? MAX_PLAYER : MIN_PLAYER, n.board.movePos);
		b.rotateQuadrant(n.board.quadrant, n.board.moveClockwise);
		b.print();
		System.out.println(duration + " ms to find move");
	}

	public void doUserInput(Board b, int player) {
		String pos = javax.swing.JOptionPane.showInputDialog("Please enter move position");
		String quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant");
		String clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
		System.out.println(
				"You (" + (player == MAX_PLAYER ? "white" : "black") + ") set pos " + pos + " and rotated quadrant " + quad + (Integer.parseInt(clockwise) == 1 ? " clockwise" : " anticlockwise"));

		b.occupyCell(player, Integer.parseInt(pos));
		b.rotateQuadrant(Integer.parseInt(quad), Integer.parseInt(clockwise) == 1);
		b.print();
	}

	public static boolean hasWinnerOrTie(Board b) {
		if (b.hasWinner() >= 0) {
			if (b.hasWinner() == 0)
				System.out.println("WHITE WINS!");
			else if (b.hasWinner() == 1)
				System.out.println("BLACK WINS!");
			else
				System.out.println("Tie");

			return true;
		}
		return false;
	}
}
