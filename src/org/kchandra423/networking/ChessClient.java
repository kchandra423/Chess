package org.kchandra423.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.kchandra423.ignore.ChessBoard;
import processing.core.PApplet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChessClient {


    public static void main(String[] args) throws IOException {
        Client client;

        ChessBoard cboard;
        client = new Client();
        client.start();
        client.connect(5000, "localhost", 54555, 54777);

        cboard = new ChessBoard(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendTCP(e.getActionCommand());
            }
        }, true);

        PApplet.runSketch(new String[]{""}, cboard);
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                cboard.setBoard(object);

            }
        });
    }
}
