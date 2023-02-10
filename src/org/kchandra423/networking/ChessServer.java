package org.kchandra423.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.kchandra423.ignore.ChessBoard;
import processing.core.PApplet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChessServer {

    public static void main(String[] args) throws IOException {
        Server server;

        ChessBoard cboard;
        server = new Server();
        server.start();
        server.bind(54555, 54777);

        cboard = new ChessBoard(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.sendToAllTCP(e.getActionCommand());
            }
        }, false);

        PApplet.runSketch(new String[]{""}, cboard);
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                cboard.setBoard(object);
            }
        });
    }
}
