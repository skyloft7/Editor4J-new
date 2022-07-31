package org.editor4j.gui.ide;

import org.editor4j.gui.components.ClosableTabbedPane;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.signals.CodeEditorSignals;

import java.awt.*;

public class CodeEditorComponent extends Component implements CodeEditorSignals {
    ClosableTabbedPane tabs = new ClosableTabbedPane();
    public CodeEditorComponent(){
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }

    @Override
    public void addTab(Editor tab) {
        tabs.addTab(tab);
    }

    @Override
    public int getTabCount() {
        return tabs.getTabCount();
    }

    @Override
    public Editor getEditorAt(int i) {
        return (Editor) tabs.getComponentAt(i);
    }

    @Override
    public int getSelectedEditorIndex() {
        return tabs.getSelectedIndex();
    }

    public boolean areAnyEditorsUnsaved() {
        for(int i = 0; i < tabs.getTabCount(); i++){
            Editor e = getEditorAt(i);
            if(!e.saved)
                return true;
        }

        return false;
    }

    public String getUnsavedEditorsAsString() {

        String unsavedEditors = "";

        for(int i = 0; i < tabs.getTabCount(); i++){
            Editor e = getEditorAt(i);
            if(!e.saved){
                unsavedEditors += e.file.getName() + " ";
            }
        }

        return unsavedEditors;
    }
}
