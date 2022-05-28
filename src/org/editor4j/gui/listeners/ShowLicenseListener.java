package org.editor4j.gui.listeners;

import org.editor4j.gui.dialogs.LicenseDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowLicenseListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        LicenseDialog licenseDialog = new LicenseDialog();
        licenseDialog.setVisible(true);
    }
}
