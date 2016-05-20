/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientv5.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author yusij
 */
public class UiUtil {

    public static void setCenter(JFrame jf) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dms = tk.getScreenSize();
        double screenWidth = dms.getWidth() - jf.getWidth();
        double screenHeight = dms.getHeight() - jf.getHeight();
        jf.setLocation((int) (screenWidth / 2), (int) (screenHeight / 2));
    }

    public static void setCenter(JDialog jf) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dms = tk.getScreenSize();
        double screenWidth = dms.getWidth() - jf.getWidth();
        double screenHeight = dms.getHeight() - jf.getHeight();
        jf.setLocation((int) (screenWidth / 2), (int) (screenHeight / 2));
    }

}
