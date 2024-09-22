package app;

import common.FileSystemTree;
import java.io.File;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import system.Sys;

public class DirTree extends system.JFrame {
    private JButton button1;
    private JButton button2;
    private JPanel mainPanel;
    private JPanel treePanel;
    private JTextField textField1;

    public DirTree() {
        // this.setMainPanel(mainPanel, 360, 240, "DirTree");
        this.setMainPanel(mainPanel, "DirTree");
        this.initTreePanel();
    }

    private void initTreePanel() {
        var tree = new FileSystemTree();
        tree.addTreeSelectionListener(new FolderSelectionListener(tree.getFileSystemView()));
        this.treePanel.add(new JScrollPane(tree));
    }

    public static void main(String[] args) {
        var frame = new DirTree();
        frame.setVisible(true);
    }

    class FolderSelectionListener implements TreeSelectionListener {
        private final FileSystemView fileSystemView;

        protected FolderSelectionListener(FileSystemView fileSystemView) {
            this.fileSystemView = fileSystemView;
        }

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
            File name = (File) node.getUserObject();
            Sys.echo(name.getPath(), "parent.getPath()");
            String path = name.getPath();
            if (path.startsWith("::")) path = "";
            //            if (path.equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) path =
            // "::PC";
            //            if (path.equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) path =
            // "::ライブラリ";
            //            if (path.equals("::{E88865EA-0E1C-4E20-9AA6-EDCD0212C87C}")) path =
            // "::ギャラリー";
            //            if (path.equals("::{F02C1A0D-BE21-4350-88B0-7367FC96EF3C}")) path =
            // "::ネットワーク";
            DirTree.this.textField1.setText(path);
        }
    }
}
