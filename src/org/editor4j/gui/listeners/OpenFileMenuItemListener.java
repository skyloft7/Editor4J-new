package org.editor4j.gui.listeners;

import org.editor4j.gui.IdeComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.ide.CodeEditorIdeComponent;
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


            CodeEditorIdeComponent codeEditors = (CodeEditorIdeComponent) IdeComponentRegistry.ideComponents.get("codeEditorComponent");

            File file = jFileChooser.getSelectedFile();

            if(file.exists()) {

                DirectoryManager.defaultJFileChooserPath = Objects.requireNonNullElse(file.getParentFile(), FileSystemView.getFileSystemView().getHomeDirectory());

                Editor editor = new Editor();
                editor.open(file);

                System.out.println(editor.languageDescriptor.iconName);
                codeEditors.addTab(editor);


            }
            else
                JOptionPane.showMessageDialog(null, "Sorry, but " + file.getAbsolutePath() + " doesn't exist!");

        }

    }
}