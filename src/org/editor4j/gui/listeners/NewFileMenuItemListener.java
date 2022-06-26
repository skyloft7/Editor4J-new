package org.editor4j.gui.listeners;

import org.editor4j.gui.ComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.ide.CodeEditorComponent;
import org.editor4j.managers.DirectoryManager;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NewFileMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser jFileChooser = new JFileChooser(DirectoryManager.defaultJFileChooserPath);

        jFileChooser.setDialogTitle("New");
        int returnValue = jFileChooser.showDialog(null, "Create File");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                boolean isNewFileCreated = jFileChooser.getSelectedFile().createNewFile();
                if(isNewFileCreated){

                    CodeEditorComponent codeEditors = (CodeEditorComponent) ComponentRegistry.components.get("codeEditorComponent");


                    File file = jFileChooser.getSelectedFile();

                    DirectoryManager.defaultJFileChooserPath = Objects.requireNonNullElse(file.getParentFile(), FileSystemView.getFileSystemView().getHomeDirectory());


                    Editor editor = new Editor();
                    editor.open(file);

                    codeEditors.addTab(editor);

                }
                else {
                    //WIP, more descriptive error messages
                    String fileCreationErrorMsg = "Failed to create " + jFileChooser.getSelectedFile() + " because another file with that same name already exists";
                    JOptionPane.showMessageDialog(null, fileCreationErrorMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}