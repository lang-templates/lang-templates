package app;

import common.FileSystemTree;

import javax.swing.*;

public class RepoTree extends system.JFrame  {
    private JPanel mainPanel;
    private JPanel treePanel;
    private JButton button1;
    private JButton button2;

    public RepoTree() {
        this.setMainPanel(mainPanel, 360, 480, "RepoTree");
        this.initTreePanel();
    }

    private void initTreePanel() {
        //var tree = new FileSystemTree("D:\\.repo\\base14");
        var tree = new FileSystemTree("D:\\.repo");
        this.treePanel.add(new JScrollPane(tree));
    }

    public static void main(String[] args) {
        var frame = new RepoTree();
        frame.setVisible(true);
    }
}
