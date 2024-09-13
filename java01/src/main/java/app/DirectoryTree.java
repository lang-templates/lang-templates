// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package app;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public final class DirectoryTree extends JPanel {
  DirectoryTree() {
    super(new BorderLayout());
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    DefaultTreeModel treeModel = new DefaultTreeModel(root);

    Stream.of(fileSystemView.getRoots()).forEach(fileSystemRoot -> {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
      root.add(node);
      Stream.of(fileSystemView.getFiles(fileSystemRoot, true))
          .filter(File::isDirectory)
          .map(DefaultMutableTreeNode::new)
          .forEach(node::add);
    });

    /*
    File repo = new File("D:\\.repo");
    root.add(new DefaultMutableTreeNode(repo));
    */

    JTree tree = new JTree(treeModel) {
      @Override public void updateUI() {
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
    };
    tree.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    tree.setRootVisible(false);
    // java - File Browser GUI - Stack Overflow
    // https://stackoverflow.com/questions/6182110/file-browser-gui
    tree.addTreeSelectionListener(new FolderSelectionListener(fileSystemView));
    tree.expandRow(0);
    // tree.setToggleClickCount(1);

    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(new JScrollPane(tree));
    setPreferredSize(new Dimension(320, 240));
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(DirectoryTree::createAndShowGui);
  }

  public static void createAndShowGui() {
    /*
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException ignored) {
      Toolkit.getDefaultToolkit().beep();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
      return;
    }
    */
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    // frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new DirectoryTree());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  class FolderSelectionListener implements TreeSelectionListener {
    // private JFrame frame = null;
    private final FileSystemView fileSystemView;

    protected FolderSelectionListener(FileSystemView fileSystemView) {
      this.fileSystemView = fileSystemView;
    }

    @Override public void valueChanged(TreeSelectionEvent e) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
      File parent = (File) node.getUserObject();
      if (!node.isLeaf() || !parent.isDirectory()) {
        return;
      }
      JTree tree = (JTree) e.getSource();
      DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
      new BackgroundTask(fileSystemView, parent) {
        @Override protected void process(List<File> chunks) {
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

    @Override public String doInBackground() {
      Stream.of(fileSystemView.getFiles(parent, true))
              .filter(File::isDirectory)
              .forEach(this::publish);
      return "done";
    }
  }

}

