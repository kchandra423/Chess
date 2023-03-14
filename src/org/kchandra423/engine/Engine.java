package org.kchandra423.engine;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.kchandra423.engine.Evaluator.evaluate;

public class Engine implements BoardEventListener {
    private final Board board;

    public Engine(Board board) {
        this.board = board;
    }

    private int alphaBetaMin(Board b, int alpha, int beta, int depth) {
        if (depth <= 0) {
            return evaluate(b);
        }
        for (Move move : b.legalMoves()) {
            Board copy = b.clone();
            copy.doMove(move);
            int score = alphaBetaMax(copy, alpha, beta, depth - 1);
            if (score <= alpha)
                return alpha; // fail hard alpha-cutoff
            if (score <= beta)
                beta = score; // beta acts like min in MiniMax
        }
        return beta;
    }

    private int alphaBetaMax(Board b, int alpha, int beta, int depth) {
        if (depth <= 0) {
            return evaluate(b);
        }
        for (Move move : b.legalMoves()) {
            Board copy = b.clone();
            copy.doMove(move);
            int score = alphaBetaMin(copy, alpha, beta, depth - 1);
            if (score >= beta)
                return beta;   // fail hard beta-cutoff
            if (score >= alpha)
                alpha = score; // alpha acts like max in MiniMax
        }
        return alpha;
    }


    public Move getBestMove(Board board, int depth) {
        Move bestMove = null;
        boolean player = board.getSideToMove() == Side.WHITE;
        int bestValue = player ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move move : board.legalMoves()) {
            Board copy = board.clone();
            copy.doMove(move);
            int value = alphaBetaMax(copy, Integer.MIN_VALUE, Integer.MAX_VALUE, depth - 1);
            if (player) {
                if (value >= bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            } else {
                if (value <= bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }

        }
//        System.out.println(bestValue);
        return bestMove;
    }


    @Override
    public void onEvent(BoardEvent boardEvent) {
        if (boardEvent.getType() == BoardEventType.ON_MOVE && board.getSideToMove() == Side.BLACK) {
            Move bestMove = getBestMove(board, 4);
            if (bestMove == null) {
                System.out.println("No legal moves!");
                return;
            }
            board.doMove(bestMove);
        }
    }

}



