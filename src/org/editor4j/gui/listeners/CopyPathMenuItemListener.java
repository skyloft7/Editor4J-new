package org.editor4j.gui.listeners;

import org.editor4j.gui.IdeComponentRegistry;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.ide.CodeEditorIdeComponent;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CopyPathMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        CodeEditorIdeComponent c = (CodeEditorIdeComponent) IdeComponentRegistry.ideComponents.get("codeEditorComponent");


        int selectedEditorIndex = c.getSelectedEditorIndex();

        if(selectedEditorIndex != -1) {

            Editor e = c.getEditorAt(selectedEditorIndex);

            Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .setContents(
                            new StringSelection(e.file.getAbsolutePath()),
                            null
                    );

        }
    }
}
