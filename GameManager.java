package pentago;

public class GameManager {

    final static String HUMAN = "human", COMPUTER = "computer";
    final static int MAX_PLAYER = 0, MIN_PLAYER = 1, PLY = 4;

    PentagoAI AIbrain;
    Board b;

    public GameManager() {
        AIbrain = new PentagoAI(PLY);
        b = new Board();
    }

    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.play(COMPUTER, COMPUTER);
    }

    public void play(String p1, String p2) {
        System.out.println(p1 + " (white) v. " + p2 + " (black)");

        if (p1.equals(HUMAN) && p2.equals(COMPUTER)) {
            for (int k = 0; k < 18; k++) {
                doUserInput(b, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doAIMove(b, AIbrain, MIN_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
            }
        } else if (p1.equals(COMPUTER) && p2.equals(HUMAN)) {
            for (int k = 0; k < 18; k++) {
                doAIMove(b, AIbrain, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doUserInput(b, MIN_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
            }
        } else if (p1.equals(HUMAN) && p2.equals(HUMAN)) {
            for (int k = 0; k < 18; k++) {
                doUserInput(b, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doUserInput(b, MIN_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
            }
        } else if (p1.equals(COMPUTER) && p2.equals(COMPUTER)){ 
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
        ScoreObject n = robot.alphaBeta(b, player);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // divide by 1000000 to get milliseconds.
        System.out.println("Computer (" + (player == MAX_PLAYER ? "white" : "black") + ") set pos " + n.board.movePos + " and rotates quadrant " + n.board.quadrant + (n.board.moveClockwise ? " clockwise" : " anticlockwise"));
        b.occupyCell(player == MAX_PLAYER ? MAX_PLAYER : MIN_PLAYER, n.board.movePos);
        b.rotateQuadrant(n.board.quadrant, n.board.moveClockwise);
        b.print();
        System.out.println(duration + " ms to find move");
    }

    public void doUserInput(Board b, int player) {
        String pos = javax.swing.JOptionPane.showInputDialog("Please enter move position");
        String quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant");
        String clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
        System.out.println("Human (" + (player == MAX_PLAYER ? "white" : "black") + ") set pos " + pos + " and rotated quadrant " + quad + (Integer.parseInt(clockwise) == 1 ? " clockwise" : " anticlockwise"));

        b.occupyCell(player, Integer.parseInt(pos));
        b.rotateQuadrant(Integer.parseInt(quad), Integer.parseInt(clockwise) == 1);
        b.print();
    }

    public static boolean hasWinnerOrTie(Board b) {
        if (b.hasWinner() >= 0) {
            switch (b.hasWinner()) {
                case 0:
                    System.out.println("WHITE WINS!");
                    break;
                case 1:
                    System.out.println("BLACK WINS!");
                    break;
                default:
                    System.out.println("Tie");
                    break;
            }
            return true;
        }
        return false;
    }
}
