package org.kchandra423.engine;

import com.github.bhlangonijr.chesslib.*;

public class Evaluator {
    private static final long PAWN_VALUE = 100L;
    private static final long BISHOP_VALUE = 320L;
    private static final long KNIGHT_VALUE = 315L;
    private static final long ROOK_VALUE = 500L;
    private static final long QUEEN_VALUE = 900L;
    private static final long MAX_VALUE = 40000L;
    private static final long MATE_VALUE = 39000L;
    private static final long[] PAWN_PST = new long[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            50, 50, 50, 50, 50, 50, 50, 50,
            10, 10, 20, 30, 30, 20, 10, 10,
            5, 5, 10, 25, 25, 10, 5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, -5, -10, 0, 0, -10, -5, 5,
            5, 10, 10, -20, -20, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0};
    private static final long[] KNIGHT_PST = new long[]{
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50
    };
    private static final long[] BISHOP_PST = new long[]{
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -20, -10, -10, -10, -10, -10, -10, -20
    };
    private static final long[] ROOK_PST = new long[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, 10, 10, 10, 10, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0
    };

    private static final long[] QUEEN_PST = new long[]{
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -5, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 5, 5, 5, 5, 5, 0, -10,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
    };

    private static final long[] KING_OPENING_PST = new long[]{
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            20, 20, 0, 0, 0, 0, 20, 20,
            20, 30, 10, 0, 0, 10, 30, 20
    };

    private static final long[] KING_END_PST = new long[]{
            -50, -40, -30, -20, -20, -30, -40, -50,
            -30, -20, -10, 0, 0, -10, -20, -30,
            -30, -10, 20, 30, 30, 20, -10, -30,
            -30, -10, 30, 40, 40, 30, -10, -30,
            -30, -10, 30, 40, 40, 30, -10, -30,
            -30, -10, 20, 30, 30, 20, -10, -30,
            -30, -30, 0, 0, 0, 0, -30, -30,
            -50, -30, -30, -30, -30, -30, -30, -50
    };


    public static int evaluate(Board board) {
        if (board.isInsufficientMaterial()) {
            return 0;
        }
        if (board.isMated()) {
            return board.getSideToMove() == Side.WHITE ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        int materialWhite = materialEval(board, Side.WHITE);
        int materialBlack = materialEval(board, Side.WHITE.flip());
        int material = materialWhite - materialBlack;
        int position = positionEval(board, Side.WHITE, materialWhite) - positionEval(board, Side.WHITE.flip(), materialBlack);
        return material + position;
    }

    private static int positionEval(Board board, Side sideToMove, long material) {
        boolean isEndGame = material < MAX_VALUE;
        int score = 0;
        long pieces = board.getBitboard(sideToMove) & (~board.getBitboard(Piece.make(sideToMove, PieceType.KING)));

        while (pieces != 0L) {
            int index = Bitboard.bitScanForward(pieces);
            pieces = Bitboard.extractLsb(pieces);
            Square sq = Square.squareAt(index);
            score += getPositionEval(board.getPiece(sq), sq);
        }

        if (isEndGame) {
            score += KING_END_PST[getIndex(sideToMove, board.getKingSquare(sideToMove))];
        } else {
            score += KING_OPENING_PST[getIndex(sideToMove, board.getKingSquare(sideToMove))];
        }

        return score;
    }


    public static long getPositionEval(Piece p, Square sq) {
        switch (p.getPieceType()) {
            case PAWN:
                return PAWN_PST[getIndex(p.getPieceSide(), sq)];
            case KNIGHT:
                return KNIGHT_PST[getIndex(p.getPieceSide(), sq)];
            case BISHOP:
                return BISHOP_PST[getIndex(p.getPieceSide(), sq)];
            case ROOK:
                return ROOK_PST[getIndex(p.getPieceSide(), sq)];
            case QUEEN:
                return QUEEN_PST[getIndex(p.getPieceSide(), sq)];
            default:
                return 0;
        }

    }


    public static int materialEval(Board board, Side side) {
        int score = 0;
        for (PieceType pt : PieceType.values()) {
            score += Long.bitCount(board.getBitboard(Piece.make(side, pt))) * getMaterialValue(pt);
        }
        return score;
    }


    private static int getIndex(Side side, Square sq) {
        return side == Side.BLACK ? sq.ordinal() : 63 - sq.ordinal();
    }


    private static long getMaterialValue(PieceType pieceType) {
        switch (pieceType) {
            case PAWN:
                return PAWN_VALUE;
            case BISHOP:
                return BISHOP_VALUE;
            case KNIGHT:
                return KNIGHT_VALUE;
            case ROOK:
                return ROOK_VALUE;
            case QUEEN:
                return QUEEN_VALUE;
            default:
                return 0;
        }
    }
}
