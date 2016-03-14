package pentago;

public class GameManager {

    final static String HUMAN = "human", COMPUTER = "computer";
    final static int MAX_PLAYER = 0, MIN_PLAYER = 1, SEARCH_DEPTH_MAX = 4;

    PentagoAI AIbrain;
    Board b;

    public GameManager() {
        AIbrain = new PentagoAI(SEARCH_DEPTH_MAX);
        b = new Board();
    }

    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.play(COMPUTER, COMPUTER);
    }

    public void play(String white, String black) {
        System.out.println(white + " (white) v. " + black + " (black)");

        if (white.equals(HUMAN) && black.equals(COMPUTER)) {
            while (!hasWinnerOrTie(b)) {
                doUserMove(b, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doAIMove(b, AIbrain, MIN_PLAYER);
            }
        } else if (white.equals(COMPUTER) && black.equals(HUMAN)) {
            while (!hasWinnerOrTie(b)) {
                doAIMove(b, AIbrain, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doUserMove(b, MIN_PLAYER);
            }
        } else if (white.equals(HUMAN) && black.equals(HUMAN)) {
            while (!hasWinnerOrTie(b)) {
                doUserMove(b, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doUserMove(b, MIN_PLAYER);
            }
        } else if (white.equals(COMPUTER) && black.equals(COMPUTER)) {
            while (!hasWinnerOrTie(b)) {
                doAIMove(b, AIbrain, MAX_PLAYER);
                if (hasWinnerOrTie(b))
                    break;
                doAIMove(b, AIbrain, MIN_PLAYER);
            }
        }
    }

    public void doAIMove(Board b, PentagoAI robot, int player) {
        long startTime = System.nanoTime();
        ScoreObject n = robot.alphaBeta(b, player);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // divide by 1000000 to get milliseconds.
        System.out.println("Computer (" + (player == MAX_PLAYER ? "white" : "black") + ") set pos " + n.board.movePos + " and rotates quadrant " + n.board.quadrant + (n.board.moveClockwise ? " clockwise" : " anticlockwise"));
        b.occupyCell(player, n.board.movePos);
        b.rotateQuadrant(n.board.quadrant, n.board.moveClockwise);
        b.print();
        System.out.println(duration + " ms to find move");
    }

    public void doUserMove(Board b, int player) {
        String pos = javax.swing.JOptionPane.showInputDialog("Please enter move position");
        String quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant");
        String clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
        System.out.println("Human (" + (player == MAX_PLAYER ? "white" : "black") + ") set pos " + pos + " and rotated quadrant " + quad + (Integer.parseInt(clockwise) == 1 ? " clockwise" : " anticlockwise"));

        b.occupyCell(player, Integer.parseInt(pos));
        b.rotateQuadrant(Integer.parseInt(quad), Integer.parseInt(clockwise) == 1);
        b.print();
    }

    public static boolean hasWinnerOrTie(Board b) {
        switch (b.hasWinner()) {
            case -1:
                return false;
            case 0:
                System.out.println("WHITE WINS!");
                return true;
            case 1:
                System.out.println("BLACK WINS!");
                return true;
            default:
                System.out.println("Tie");
                return true;
        }
    }
}
