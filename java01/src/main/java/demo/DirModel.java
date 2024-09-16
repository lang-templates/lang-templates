package demo;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class DirModel extends DefaultTreeModel {
    public DirModel(String dir) {
        super(new DefaultMutableTreeNode());
        var root = (DefaultMutableTreeNode) this.getRoot();
        addDirectory(root, dir);
    }
    private static void addDirectory(DefaultMutableTreeNode parent, String path/*, boolean isDummy*/) {
        path += "\\";
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory() && !file.isHidden() && file.canRead()) {
                var node = new DefaultMutableTreeNode(file);
                parent.add(node);
                addDirectory(node, file.getPath()/*, isDummy*/);
            }
        }
    }
    public List<File> getFileList() {
        var root = (DefaultMutableTreeNode) this.getRoot();
        return Collections.list(root.depthFirstEnumeration()).stream()
                .filter(DefaultMutableTreeNode.class::isInstance)
                .map(DefaultMutableTreeNode.class::cast)
                .map(DefaultMutableTreeNode::getUserObject)
                .filter(x -> x != null) // exclude root
                .map(File.class::cast)
                .toList();
    }
    public List<String> getPathList() {
        var root = (DefaultMutableTreeNode) this.getRoot();
        return Collections.list(root.depthFirstEnumeration()).stream()
                .filter(DefaultMutableTreeNode.class::isInstance)
                .map(DefaultMutableTreeNode.class::cast)
                .map(DefaultMutableTreeNode::getUserObject)
                .filter(x -> x != null) // exclude root
                .map(File.class::cast)
                .map(File::getPath)
                .toList();
    }
}
