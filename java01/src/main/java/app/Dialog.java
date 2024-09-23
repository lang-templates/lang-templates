package app;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jc.swing.JCDialog;

public class Dialog extends JCDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel textField1Label;

    public String result;

    public Dialog() {
        //
        setMainPanel(contentPane, "Dialog Sample");
        // buttnOk, buttonCancel
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        // X をクリックしたとき、 onCancel() を呼ぶ
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        onCancel();
                    }
                });
        // ESCAPE で onCancel() を呼ぶ
        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        // textField1
        textField1.grabFocus();
        textField1Label.setText("");
        this.buttonOK.setEnabled(false);
        textField1
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                on_textField1_changed();
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                on_textField1_changed();
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                on_textField1_changed();
                            }
                        });
    }

    private void onOK() {
        this.result = textField1.getText();
        dispose();
    }

    private void onCancel() {
        JOptionPane.showMessageDialog(this, "キャンセルされました");
        this.result = null;
        dispose();
    }

    private void on_textField1_changed() {
        this.textField1Label.setText(textField1.getText());
        this.buttonOK.setEnabled(!this.textField1Label.getText().isEmpty());
    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        dialog.setVisible(true);
        System.exit(0);
    }
}
