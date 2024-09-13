package demo;

import system.Sys;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenExplorer {
    public static void main(String[] args) throws IOException {
        openFolder("C:\\Windows");
    }
    public static void openFolder(String path) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        try {
            File dirToOpen = new File(path);
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException iae) {
            Sys.println("File Not Found");
        }
    }
}
