/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netchatserverv5.listen;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import netchatserverv5.DBOperate.CheckDB;
import netchatserverv5.DBOperate.DBConnection;

/**
 *
 * @author yusij
 */
public class UserLogin implements Runnable {

    private static final int PORT = 33502;

    @Override
    public void run() {
        //获取数据库连接
        Connection conn = DBConnection.getDBConnection();
        try {
            ServerSocket ss = new ServerSocket(PORT);
            byte[] b = new byte[256];
            int bLength;
            String message;
            for (;;) {
                try (Socket s = ss.accept()) {
                    InputStream is = s.getInputStream();
                    bLength = is.read(b);
                    message = new String(b, 0, bLength);
                    String[] usernameAndPassword = message.split("\n");
                    boolean result = CheckDB.checkUsernameAndPassword(conn, usernameAndPassword[0], usernameAndPassword[1]);
                    OutputStream os = s.getOutputStream();
                    if (result) {
                        os.write("Y".getBytes());
                    } else {
                        os.write("N".getBytes());
                    }
                    s.close();
                }
            }
        } catch (IOException ex) {
            System.out.print("ServerSocket错误！");
        }
    }

}
