/*
 * Created by JFormDesigner on Sat Sep 14 05:01:39 JST 2024
 */

package jform;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author user
 */
public class JForm2 extends JFrame {
    public static void main(String[] args) {
        var form = new JForm2();
        form.setVisible(true);
    }
    public JForm2() {
        initComponents();
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        JOptionPane.showMessageDialog(this, "hello©");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Akio Tokaji
        button1 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("text");
        button1.addActionListener(e -> button1(e));
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(15, 15), button1.getPreferredSize()));

        contentPane.setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Akio Tokaji
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
