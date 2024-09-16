package demo;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;

public class SimpleFileExplorer extends JFrame {
    private JTree fileTree;

    public SimpleFileExplorer() {
        setTitle("Simple File Explorer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //File root = new File(System.getProperty("user.home"));
        File root = new File("D:\\.repo\\base14");
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        createTree(rootNode, root);

        fileTree = new JTree(rootNode);
        JScrollPane scrollPane = new JScrollPane(fileTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createTree(DefaultMutableTreeNode parent, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
                    parent.add(childNode);
                    if (child.isDirectory()) {
                        createTree(childNode, child);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleFileExplorer explorer = new SimpleFileExplorer();
            explorer.setVisible(true);
        });
    }
}