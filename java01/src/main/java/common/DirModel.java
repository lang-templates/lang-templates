package common;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DirModel extends DefaultTreeModel {
    public DirModel(String dir) {
        super(new DefaultMutableTreeNode(new File(dir)));
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
        var list = getPathList(false);
        return list.stream()
                .map(File::new)
                .collect(Collectors.toList());
    }

    public List<String> getPathList(boolean forwardSlash) {
        var root = (DefaultMutableTreeNode) this.getRoot();
        return Collections.list(root.depthFirstEnumeration()).stream()
                .filter(DefaultMutableTreeNode.class::isInstance)
                .map(DefaultMutableTreeNode.class::cast)
                .map(DefaultMutableTreeNode::getUserObject)
                .filter(x -> x != null) // exclude root
                .map(File.class::cast)
                .map(File::getPath)
                .map(x -> forwardSlash ? x.replaceAll("\\\\", "/") : x)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getPathListLevel1(boolean forwardSlash) {
        var root = (DefaultMutableTreeNode) this.getRoot();
        return Collections.list(root.breadthFirstEnumeration()).stream()
                .filter(DefaultMutableTreeNode.class::isInstance)
                .map(DefaultMutableTreeNode.class::cast)
                .takeWhile(node -> node.getLevel() <= 1)
                .map(DefaultMutableTreeNode::getUserObject)
                .filter(x -> x != null) // exclude root
                .map(File.class::cast)
                .map(File::getPath)
                .map(x -> forwardSlash ? x.replaceAll("\\\\", "/") : x)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> filterByRegex(String regex) {
        return StringListFilter.filterByRegex(getPathList(true), regex);
    }

    public static void main(String[] args) {
        var dirModel = new DirModel("D:\\.repo\\base14\\cmd");
        var list = dirModel.getPathListLevel1(true);
        list.stream()
                .forEach( x -> {
                    System.out.println(x);
        });
    }
}
