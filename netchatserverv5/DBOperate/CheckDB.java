/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netchatserverv5.DBOperate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yusij
 */
public class CheckDB {

    public static void changeUserState(Connection conn, String username, boolean b) {
        String sql = "select ChangeLogin('" + username + "','" + (int) (b ? 1 : 0) + "')";
        try {
            try (Statement st = conn.createStatement()) {
                ResultSet rst = st.executeQuery(sql);
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("修改不成功" + sql);
        }
    }//改变用户登录状态

    public static boolean checkAndInsertUsernameAndPassword(Connection conn, String username, String password) {
        String sql = "INSERT INTO javachatdb.JAVACHATUSER (USERNAME, PASSWORD) VALUES ('" + username + "','" + password + "')";
        try {
            try (Statement st = conn.createStatement()) {
                int rst = st.executeUpdate(sql);
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }//注册

    public static boolean checkUsernameAndPassword(Connection conn, String username, String password) {
        String sql = "SELECT * FROM javachatdb.JAVACHATUSER WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'AND ISLOGIN=false";
        try {
            try (Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }

            }
        } catch (SQLException ex) {
            return false;
        }
    }//登陆

    public static String getFriendsList(Connection conn, String user) {
        String sql = "SELECT FRIENDNAME FROM javachatdb.friends WHERE USERNAME='" + user + "'";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            String friendlist = "";
            while (rs.next()) {
                friendlist = friendlist + rs.getString("FRIENDNAME").trim() + "\n";
            }
            st.close();
            return friendlist;
        } catch (SQLException ex) {
            return "error";
        }
    }//获取好友列表

    public static String getGroupList(Connection conn, String user) {
        String sql = "SELECT GROUPNAME FROM javachatdb.groupsmembers WHERE MEMBERNAME='" + user + "'";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            String groupList = "";
            while (rs.next()) {
                groupList = groupList + rs.getString("GROUPNAME").trim() + "\n";
            }
            st.close();
            return groupList;
        } catch (SQLException ex) {
            return "error";
        }
    }//获取群列表

    public static String addFriend(Connection conn, String user, String friend) {
        String sql = "select AddFriend('" + user + "','" + friend + "')";
        try {
            try (Statement st = conn.createStatement()) {
                st.executeQuery(sql);
                st.close();
                return "ok";
            }
        } catch (SQLException ex) {
            return "error";
        }
    }//添加好友

    public static String addGroup(Connection conn, String user, String group) {
        String sql = "INSERT INTO javachatdb.GROUPS (GROUPNAME) VALUES ('" + group + "')";
        try {
            try (Statement st = conn.createStatement()) {
                int rst = st.executeUpdate(sql);
                st.close();
            }
        } catch (SQLException ex) {
        }
        String sql2 = "INSERT INTO javachatdb.GROUPSMEMBERS (GROUPNAME,MEMBERNAME) VALUES ('" + group + "','" + user + "')";
        try {
            try (Statement st = conn.createStatement()) {
                st.executeUpdate(sql2);
                st.close();
                return "ok";
            }
        } catch (SQLException ex) {
            return "error";
        }
    }//添加群组

    public static String sandMessageToFriend(Connection conn, String user, String friend, String message) {
        String sql = "INSERT INTO javachatdb.friendsmessage (sander,receiveer,message) VALUES ('" + user + "','"
                + friend + "','" + message + "')";
        try {
            try (Statement st = conn.createStatement()) {
                int rst = st.executeUpdate(sql);
                st.close();
                return "ok";
            }
        } catch (SQLException ex) {
            return "error";
        }
    }//发送私聊信息

    public static String getMessageFromFriend(Connection conn, String user, String friend) {
        String msg = "none";
        String sql = "SELECT * FROM javachatdb.friendsmessage WHERE SANDER='" + friend + "' AND "
                + "RECEIVEER='" + user + "' AND RECEIVED = false";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                msg = rs.getString("sander").trim() + "\t" + rs.getTimestamp("sandtime") + "\n"
                        + rs.getString("message").trim() + "\n";
                int r = st.executeUpdate("update javachatdb.FRIENDSMESSAGE set received = true WHERE id='"
                        + rs.getInt("id") + "'");
                break;
            }
        } catch (SQLException ex) {
            msg = "none";
        }
        return msg;
    }//获取私聊信息

    public static String getMessageFromGroup(Connection conn, String user, String group, String id) {
        String msg = "none";
        int idnum = Integer.valueOf(id);
        if (idnum < 0) {
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT count(*) FROM javachatdb.groupsmessage");
                while (rs.next()) {
                    idnum = rs.getInt(1);
                }
            } catch (SQLException ex) {
                return "none";
            }
            return "" + idnum;
        }

        String sql = "SELECT * FROM javachatdb.groupsmessage WHERE GROUPNAME='" + group + "' AND ID>" + idnum;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                idnum = rs.getInt("ID");
                msg = "" + rs.getInt("ID") + "\n" + rs.getString("sander").trim() + "\t" + rs.getTimestamp("sandtime")
                        + "\n" + rs.getString("message").trim() + "\n";
                break;
            }
        } catch (SQLException ex) {
            msg = "none";
        }
        return msg;
    }//获取群聊信息

    public static String getGroupOnlineMember(Connection conn, String user, String group) {
        String sql = "SELECT MEMBERNAME FROM  javachatdb.groupsmembers WHERE GROUPNAME='" + group + "'";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            String groupmemberlist = "";
            while (rs.next()) {
                groupmemberlist = groupmemberlist + rs.getString("MEMBERNAME").trim() + "\n";
            }
            st.close();
            return groupmemberlist;
        } catch (SQLException ex) {
            return "";
        }
    }//获取群在线成员

    public static String sandMessageToGroup(Connection conn, String user, String group, String msg) {
        String sql = "INSERT INTO javachatdb.groupsmessage (sander,groupname,message) VALUES ('" + user + "','"
                + group + "','" + msg + "')";
        try {
            try (Statement st = conn.createStatement()) {
                int rst = st.executeUpdate(sql);
                st.close();
                return "ok";
            }
        } catch (SQLException ex) {
            return "error";
        }
    }//发送群消息

}
