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
        game.play(COMPUTER, HUMAN);
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
        String s_pos;
        int pos;
        do {
            s_pos = javax.swing.JOptionPane.showInputDialog("Please enter move position [0, 35]");
            pos = Integer.parseInt(s_pos);
        } while (b.getCell(player, pos) != 0 || b.getCell(1 - player, pos) != 0 || pos < 0 || pos > 35);

        String s_quad;
        int quad;
        do {
            s_quad = javax.swing.JOptionPane.showInputDialog("Please choose quadrant [1, 4]");
            quad = Integer.parseInt(s_quad);
        } while (quad < 1 || quad > 4);

        String s_clockwise;
        int clockwise;
        do {
            s_clockwise = javax.swing.JOptionPane.showInputDialog("1 for clockwise, 0 for anticlockwise");
            clockwise = Integer.parseInt(s_clockwise);
        } while (clockwise < 0 || clockwise > 1);

        System.out.println("human (" + (player == MAX_PLAYER ? "white" : "black") + ") set pos " + pos + " and rotated quadrant " + quad + (clockwise == 1 ? " clockwise" : " anticlockwise"));

        b.occupyCell(player, pos);
        b.rotateQuadrant(quad, clockwise == 1);
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
