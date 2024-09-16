package demo;

import org.eclipse.swt.widgets.TreeItem;
import system.Sys;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.Collections;
import java.util.stream.Stream;

public class RepoSearch {
    public static void main(String[] args) {
        String dir = "D:\\.repo\\base14";
        //final DefaultMutableTreeNode root = new DefaultMutableTreeNode(new File("C:\\"));
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        addDirectory(root, dir);
        DefaultTreeModel model = new DefaultTreeModel(root);
        //DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        // Java 9:
        Collections.list(root.depthFirstEnumeration()).stream()
                .filter(DefaultMutableTreeNode.class::isInstance)
                .map(DefaultMutableTreeNode.class::cast)
                //.dropWhile(DefaultMutableTreeNode::isRoot)
                //.dropWhile(DefaultMutableTreeNode::isLeaf)
                .map(DefaultMutableTreeNode::getUserObject)
                .filter(x -> x != null) // exclude root
                .map(File.class::cast)
                .map(File::getPath)
                .forEach(System.out::println);
    }

    private static void addDirectory(DefaultMutableTreeNode parent, String path/*, boolean isDummy*/) {

        //! ディレクトリ直下のファイル一覧を取得する.
        path += "\\";
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory() && !file.isHidden() && file.canRead()) {
                //! サブディレクトリを追加する.
                var node = new DefaultMutableTreeNode(file);
                parent.add(node);

                /*
                //! サブディレクトリの存在チェックの場合は、1件検出した時点で処理を終了する.
                if (isDummy) {
                    return;
                }
                */

                //! サブディレクトリが存在するかどうかを調べる.
                addDirectory(node, file.getPath()/*, isDummy*/);
            }
        }
    }
}
