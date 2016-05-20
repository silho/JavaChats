/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netchatserverv5.DBOperate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yusij
 */
public class DBConnection {

    static final String DBURL = "jdbc:mysql://localhost:3306/javachatdb";
    static final String USER = "root";
    static final String PSW = "yusky";

    private DBConnection() {
    }

    public static Connection getDBConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBURL, USER, PSW);
        } catch (SQLException ex) {
            System.out.print("数据库错误！");
        }
        return conn;
    }
}
