package org.kchandra423.engine;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

public class Engine {

    private static Move bestMove = null;
    private static boolean player = false;
    private static int bestValue = 0;


    public static Move getBestMove(Board board, int depth) {
        bestMove = null;
        player = board.getSideToMove() == Side.WHITE;
        bestValue = player ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        miniMaxSeaarch(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
        return bestMove;
    }

    private static int miniMaxSeaarch(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return Evaluator.evaluate(board);
        }
        for (Move move : board.legalMoves()) {
            board.doMove(move);
            int value = miniMaxSeaarch(board.clone(), depth - 1, alpha, beta, !maximizingPlayer);
            if (maximizingPlayer) {
                if (value > bestValue) {
                    bestValue = value;
                    if (player) {
                        bestMove = move;
                    }
                }
                alpha = Math.max(alpha, value);
                if (beta <= alpha) {
                    break;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    if (!player) {
                        bestMove = move;
                    }
                }
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break;
                }
            }
            board.undoMove();
        }
        return bestValue;
    }

}



