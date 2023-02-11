package org.kchandra423.engine;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Engine implements BoardEventListener {
    private final Board board;

    public Engine(Board board) {
        this.board = board;
    }


    private int minimax(Board b, int depth,
                       Boolean maximizingPlayer, int alpha,
                       int beta) {
        if (depth <= 0)
            return Evaluator.evaluate(b);

        if (maximizingPlayer) {
            int best = Integer.MIN_VALUE;
            for (Move move : b.legalMoves()) {
                Board copy = b.clone();
                copy.doMove(move);
                int val = minimax(copy, depth - 1,
                        false, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Move move : b.legalMoves()) {
                Board copy = b.clone();
                copy.doMove(move);
                int val = minimax(copy, depth - 1,
                        true, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, best);

                // Alpha Beta Pruning
                if (beta >= alpha)
                    break;
            }
            return best;
        }
    }


    public Move getBestMove(Board board, int depth) {
        Move bestMove = null;
        boolean player = board.getSideToMove() == Side.WHITE;
        int bestValue = player ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move move : board.legalMoves()) {
            Board copy = board.clone();
            copy.doMove(move);
            int value = minimax(copy, depth - 1, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (player) {
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }

        }
        System.out.println(bestValue);
        return bestMove;
    }


    @Override
    public void onEvent(BoardEvent boardEvent) {
        if (boardEvent.getType() == BoardEventType.ON_MOVE && board.getSideToMove() == Side.BLACK){
            Move bestMove = getBestMove(board, 6);
            if (bestMove == null) {
                System.out.println("No legal moves!");
                return;
            }
            board.doMove(bestMove);
        }
    }

}



