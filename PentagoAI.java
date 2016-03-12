package pentago;

public class PentagoAI {

	public static final int WHITE = 0, BLACK = 1;
	public static int numPrunes = 0;

	
	public ScoreObject alphaBeta(Board board, int depth, int alpha, int beta, boolean isMaxPlayer) {
		if (depth == 0 || board.isTerminalBoard()) {
			return new ScoreObject(null, board.getHeuristicValue());
		}

		ScoreObject returnMove;
		ScoreObject bestMove = null;

		if (isMaxPlayer) {
			for (Board child : board.getChildren(WHITE)) {
				if (child != null) {
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
				} else{
					break;
				}
			}

			return bestMove;
		} else {
			for (Board child : board.getChildren(BLACK)) {
				if (child != null) {
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
				} else{
					break;
				}
			}
			return bestMove;
		}
	}
}
