package demo;

import javax.swing.*;
import jc.swing.JCFrame;

public class GUI01 extends JCFrame {
    private JPanel mainPanel;

    public static void main(String[] args) {
        var frame = new GUI01();
        frame.setVisible(true);
    }

    public GUI01() {
        this.setMainPanel(mainPanel, 480, 480, "JFrame");
    }
}
