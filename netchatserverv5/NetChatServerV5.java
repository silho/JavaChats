/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netchatserverv5;

import netchatserverv5.listen.ListenMessage;
import netchatserverv5.listen.ListenUserRegister;
import netchatserverv5.listen.UserLogin;

/**
 *
 * @author yusij
 */
public class NetChatServerV5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("系统启动！");

        //启动线程监听用户注册
        new Thread(new ListenUserRegister()).start();

        //启动线程监听用户登录
        new Thread(new UserLogin()).start();

        //启动线程监听用户消息
        new Thread(new ListenMessage()).start();
    }

}
