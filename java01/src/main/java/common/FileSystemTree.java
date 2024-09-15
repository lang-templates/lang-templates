package common;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class FileSystemTree extends JTree {
    private FileSystemView fileSystemView;

    public FileSystemView getFileSystemView() {
        return this.fileSystemView;
    }

    public FileSystemTree() {
        this(null);
    }

    public FileSystemTree(String dir) {
        /*FileSystemView*/
        fileSystemView = FileSystemView.getFileSystemView();
        final DefaultMutableTreeNode root = (dir != null) ?
                new DefaultMutableTreeNode(new File(dir)) :
                new DefaultMutableTreeNode();
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        if (dir == null) {
            Stream.of(fileSystemView.getRoots()).forEach(fileSystemRoot -> {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
                //root.add(node);
                Stream.of(fileSystemView.getFiles(fileSystemRoot, true))
                        .filter(File::isDirectory)
                        .map(DefaultMutableTreeNode::new)
                        .forEach(root::add);
            });
        } else {
            Stream.of(fileSystemView.getFiles(new File(dir), true))
                    .filter(File::isDirectory)
                    .map(DefaultMutableTreeNode::new)
                    .forEach(root::add);
        }
        this.setModel(treeModel);
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setRootVisible(false);
        this.addTreeSelectionListener(new FileSystemTree.FolderSelectionListener(fileSystemView));
        this.expandRow(0);
        this.addTreeWillExpandListener(new TreeWillExpandListener() {
            private final AtomicBoolean isAdjusting = new AtomicBoolean();

            @Override
            public void treeWillExpand(TreeExpansionEvent e) { // throws ExpandVetoException {
                // collapseAll(tree); // StackOverflowError when collapsing nodes below 2nd level
                if (isAdjusting.get()) {
                    return;
                }
                isAdjusting.set(true);
                collapseFirstHierarchy(FileSystemTree.this);
                FileSystemTree.this.setSelectionPath(e.getPath());
                isAdjusting.set(false);
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent e) { // throws ExpandVetoException {
                // throw new ExpandVetoException(e, "Tree collapse cancelled");
            }
        });
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

    private final AtomicBoolean isAdjusting = new AtomicBoolean();

    public static void collapseFirstHierarchy(JTree tree) {
        TreeModel model = tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        // // Java 9:
        // Collections.list(root.breadthFirstEnumeration()).stream()
        //   .filter(DefaultMutableTreeNode.class::isInstance)
        //   .map(DefaultMutableTreeNode.class::cast)
        //   .takeWhile(node -> node.getLevel() <= 1)
        //   .dropWhile(DefaultMutableTreeNode::isRoot)
        //   .dropWhile(DefaultMutableTreeNode::isLeaf)
        //   .map(DefaultMutableTreeNode::getPath)
        //   .map(TreePath::new)
        //   .forEach(tree::collapsePath);

        // Java 9: Enumeration<TreeNode> e = root.breadthFirstEnumeration();
        Enumeration<?> e = root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            boolean isOverFirstLevel = node.getLevel() > 1;
            if (isOverFirstLevel) { // Collapse only nodes in the first hierarchy
                return;
            } else if (node.isLeaf() || node.isRoot()) {
                continue;
            }
            collapseNode(tree, node);
        }
    }

    private static void collapseNode(JTree tree, DefaultMutableTreeNode node) {
        tree.collapsePath(new TreePath(node.getPath()));
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
