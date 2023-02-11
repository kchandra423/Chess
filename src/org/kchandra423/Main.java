package org.kchandra423;

import org.kchandra423.ignore.ChessBoard;
import processing.core.PApplet;

import java.io.IOException;

public class Main {
    public static void main(String[] args)  {
        ChessBoard drawing = new ChessBoard();
        PApplet.runSketch(new String[]{""}, drawing);

    }
}

