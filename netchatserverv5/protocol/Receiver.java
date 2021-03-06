/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netchatserverv5.protocol;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import netchatserverv5.DBOperate.CheckDB;

/**
 *
 * @author yusij
 */
public class Receiver implements Runnable {

    Connection conn;
    Socket s;

    public Receiver(Connection conn, Socket s) {
        this.conn = conn;
        this.s = s;
    }

    @Override
    public void run() {
        String messageReceived, username = null;
        try {
            InputStreamReader isr = new InputStreamReader(s.getInputStream(), "UTF-8");
            OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream(), "UTF-8");

            {
                char[] cbuf = new char[20];
                isr.read(cbuf);
                osw.write("ok");
                osw.flush();
                messageReceived = new String(cbuf).trim();
                String[] temp = messageReceived.split("\n");
                username = temp[1];
                System.out.println(username + "连接");

                CheckDB.changeUserState(conn, username, true);
            }
            //改变用户登录状态

            while (true) {
                char[] cbuf = new char[1024];
                isr.read(cbuf);
                messageReceived = null;
                messageReceived = new String(cbuf).trim();
                osw.write(analyser(messageReceived));
                osw.flush();
            }
            //while

        } catch (IOException ex) {
            System.out.println(username + "断开连接");
            CheckDB.changeUserState(conn, username, false);
        }

    }

    private String analyser(String messageReceived) {
        String result = null;
        String[] temp = messageReceived.split("\n");
        switch (temp[1]) {
            case "FreshFriendsList":
                result = "FriendsList                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             :\n" + CheckDB.getFriendsList(conn, temp[0]);
                break;
            case "FreshGroupsList":
                result = "GroupsList:\n" + CheckDB.getGroupList(conn, temp[0]);
                break;
            case "AddFriend":
                result = CheckDB.addFriend(conn, temp[0], temp[2]);
                System.out.println(messageReceived);
                break;
            case "AddGroup":
                result = CheckDB.addGroup(conn, temp[0], temp[2]);
                System.out.println(messageReceived);
                break;
            case "ChatWithFriend":
                String message = temp[3];
                for (int i = 4; i < temp.length; i++) {
                    message = message + "\n" + temp[i];
                }
                result = CheckDB.sandMessageToFriend(conn, temp[0], temp[2], message);
                break;
            case "CheckNewMessage":
                result = CheckDB.getMessageFromFriend(conn, temp[0], temp[2]);
                break;
            case "CheckGroupMessage":
                result = CheckDB.getMessageFromGroup(conn, temp[0], temp[2], temp[3]);
                break;
            case "CheckGroupOnlineMember":
                result = "member:\n" + CheckDB.getGroupOnlineMember(conn, temp[0], temp[2]);
                break;
            case "ChatWithGroup":
                String msg = temp[3];
                for (int i = 4; i < temp.length; i++) {
                    msg = msg + "\n" + temp[i];
                }
                result = CheckDB.sandMessageToGroup(conn, temp[0], temp[2], msg);
                break;
            default:
                result = "Error";
        }
        return result;
    }

}
