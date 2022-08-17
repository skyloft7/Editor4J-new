package org.editor4j.gui.ide;

import org.editor4j.models.IdeComponentState;

import javax.swing.*;

public abstract class IdeComponent extends JPanel {
    protected IdeComponentState state;

    public abstract void loadState(IdeComponentState f);
    public abstract IdeComponentState onSubmitState();

    public IdeComponentState getState() {
        return state;
    }
}
