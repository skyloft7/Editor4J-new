

package org.editor4j;

import org.editor4j.gui.Application;
import org.editor4j.gui.dialogs.FirstTimeLicenseDialog;
import org.editor4j.managers.SettingsManager;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {

            File activationFile = new File("appdata/activation.e4j");

            if(!activationFile.exists()){

                FirstTimeLicenseDialog firstTimeLicenseDialog = new FirstTimeLicenseDialog();
                firstTimeLicenseDialog.setVisible(true);

                firstTimeLicenseDialog.accept.addActionListener(e -> {
                    firstTimeLicenseDialog.dispose();
                    Utils.createNewFile(activationFile);
                    launchEditor4J();
                });

            }
            else
                launchEditor4J();
        });
    }

    private static void launchEditor4J(){
        loadSettings();
        new Application().createNewEditor();
    }

    private static void loadSettings() {
        File startupSettingsFile = new File(SettingsManager.settingsPath);
        if(startupSettingsFile.exists())
            SettingsManager.currentSettings = SettingsManager.loadSettingsFromFile(startupSettingsFile);
        else
            SettingsManager.currentSettings = SettingsManager.STARTUP_DEFAULT_SETTINGS;
    }
}

