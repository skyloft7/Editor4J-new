package org.editor4j.gui.listeners;

import org.editor4j.gui.ComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.components.FindReplaceBar;
import org.editor4j.gui.ide.CodeEditorComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindReplaceMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        CodeEditorComponent codeEditors = (CodeEditorComponent) ComponentRegistry.components.get("codeEditorComponent");
        int index = codeEditors.getSelectedEditorIndex();

        if(index != -1) {

            Editor currentEditor = codeEditors.getEditorAt(index);

            if (!currentEditor.containsToolbar(FindReplaceBar.class)) {
                currentEditor.addToolbar(FindReplaceBar.class);
            }
            else {
                currentEditor.removeToolbar(FindReplaceBar.class);
            }
        }
    }
}
