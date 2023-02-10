import processing.core.PApplet;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
		ChessBoard drawing = new ChessBoard();
		PApplet.runSketch(new String[]{""}, drawing);

	}
}

