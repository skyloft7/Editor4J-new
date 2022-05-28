package org.editor4j.gui.listeners;

import org.editor4j.gui.ComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.ide.CodeEditorComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveFileMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        CodeEditorComponent codeEditors = (CodeEditorComponent) ComponentRegistry.components.get("codeEditorComponent");

        if(codeEditors.getTabCount() != -1) {

            Editor editor = codeEditors.getEditorAt(codeEditors.getSelectedEditorIndex());

            editor.save();
        }
    }
}