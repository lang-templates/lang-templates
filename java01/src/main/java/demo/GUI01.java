package demo;

import javax.swing.*;

public class GUI01 extends system.JFrame {
    private JPanel mainPanel;

    public static void main(String[] args) {
        var frame = new GUI01();
        frame.setVisible(true);
    }
    public GUI01() {
        this.setMainPanel(mainPanel, 480, 480, "JFrame");
    }
}
