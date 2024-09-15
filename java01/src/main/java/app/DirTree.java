package app;

import javax.swing.*;

public class DirTree extends system.JFrame {
    private JButton button1;
    private JButton button2;
    private JPanel mainPanel;
    private JPanel treePanel;

    public DirTree() {
        this.setMainPanel(mainPanel, 640, 480, "DirTree");
        this.initTreePanel();
    }

    private void initTreePanel() {
        var tree = new FileSystemTree();
        //var tree = new FileSystemTree("D:\\.repo\\base14");
        this.treePanel.add(new JScrollPane(tree));
    }

    public static void main(String[] args) {
        var frame = new DirTree();
        frame.setVisible(true);
    }
}
