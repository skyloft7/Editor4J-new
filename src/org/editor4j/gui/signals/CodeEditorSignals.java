package org.editor4j.gui.signals;

import java.io.File;

public interface CodeEditorSignals {
    void open(File file);

    void save();
}
