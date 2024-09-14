package system;

import javax.swing.*;

public class JDialog extends javax.swing.JDialog {
    public JDialog()
    {
        super();
    }
    public void setMainPanel(JPanel panel, int width, int height, String title) {
        setContentPane(panel);
        setModal(true);/**/
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(width, height);
        setLocationRelativeTo(null);
    }
}
