/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientv5.chat;

import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Thread.sleep;

/**
 *
 * @author yusij
 */
public class FriendBackgroudMessageChacker implements Runnable {

    FriendChatFrame father;
    String name;

    public FriendBackgroudMessageChacker(FriendChatFrame father, String friend) {
        this.father = father;
        this.name = friend;
    }

    @Override
    public void run() {
        String msg = null;
        while (father.isShowing()) {
            msg = MainFrame.sander.write("CheckNewMessage\n" + name);
            if (!msg.equalsIgnoreCase("none")) {
                father.messageTextArea.append(msg + "\n-----------------------------------------"
                        + "---------------------------------------\n");
            }
            try {
                sleep(567);
            } catch (InterruptedException ex) {
                Logger.getLogger(FriendBackgroudMessageChacker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
