package org.editor4j.gui.listeners;

import org.editor4j.gui.IdeComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.ide.CodeEditorIdeComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveFileMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        CodeEditorIdeComponent codeEditors = (CodeEditorIdeComponent) IdeComponentRegistry.ideComponents.get("codeEditorComponent");

        if(codeEditors.getTabCount() != -1) {

            Editor editor = codeEditors.getEditorAt(codeEditors.getSelectedEditorIndex());

            editor.save();
        }
    }
}