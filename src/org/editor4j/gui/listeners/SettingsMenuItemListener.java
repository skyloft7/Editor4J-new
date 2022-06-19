package org.editor4j.gui.listeners;

import org.editor4j.ErrorLogger;
import org.editor4j.gui.Application;
import org.editor4j.gui.dialogs.SettingsDialog;
import org.editor4j.managers.SettingsManager;
import org.editor4j.models.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SettingsMenuItemListener implements ActionListener {

    private final Application application;

    public SettingsMenuItemListener(Application e){
        application = e;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        SettingsDialog settingsDialog = new SettingsDialog();

        settingsDialog.apply.addActionListener(actionEvent1->{
            try {
                Settings chosenSettings = settingsDialog.getSettings();
                SettingsManager.currentSettings = chosenSettings;
                SettingsManager.saveSettingsToFile(chosenSettings);
                application.applySettings(chosenSettings);

            } catch (IOException e) {
                ErrorLogger.log(e);
            }
        });

        settingsDialog.setVisible(true);
    }
}