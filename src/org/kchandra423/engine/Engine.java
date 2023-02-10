package org.kchandra423.engine;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Engine {

    private static Move bestMove = null;
    private static boolean player = false;



    public static Move getBestMove(Board board, int depth) {
        bestMove = null;
        player = board.getSideToMove() == Side.WHITE;
        System.out.println(minimax(board, depth, player , Integer.MIN_VALUE, Integer.MAX_VALUE));
        return bestMove;
    }

    // Initial values of
// Alpha and Beta
    static int MAX = 1000;
    static int MIN = -1000;

    // Returns optimal value for
// current player (Initially called
// for root and maximizer)
    static int minimax(Board b, int depth,
                       Boolean maximizingPlayer, int alpha,
                       int beta) {
        // Terminating condition. i.e
        // leaf node is reached
        if (depth == 0)
            return Evaluator.evaluate(b);

        if (maximizingPlayer) {
            int best = MIN;
            for (Move move : b.legalMoves()) {
                b.doMove(move);
                int val = minimax(b.clone(), depth - 1,
                        false, alpha, beta);
                b.undoMove();
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;

            }
            return best;
        } else {
            int best = MAX;

            // Recur for left and
            // right children
            for (Move move : b.legalMoves()) {
                b.doMove(move);
                int val = minimax(b.clone(), depth - 1,
                        true, alpha, beta);
                b.undoMove();
                best = Math.min(best, val);
                if (!player && best< beta){
                    bestMove = move;
                }
                beta = Math.min(beta, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }
//    private static int miniMaxSearch(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
//        if (depth == 0) {
//            return Evaluator.evaluate(board);
//        }
//        if (maximizingPlayer) {
//            int bestVal = Integer.MIN_VALUE;
//            for (Move move : board.legalMoves()) {
//                board.doMove(move);
//                int value = miniMaxSearch(board.clone(), depth - 1, alpha, beta, !maximizingPlayer);
//
//                bestVal = max(value, bestVal);
//
//
//                if (player && bestVal> alpha){
//                    bestMove = move;
//                }
//                alpha = max(alpha, bestVal);
//                if (beta <= alpha) {
//                    break;
//                }
//                board.undoMove();
//            }
//            return bestVal;
//        } else {
//            int bestVal = Integer.MAX_VALUE;
//            for (Move move : board.legalMoves()) {
//                board.doMove(move);
//                int value = miniMaxSearch(board.clone(), depth - 1, alpha, beta, !maximizingPlayer);
//
//
//                bestVal = min(bestVal, value);
//
//                if (!player && bestVal< beta){
//                    bestMove = move;
//                }
//                beta = min(beta, bestVal);
//                if (beta <= alpha) {
//                    break;
//                }
//                board.undoMove();
//            }
//            return bestVal;
//        }
//    }

}



