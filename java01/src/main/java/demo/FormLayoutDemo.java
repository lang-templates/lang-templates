package demo;

import javax.swing.*;
import jc.swing.JCFrame;

public class FormLayoutDemo extends JCFrame {
    private JButton button1;
    private JPanel panel1;

    public FormLayoutDemo() {
        this.setMainPanel(panel1, 640, 480, "FormLayoutDemo");
    }

    public static void main(String[] args) {
        var frame = new FormLayoutDemo();
        frame.setVisible(true);
    }
}
