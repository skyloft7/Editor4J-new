package org.editor4j.gui.signals;

import org.editor4j.gui.components.Editor;
import org.editor4j.gui.components.Tab;

public interface CodeEditorSignals {
    void addTab(Tab tab);

    int getTabCount();

    Editor getEditorAt(int i);

    int getSelectedEditorIndex();
}
