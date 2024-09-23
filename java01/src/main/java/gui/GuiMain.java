package gui;

import javax.swing.*;

public class GuiMain extends system.JFrame {
    private JPanel mainPanel;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextField textField1;

    public GuiMain() {
        this.setMainPanel(this.mainPanel, "GuiMain");
        OKButton.addActionListener(e -> onOKButton());
        cancelButton.addActionListener(e -> onCancelButton());
    }

    private void onOKButton() {
        dispose();
    }

    private void onCancelButton() {
        dispose();
    }

    public static void main(String[] args) {
        var frame = new GuiMain();
        frame.setVisible(true);
    }
}
