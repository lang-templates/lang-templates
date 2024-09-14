package intel;

import system.Sys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JLabel lblFirstName;
    private JTextField textField1;
    private JButton clickMeButton;
    private JPanel mainPanel;

    public static void main(String[] args) {
        var frame = new Main();
        frame.setVisible(true);
    }
    public Main() {
        setContentPane(mainPanel);/**/
        setTitle("Simple GUI APP");/**/
        setDefaultCloseOperation(EXIT_ON_CLOSE);/**/
        setSize(300, 200);/**/
        setLocationRelativeTo(null);/**/
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**/
                var firstName = textField1.getText();
                Sys.echo(firstName);
                if (firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(Main.this, "Enter first name");
                    return;
                }
                JOptionPane.showMessageDialog(Main.this, "Hello " + firstName);
            }
        });
    }
}
