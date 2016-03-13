package pentago;

public class PentagoAI {

	public static final int P_MAX = 0, P_MIN = 1;
	public static int maxDepth;

	public PentagoAI(int maxDepth_rhs) {
		maxDepth = maxDepth_rhs;
	}

	public ScoreObject alphaBeta(Board b, boolean isMaxPlayer) {
		return alphaBeta(b, maxDepth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, isMaxPlayer);
	}

	public ScoreObject alphaBeta(Board board, int depth, int alpha, int beta, boolean isMaxPlayer) {
		if (depth == 0 || board.isTerminalBoard()) 
			return new ScoreObject(null, board.getHeuristicValue());
		

		ScoreObject returnMove;
		ScoreObject bestMove = null;

		if (isMaxPlayer) {
			for (Board child : board.getChildren(P_MAX)) {
				returnMove = alphaBeta(child, depth - 1, alpha, beta, false);

				if (bestMove == null) {
					bestMove = returnMove;
					bestMove.board = child;
				} else if (bestMove.score < returnMove.score) {
					bestMove = returnMove;
					bestMove.board = child;
				}

				if (returnMove.score > alpha) {
					alpha = returnMove.score;
					bestMove = returnMove;
				}

				if (alpha >= beta) {
					bestMove.score = beta;
					bestMove.board = null;

					return bestMove;
				}

			}

			return bestMove;
		} else {
			for (Board child : board.getChildren(P_MIN)) {
				returnMove = alphaBeta(child, depth - 1, alpha, beta, true);

				if (bestMove == null) {
					bestMove = returnMove;
					bestMove.board = child;
				} else if (bestMove.score > returnMove.score) {
					bestMove = returnMove;
					bestMove.board = child;
				}

				if (returnMove.score < beta) {
					beta = returnMove.score;
					bestMove = returnMove;
				}

				if (alpha >= beta) {
					bestMove.score = alpha;
					bestMove.board = null;

					return bestMove;
				}

			}
			return bestMove;
		}
	}
}
