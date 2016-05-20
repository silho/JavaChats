/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netchatserverv5.listen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import netchatserverv5.DBOperate.DBConnection;
import netchatserverv5.protocol.Receiver;

/**
 *
 * @author yusij
 */
public class ListenMessage implements Runnable {

    private static final int PORT = 40335;

    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(PORT);
            while (true) {
                try {
                    Socket s = ss.accept();
                    Connection conn = DBConnection.getDBConnection();
                    new Thread(new Receiver(conn, s)).start();
                } catch (IOException ex) {
                    Logger.getLogger(ListenMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
