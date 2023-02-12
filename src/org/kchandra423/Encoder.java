package org.kchandra423;

import com.github.bhlangonijr.chesslib.Board;
import org.kchandra423.ignore.TesterHelper;

import java.io.IOException;

public class Encoder {

    public static void main(String[] args) throws IOException {
        TesterHelper.checkBoards();
    }


    public static Object getDefaultEncoding(Board b) {
        return getEncodingStrings(b);
    }

    public static Board decodeDefault(Object o) {
        return decodeStrings(o);
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
        /*
        Decode the object into a board
        Hint! Use the bbToSquaryArray method in the Bitboard class
         */
        return b;
    }

    private static Object getEncodingFEN(Board b) {
//        Use FEN
            /*
            There is a method for this but try to implement it by yourself
             */
        return null;
    }

    private static Board decodeFEN(Object o) {
        /*
        Decode the object into a board
         */
        return null;
    }


}
