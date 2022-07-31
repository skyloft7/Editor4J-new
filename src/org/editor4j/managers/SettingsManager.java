package org.editor4j.managers;

import org.editor4j.ErrorLogger;
import org.editor4j.Utils;
import org.editor4j.gui.styles.LightStyle;
import org.editor4j.models.Settings;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SettingsManager {
    public static Settings currentSettings;
    public static Settings STARTUP_DEFAULT_SETTINGS;
    public static String settingsPath = "appdata/settings.e4j";

    static {
        try {
            STARTUP_DEFAULT_SETTINGS = new Settings();
            STARTUP_DEFAULT_SETTINGS.style = new LightStyle();
            STARTUP_DEFAULT_SETTINGS.font = new Font("JetBrains Mono Regular", Font.PLAIN, 20);
            STARTUP_DEFAULT_SETTINGS.wordWrapEnabled = false;
            STARTUP_DEFAULT_SETTINGS.tabSize = 4;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Settings loadSettingsFromFile(File settingsFile) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(settingsFile));
            return (Settings) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Never happens because deserialize is only called when loadSettings() finds editor.settingsFile
        return null;
    }


    public static void saveSettingsToFile(Settings s) {
        File settingsFile = new File(settingsPath);
        Utils.serializeToPath(settingsFile, s);
    }


}
