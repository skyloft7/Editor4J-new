package org.editor4j.gui.listeners;

import org.editor4j.gui.dialogs.AboutDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.setVisible(true);
    }
}
