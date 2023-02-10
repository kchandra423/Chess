package org.kchandra423;

import org.kchandra423.ignore.ChessBoard;
import processing.core.PApplet;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ChessBoard drawing = new ChessBoard(null, false);
        PApplet.runSketch(new String[]{""}, drawing);

    }
}

