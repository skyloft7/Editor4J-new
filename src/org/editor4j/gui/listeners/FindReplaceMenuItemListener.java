package org.editor4j.gui.listeners;

import org.editor4j.gui.IdeComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.components.FindReplaceBar;
import org.editor4j.gui.ide.CodeEditorIdeComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindReplaceMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        CodeEditorIdeComponent codeEditors = (CodeEditorIdeComponent) IdeComponentRegistry.ideComponents.get("codeEditorComponent");
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
