package org.editor4j.gui.listeners;

import org.editor4j.gui.ComponentRegistry;
import org.editor4j.gui.components.ClosableTabbedPane;
import org.editor4j.gui.components.CodeEditor;
import org.editor4j.gui.components.FindReplaceBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindReplaceMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        ClosableTabbedPane closableTabbedPane = (ClosableTabbedPane) ComponentRegistry.components.get("tabPane");
        int index = closableTabbedPane.getSelectedIndex();

        if(index != -1) {

            CodeEditor currentEditor = (CodeEditor) closableTabbedPane.getComponentAt(index);

            if (!currentEditor.containsToolbar(FindReplaceBar.class)) {
                currentEditor.addToolbar(FindReplaceBar.class);
            }
            else {
                currentEditor.removeToolbar(FindReplaceBar.class);
            }
        }
    }
}
