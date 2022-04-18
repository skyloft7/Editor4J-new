package org.editor4j.gui.listeners;

import org.editor4j.gui.components.CodeEditor;
import org.editor4j.gui.components.ClosableTabbedPane;
import org.editor4j.gui.components.Tab;
import org.editor4j.gui.ComponentRegistry;
import org.editor4j.managers.DirectoryManager;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class OpenFileMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser(DirectoryManager.defaultJFileChooserPath);

        int returnValue = jFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {


            ClosableTabbedPane closableTabbedPane = (ClosableTabbedPane) ComponentRegistry.components.get("tabPane");

            File file = jFileChooser.getSelectedFile();

            DirectoryManager.defaultJFileChooserPath = Objects.requireNonNullElse(file.getParentFile(), FileSystemView.getFileSystemView().getHomeDirectory());

            CodeEditor codeEditor = new CodeEditor();
            codeEditor.open(file);

            closableTabbedPane.addTab(new Tab(file.getName(), codeEditor));

        }

    }
}