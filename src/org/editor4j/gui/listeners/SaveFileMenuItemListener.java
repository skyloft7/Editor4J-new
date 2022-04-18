package org.editor4j.gui.listeners;

import org.editor4j.gui.components.ClosableTabbedPane;
import org.editor4j.gui.components.CodeEditor;
import org.editor4j.gui.ComponentRegistry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveFileMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ClosableTabbedPane closableTabbedPane = (ClosableTabbedPane) ComponentRegistry.components.get("tabPane");

        CodeEditor codeEditor = (CodeEditor) closableTabbedPane.getComponentAt(closableTabbedPane.getSelectedIndex());


        codeEditor.save();
    }
}