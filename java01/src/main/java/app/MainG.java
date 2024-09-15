package app;

import system.Sys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainG extends system.JFrame {
    private JPanel mainPanel;
    private JButton showArgsButton;
    private JButton directoryTreeButton;
    private JButton repoTreeButton;
    private JButton ダイアログを表示Button;
    private JLabel dialogResultLabel;
    private JButton アイコン表示テストButton;
    private JLabel ここに引数を表示Label;

    public static void main(String[] args) {
        var frame = new MainG();
        frame.setVisible(true);
    }
    public MainG() {
        setMainPanel(mainPanel, 400, 240, "Simple GUI APP");/**/
        showArgsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainG.this.ここに引数を表示Label.setText("クリックされました: [" + Main.argsList + "]");
                MainG.this.update(MainG.this.getGraphics());
                Sys.println("showArgsButton clicked クリック");
            }
        });
        directoryTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //DirectoryTree.createAndShowGui();
                var frame = new DirTree();
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frame.setVisible(true);
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
        アイコン表示テストButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var frame = new demo.GUI01();
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
