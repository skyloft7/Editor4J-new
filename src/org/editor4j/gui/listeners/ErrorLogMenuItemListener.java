package org.editor4j.gui.listeners;

import org.editor4j.gui.dialogs.ErrorLogDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorLogMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ErrorLogDialog errorLogDialog = new ErrorLogDialog();
        errorLogDialog.setVisible(true);
    }
}
