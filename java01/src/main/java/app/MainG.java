package app;

import system.Sys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainG extends system.JFrame {
    private JPanel mainPanel;
    private JButton showArgsButton;
    private JLabel labelShowArgs;
    private JButton directoryTreeButton;
    private JButton repoTreeButton;
    private JButton ダイアログを表示Button;
    private JLabel dialogResultLabel;

    public static void main(String[] args) {
        var frame = new MainG();
        frame.setVisible(true);
    }
    public MainG() {
        setMainPanel(mainPanel, 400, 240, "Simple GUI APP");/**/
        showArgsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainG.this.labelShowArgs.setText("クリックされました: [" + Main.argsList + "]");
                MainG.this.update(MainG.this.getGraphics());
                Sys.println("button clicked クリック");
            }
        });
        directoryTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DirectoryTree.createAndShowGui();
            }
        });
        repoTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RepoTree.createAndShowGui();
            }
        });
        ダイアログを表示Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var dialog = new Dialog();
                dialog.setVisible(true);
                //System.out.println("dialog exited!");
                MainG.this.dialogResultLabel.setText("[" + dialog.result + "]");
                MainG.this.update(MainG.this.getGraphics());
                Sys.echo(dialog.result, "dialog.result");
            }
        });
    }
}
