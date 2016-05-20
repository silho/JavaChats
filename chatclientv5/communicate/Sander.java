/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientv5.communicate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yusij
 */
public class Sander implements Runnable {

    public static int PORT = 40335;
    public static String IP = "127.0.0.1";
    private final String username;
    private final ArrayBlockingQueue writer;
    private final ArrayBlockingQueue reader;

    public Sander(String username) {
        this.username = username;
        writer = new ArrayBlockingQueue(1);
        reader = new ArrayBlockingQueue(1);
    }

    public synchronized String write(String message) {
        try {
            writer.put(message);
            return reader.take().toString().trim();
        } catch (InterruptedException ex) {
            Logger.getLogger(Sander.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }

    @Override
    public void run() {
        Socket s = null;
        String message = null;
        OutputStreamWriter osw = null;
        InputStreamReader isr = null;
        try {
            s = new Socket(IP, PORT);
            osw = new OutputStreamWriter(s.getOutputStream(), "UTF-8");
            isr = new InputStreamReader(s.getInputStream(), "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(Sander.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            try {
                message = this.username + "\n" + (String) writer.take();
                osw.write(message);
                osw.flush();
                char[] cbuf = new char[4096];
                isr.read(cbuf);
                reader.put(new String(cbuf));
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(Sander.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
