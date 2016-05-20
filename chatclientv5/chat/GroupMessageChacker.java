/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientv5.chat;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import static java.lang.Thread.sleep;

/**
 *
 * @author yusij
 */
public class GroupMessageChacker implements Runnable {
    
    GroupChatFrame father;
    String group;
    
    public GroupMessageChacker(GroupChatFrame father, String group) {
        this.father = father;
        this.group = group;
    }
    
    @Override
    public void run() {
        String msg = null;
        int id = -1;
        while (father.isShowing()) {
            String[] member = MainFrame.sander.write("CheckGroupOnlineMember\n" + group).split("\n");
            DefaultListModel dlm = new DefaultListModel();
            for (int i = 1; i < member.length; i++) {
                dlm.addElement(member[i]);
            }
            if (member.length < 2) {
                dlm.addElement("");
            }
            father.memberList.setModel(dlm);
            father.memberLabel.setText("群组成员：" + (member.length - 1));
            
            for (int i = 0; i < 10 && father.isShowing(); i++) {
                msg = MainFrame.sander.write("CheckGroupMessage\n" + group + "\n" + id + "\n");
                if (!msg.equalsIgnoreCase("none")) {
                    String[] temp = msg.split("\n");
                    id = Integer.valueOf(temp[0]);
                    if (temp.length > 1) {
                        msg = temp[1];
                        for (int j = 2; j < temp.length; j++) {
                            msg += "\n" + temp[j];
                        }
                        if (!msg.startsWith(MainFrame.user + "\t")) {
                            father.msgTextArea.append(msg + "\n-------------------------------------------------"
                                    + "-------------------------------\n");
                        }
                    }
                }
                try {
                    sleep(567);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FriendBackgroudMessageChacker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }//while
    }
}
