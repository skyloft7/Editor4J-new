package org.editor4j.gui.ide;

import org.editor4j.gui.components.ClosableTabbedPane;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.components.Tab;
import org.editor4j.gui.signals.CodeEditorSignals;

import java.awt.*;

public class CodeEditorComponent extends Component implements CodeEditorSignals {
    ClosableTabbedPane tabs = new ClosableTabbedPane();
    public CodeEditorComponent(){
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }

    @Override
    public void addTab(Tab tab) {
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
}
