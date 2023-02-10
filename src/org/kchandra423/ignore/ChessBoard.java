package org.kchandra423.ignore;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ChessBoard extends PApplet {
    private ActionListener moveListener;

    private Square squareSelected;
    private final HashMap<Piece, PImage> images;
    private Board board;

    private boolean myTurn;

    private boolean isClient;


    public ChessBoard(ActionListener a, boolean isClient) {
        board = new Board();
        this.isClient = isClient;
        myTurn = isClient;
        this.moveListener = a;
        images = new HashMap<>();
        squareSelected = Square.NONE;
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name = file.getName().substring(0, file.getName().length() - 4);
                String file_name = file.getName();
                images.put(Piece.fromValue(name), loadImage("resources/" + file_name));
            }
        }

    }

    @Override
    public void draw() {
        background(0);
        if (board.isMated()) {
            System.out.println("Checkmate!");
            System.out.println("Winner: " + board.getSideToMove().flip().toString());
            exit();
        }
        if (board.isStaleMate()) {
            System.out.println("Stalemate!");
            exit();
        }
        if (board.isDraw()) {
            System.out.println("Draw!");
            exit();
        }
        if (board.isInsufficientMaterial()) {
            System.out.println("Insufficient material!");
            exit();
        }
        for (int i = 0; i < 64; i++) {
            int row = i / 8;
            int column = i % 8;
            if ((row + column) % 2 == 0) {
                fill(255);
            } else {
                if (isClient) {
                    fill(128, 0, 128);
                } else {
                    fill(166, 94, 46);
                }
            }
            if (!squareSelected.equals(Square.NONE)) {
                int selected_column = squareSelected.getFile().getNotation().charAt(0) - 'A';
                int selected_row = squareSelected.getRank().getNotation().charAt(0) - '1';
                selected_row = 7 - selected_row;
                if (selected_row == row && selected_column == column) {
                    fill(0, 255, 0);
                }
            }
            int x = 100 * column, y = 100 * row;
            rect(x, y, 100, 100);
            Piece cur = board.getPiece(getSquare(row, column));
            if (cur.equals(Piece.NONE)) {
                continue;
            }
            image(images.get(cur), column * 100, row * 100, 100, 100);

        }
    }

    @Override
    public void mousePressed() {
        Square position = getPosition(mouseX, mouseY);
        if (squareSelected.equals(Square.NONE)) {
            squareSelected = position;
        } else if (squareSelected.equals(position)) {
            squareSelected = Square.NONE;
        } else {

            List<Move> legal_moves = board.legalMoves();
            for (Move move : legal_moves) {
                if (move.getFrom().equals(squareSelected) && move.getTo().equals(position)) {
                    if (moveListener != null && myTurn) {
                        board.doMove(move);
                        moveListener.actionPerformed(new ActionEvent(this, this.hashCode(), board.getFen()));
                        myTurn = false;
                    }
                    squareSelected = Square.NONE;
                    return;
                }
            }
            squareSelected = position;

        }
    }



    private Square getSquare(int row, int column) {
        row = 8 - row;
        char column_char = (char) (column + 'A');
        char row_char = (char) (row + '0');
        return Square.fromValue("" + column_char + row_char);
    }

    private Square getPosition(int mouseX, int mouseY) {
        int row = mouseY / 100;
        int column = mouseX / 100;
        return getSquare(row, column);
    }



    public void setBoard(Object fen){
        if (!myTurn && fen instanceof String){
            String fe = (String) fen;
            board.loadFromFen(fe);
            myTurn = true;
        }
    }
}










