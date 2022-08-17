package org.editor4j.gui.ide;

import org.editor4j.gui.components.ClosableTabbedPane;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.signals.CodeEditorSignals;
import org.editor4j.models.IdeComponentState;
import org.editor4j.models.OpenedFilesState;

import java.awt.*;
import java.io.File;

public class CodeEditorIdeComponent extends IdeComponent implements CodeEditorSignals {
    ClosableTabbedPane tabs = new ClosableTabbedPane();
    public CodeEditorIdeComponent(){
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);

        state = new OpenedFilesState();
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
            if(e.fileState == Editor.FileState.NOT_SAVED)
                return true;
        }

        return false;
    }

    public String getUnsavedEditorsAsString() {

        String unsavedEditors = "";


        for(int i = 0; i < tabs.getTabCount(); i++){
            Editor e = getEditorAt(i);


            System.out.println("Editor [" + e.file.getName() + "] Saved: " + e.fileState);

            if(e.fileState == Editor.FileState.NOT_SAVED) unsavedEditors += e.file.getName() + " ";
        }

        return unsavedEditors;
    }

    @Override
    public void loadState(IdeComponentState f) {
        state = f;

        OpenedFilesState openedFilesState = (OpenedFilesState) state;

        System.out.println(openedFilesState.openFiles.size());

        for (int index = 0; index < openedFilesState.openFiles.size(); index++) {
            String filePath = openedFilesState.openFiles.get(index);

            File file = new File(filePath);

            if(file.exists()){
                Editor editor = new Editor();
                editor.open(file);
                addTab(editor);

            }
        }
    }

    @Override
    public IdeComponentState onSubmitState() {

        state = new OpenedFilesState();

        for (int index = 0; index < getTabCount(); index++) {
            File file = getEditorAt(index).file;
            state.openFiles.add(file.getAbsolutePath());

        }
        return state;
    }
}
