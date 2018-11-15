/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GetterDialog;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Saud
 */
public class DialogTester {
    
    public static void main(String[] args) {
        /*JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        
        JComponent[] textField = GetterFrame.getTextField("Text");
        mainPanel.add(textField[0]);
        
        JComponent[] passField = GetterFrame.getHiddenTextField("Password");
        mainPanel.add(passField[0]);
        
        JComponent[] dirField = GetterFrame.getDirectoryChooserField("Directory", "Choose File", "");
        mainPanel.add(dirField[0]);
        
        GetterFrame gf = new GetterFrame(null, mainPanel, "Frame Title");
        gf.showAndComplete(500, 500);
        
        System.out.println(((JTextField) textField[1]).getText());
        System.out.println(((JTextField) passField[1]).getText());
        System.out.println(((JTextField) dirField[1]).getText());*/
        
        GetterFrame gf = new GetterFrame(null, "yolo");
        JTextField tf = gf.addTextField("Name");
        JTextField df = gf.addDirectoryChooserField("Save Dir", "Choose a/my file", "");
        gf.showAndComplete(500, 500);
        if(!gf.isInputComplete()) {
            System.out.println(tf.getText());
            System.out.println(df.getText());
        }
    }
    
}
