package system;

import javax.swing.*;

public class JFrame extends javax.swing.JFrame {
    public JFrame()
    {
        super();
    }
    public void setMainPanel(JPanel panel, int width, int height, String title) {
        setContentPane(panel);
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(width, height);
        setLocationRelativeTo(null);
    }
}
