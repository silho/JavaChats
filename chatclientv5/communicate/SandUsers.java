/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientv5.communicate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yusij
 */
public class SandUsers {
    private static final int PORT1=33502;
    private static final int PORT2=33501;
    private static final int PORT3=40335;

    public static synchronized boolean login(String username, char[] password) {
        try {
            Socket s = new Socket("127.0.0.1", PORT1);
            OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream();
            String message = username + "\n" + String.valueOf(password);
            byte[] b = new byte[8];

            os.write(message.getBytes());
            String result = new String(b, 0, is.read(b));

            return result.equals("Y");
        } catch (IOException ex) {
            Logger.getLogger(SandUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static synchronized boolean newuser(String username, char[] password) {
        try {
            Socket s = new Socket("127.0.0.1", PORT2);
            OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream();
            String message = username + "\n" + String.valueOf(password);
            byte[] b = new byte[8];

            os.write(message.getBytes());
            String result = new String(b, 0, is.read(b));

            return result.equals("Y");
        } catch (IOException ex) {
            Logger.getLogger(SandUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
