package org.kchandra423;

import com.github.bhlangonijr.chesslib.Board;

import java.io.IOException;

public class Encoder {


    public static Object getDefaultEncoding(Board b) {
        return getEncodingFEN(b);
    }

    public static Board decodeDefault(Object o) {
        return decodeFEN(o);
    }

    private static Object getEncodingStrings(Board b) {
        String boardString = "";

//        Use Strings
        /*
        Hint!
        i is from 0 - 63
        Piece p = b.getPiece(Square.squareAt(i));
        Square s = Square.squareAt(i);
         */
        return boardString;
    }

    private static Board decodeStrings(Object o) {
        Board b = new Board();
        b.clear();
        /*
        Decode the object into a board
         */
        return b;
    }


    private static Object getEncodingBitboards(Board b) {
        long[] bitboards = new long[12];
//        Use Bitboards
        /*
        Find a way to store the bitboard for each piece
        Hint! Use the getBitboard method in the Board class
         */
        return null;
    }

    private static Board decodeBitboards(Object o) {
        Board b = new Board();
        b.clear();
        /*
        Decode the object into a board
        Hint! Use the bbToSquaryArray method in the Bitboard class
         */
        return b;
    }

    private static Object getEncodingFEN(Board b) {
        return b.getFen();
//        Use FEN
            /*
            There is a method for this but try to implement it by yourself
             */
    }

    private static Board decodeFEN(Object o) {
        Board b = new Board();
        b.loadFromFen((String) o);
        return b;
        /*
        Decode the object into a board
         */
    }


//    ############### ENCODE METHODS ###############
//        Method 1: Strings
//        StringBuilder boardString = new StringBuilder();
//        for (int i = 0; i < 64; i++) {
//            Piece p = b.getPiece(Square.squareAt(i));
//            boardString.append(p.getFenSymbol());
//        }
//        return boardString.toString();

//        Method 2: Bitboards
//        long[] bitboards = new long[12];
//        for (int i = 0; i < 12; i++) {
//            bitboards[i] = b.getBitboard(Piece.allPieces[i]);
//        }
//        return bitboards;

//        Method 3: Use FEN
//        return b.getFen();


//    ############### DECODE METHODS ###############
//        Method 1: Strings
//        b.clear();
//        String boardString = (String) o;
//        char[] lines = boardString.toCharArray();
//        for (int i = 0; i < 64; i++) {
//            Piece p = Piece.fromFenSymbol(String.valueOf(lines[i]));
//            Square s = Square.squareAt(i);
//            if (!p.equals(Piece.NONE) && !s.equals(Square.NONE)) {
//                b.setPiece(p, s);
//            }
//
//        }
//        return b;

//        Method 2: Bitboards
//        b.clear();
//        long[] bitboards = (long[]) o;
//        for (int i = 0; i < 12; i++) {
//            Piece p = Piece.allPieces[i];
//            for (Square s :
//                    Bitboard.bbToSquareArray(bitboards[i])) {
//                b.setPiece(p, s);
//            }
//        }


//        Method 3: Use FEN
//        b.loadFromFen((String) o);


}
