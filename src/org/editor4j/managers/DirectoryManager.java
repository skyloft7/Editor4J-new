package org.editor4j.managers;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class DirectoryManager {
    public static File defaultJFileChooserPath = FileSystemView.getFileSystemView().getHomeDirectory();
}
