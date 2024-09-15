package app;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class Dialog extends system.JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel textField1Label;

    public String result;

    private void on_textField1_changed()
    {
        this.textField1Label.setText(textField1.getText());
        this.buttonOK.setEnabled(!this.textField1Label.getText().isEmpty());
    }

    public Dialog() {
        //setMainPanel(contentPane, 400, 200, "Dialog Sample");/**/
        setMainPanel(contentPane, "Dialog Sample");/**/
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        textField1.grabFocus();/**/
        textField1Label.setText("");/**/
        this.buttonOK.setEnabled(false);/**/
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Dialog.this.on_textField1_changed();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                Dialog.this.on_textField1_changed();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                Dialog.this.on_textField1_changed();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // X をクリックしたとき、 onCancel() を呼ぶ
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // ESCAPE で onCancel() を呼ぶ
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // ここにコードを追加
        this.result = textField1.getText();
        dispose();
    }

    private void onCancel() {
        // 必要に応じてここにコードを追加
        this.result = null;
        dispose();
    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        //dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
