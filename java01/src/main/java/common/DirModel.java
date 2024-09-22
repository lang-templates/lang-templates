package common;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class DirModel extends DefaultTreeModel {
    public DirModel(String dir) {
        super(new DefaultMutableTreeNode(new File(dir)));
        var root = (DefaultMutableTreeNode) this.getRoot();
        addDirectory(root, dir);
    }

    private static void addDirectory(DefaultMutableTreeNode parent, String path) {
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
                addDirectory(node, file.getPath() /*, isDummy*/);
            }
        }
    }

    public static void main(String[] args) {
        var dirModel = new DirModel("D:\\.repo\\base14\\cmd");
        var list = dirModel.getPathListLevel1(true);
        list.stream()
                .forEach(
                        x -> {
                            System.out.println(x);
                        });
    }

    public List<File> getFileList() {
        var list = getPathList(false);
        return list.stream().map(File::new).collect(Collectors.toList());
    }

    public List<String> getPathList(boolean forwardSlash) {
        return getPathList(forwardSlash, (path, level) -> true);
    }

    public List<String> getPathList(boolean forwardSlash, DirModel.PathFilter pathFilter) {
        var root = (DefaultMutableTreeNode) this.getRoot();
        return Collections.list(root.depthFirstEnumeration()).stream()
                .filter(DefaultMutableTreeNode.class::isInstance)
                .map(DefaultMutableTreeNode.class::cast)
                .filter(
                        x ->
                                pathFilter.filter(
                                        (forwardSlash
                                                ? ((File) x.getUserObject())
                                                        .getPath()
                                                        .replaceAll("\\\\", "/")
                                                : ((File) x.getUserObject()).getPath()),
                                        x.getLevel()))
                .map(DefaultMutableTreeNode::getUserObject)
                // .filter(x -> x != null) // exclude root
                .map(File.class::cast)
                .map(File::getPath)
                .map(x -> forwardSlash ? x.replaceAll("\\\\", "/") : x)
                // .filter(x -> x != null) // exclude root
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getPathListReverse(boolean forwardSlash) {
        return getPathListReverse(forwardSlash, (path, level) -> true);
    }

    public List<String> getPathListReverse(boolean forwardSlash, DirModel.PathFilter pathFilter) {
        var list = getPathList(forwardSlash, pathFilter);
        Collections.reverse(list);
        return list;
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

    public List<String> filterByRegex(String regex, boolean forwardSlash) {
        //        return filterByRegex(regex, forwardSlash, new PathFilter() {
        //            @Override
        //            public boolean filter(String path) {
        //                return true;
        //            }
        //        });
        return filterByRegex(regex, forwardSlash, (path, level) -> true);
    }

    public List<String> filterByRegex(String regex, boolean forwardSlash, PathFilter pathFilter) {
        return StringListFilter.filterByRegex(getPathList(forwardSlash, pathFilter), regex);
    }

    public static interface PathFilter {
        public boolean filter(String path, int level);
    }
}
