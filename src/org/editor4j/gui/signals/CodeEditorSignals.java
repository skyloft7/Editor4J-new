package org.editor4j.gui.signals;

import org.editor4j.gui.components.Editor;

public interface CodeEditorSignals {
    void addTab(Editor tab);

    int getTabCount();

    Editor getEditorAt(int i);

    int getSelectedEditorIndex();
}
