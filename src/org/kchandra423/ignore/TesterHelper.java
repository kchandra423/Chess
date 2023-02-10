package org.kchandra423.ignore;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import org.kchandra423.Encoder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TesterHelper {
    public static void checkBoards() throws IOException {
        File boardFile = new File("data/small.fen");
        Scanner reader = new Scanner(boardFile);
        int num = 0;
        long total_size = 0;
        while (reader.hasNextLine()) {
            Board b = new Board();
            b.loadFromFen(reader.nextLine());
            Object encoding = Encoder.getDefaultEncoding(b);
            total_size += checkMemorySize(encoding);
            num++;
            Board newBoard = Encoder.decodeDefault(encoding);
            if (!boardEquals(b, newBoard)) {
                throw new RuntimeException("Boards not equal");
            }
        }
        System.out.println("Average size: " + total_size / num);
    }

    private static boolean boardEquals(Board b1, Board b2) {
        for (Piece p : Piece.allPieces) {
            if (b1.getBitboard(p) != b2.getBitboard(p)) {
                return false;
            }
        }
        return true;
    }

    private static long checkMemorySize(Object o) {
        return ObjectSizeCalculator.getObjectSize(o);
    }
}
