package common;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileSystemTree extends JTree {
    private FileSystemView fileSystemView;

    public FileSystemView getFileSystemView() {
        return this.fileSystemView;
    }

    public FileSystemTree() {
        this((String[]) null);
    }

    public FileSystemTree(String root) {
        this(new String[]{root});
    }

    public FileSystemTree(String[] roots) {
        /*FileSystemView*/
        fileSystemView = FileSystemView.getFileSystemView();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        if (roots == null) {
            Stream.of(fileSystemView.getRoots()).forEach(fileSystemRoot -> {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
                root.add(node);
                Stream.of(fileSystemView.getFiles(fileSystemRoot, true))
                        .filter(File::isDirectory)
                        .map(DefaultMutableTreeNode::new)
                        .forEach(node::add);
            });
        } else {
            List<String> list = new ArrayList<>(Arrays.asList(roots));
            list.stream().forEach((dir) -> {
                File repo = new File(dir);
                root.add(new DefaultMutableTreeNode(repo));
            });
        }
        this.setModel(treeModel);
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setRootVisible(false);
        this.addTreeSelectionListener(new FileSystemTree.FolderSelectionListener(fileSystemView));
        this.expandRow(0);
    }

    @Override
    public void updateUI() {
        setCellRenderer(null);
        super.updateUI();
        DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
        setCellRenderer((tree, value, selected, expanded, leaf, row, hasFocus) -> {
            Component c = r.getTreeCellRendererComponent(
                    tree, value, selected, expanded, leaf, row, hasFocus);
            if (selected) {
                c.setForeground(r.getTextSelectionColor());
                // c.setBackground(Color.BLUE); // r.getBackgroundSelectionColor());
            } else {
                c.setForeground(r.getTextNonSelectionColor());
                c.setBackground(r.getBackgroundNonSelectionColor());
            }
            if (value instanceof DefaultMutableTreeNode && c instanceof JLabel) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                JLabel l = (JLabel) c;
                l.setOpaque(!selected);
                Object o = node.getUserObject();
                if (o instanceof File) {
                    File file = (File) o;
                    l.setIcon(fileSystemView.getSystemIcon(file));
                    l.setText(fileSystemView.getSystemDisplayName(file));
                    l.setToolTipText(file.getPath());
                }
            }
            return c;
        });
    }

    class FolderSelectionListener implements TreeSelectionListener {
        // private JFrame frame = null;
        private final FileSystemView fileSystemView;

        protected FolderSelectionListener(FileSystemView fileSystemView) {
            this.fileSystemView = fileSystemView;
        }

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
            File parent = (File) node.getUserObject();
            if (!node.isLeaf() || !parent.isDirectory()) {
                return;
            }
            JTree tree = (JTree) e.getSource();
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            new FileSystemTree.BackgroundTask(fileSystemView, parent) {
                @Override
                protected void process(List<File> chunks) {
                    if (tree.isDisplayable() && !isCancelled()) {
                        chunks.stream().map(DefaultMutableTreeNode::new)
                                .forEach(child -> model.insertNodeInto(child, node, node.getChildCount()));
                        // model.reload(parent); // = model.nodeStructureChanged(parent);
                        // tree.expandPath(path);
                    } else {
                        cancel(true);
                    }
                }
            }.execute();
        }
    }

    class BackgroundTask extends SwingWorker<String, File> {
        private final FileSystemView fileSystemView;
        private final File parent;

        protected BackgroundTask(FileSystemView fileSystemView, File parent) {
            super();
            this.fileSystemView = fileSystemView;
            this.parent = parent;
        }

        @Override
        public String doInBackground() {
            Stream.of(fileSystemView.getFiles(parent, true))
                    .filter(File::isDirectory)
                    .forEach(this::publish);
            return "done";
        }
    }
}
