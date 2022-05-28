package org.editor4j.gui.listeners;

import org.editor4j.gui.ComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.components.Tab;
import org.editor4j.gui.ide.CodeEditorComponent;
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


            CodeEditorComponent codeEditors = (CodeEditorComponent) ComponentRegistry.components.get("codeEditorComponent");

            File file = jFileChooser.getSelectedFile();

            DirectoryManager.defaultJFileChooserPath = Objects.requireNonNullElse(file.getParentFile(), FileSystemView.getFileSystemView().getHomeDirectory());

            Editor editor = new Editor();
            editor.open(file);

            codeEditors.addTab(new Tab(file.getName(), editor));

        }

    }
}