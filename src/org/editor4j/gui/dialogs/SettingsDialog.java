package org.editor4j.gui.dialogs;


import org.editor4j.gui.components.DialogBase;
import org.editor4j.gui.components.Field;
import org.editor4j.gui.components.FontBox;
import org.editor4j.gui.components.RenderedFont;
import org.editor4j.gui.styles.DarkPurple;
import org.editor4j.gui.styles.DarkStyle;
import org.editor4j.gui.styles.LightStyle;
import org.editor4j.gui.styles.MetalStyle;
import org.editor4j.models.Settings;
import org.editor4j.models.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static org.editor4j.managers.SettingsManager.currentSettings;

public class SettingsDialog extends DialogBase {

    public JButton apply = new JButton("Apply");

    JSpinner tabSizes = new JSpinner(new SpinnerNumberModel(4, 2, 10, 1));
    JCheckBox wordWrap = new JCheckBox();
    JCheckBox embedMenuBar = new JCheckBox();

    JComboBox<Style> styles = new JComboBox<>();

    FontBox fonts;
    JSpinner fontSizes = new JSpinner(new SpinnerNumberModel(20, 10, 50, 1));


    JPanel jPanel = new JPanel();


    public SettingsDialog(){
        //Simple change just to test Git
        super("All Settings", 600, 440);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));


        JTabbedPane jTabbedPane = new JTabbedPane();

        jTabbedPane.add("General", buildGeneralOptions());
        jTabbedPane.add("Appearance", buildStyleOptions());
        jTabbedPane.add("Fonts", buildFontOptions());

        jPanel.add(jTabbedPane);
        SwingUtilities.getRootPane(this).setDefaultButton(apply);
        setModalityType(ModalityType.APPLICATION_MODAL);

        super.setContent(jPanel);
        super.setDefaultButtonOnly(apply);


        apply.addActionListener(new ReloadSettingsDialogListener(this));

        setSettings(currentSettings);
    }


    private JPanel buildGeneralOptions() {
        JPanel jPanel = new JPanel();
        jPanel.add(new Field("Tab Size", tabSizes));
        jPanel.add(new Field("Enable Word Wrapping", wordWrap));
        jPanel.add(new Field("Embed Menu Bar (Windows 10+ only \n and requires restart)", embedMenuBar));


        return jPanel;
    }

    private JPanel buildStyleOptions() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        try {
            styles.addItem(new LightStyle());
            styles.addItem(new DarkStyle());
            styles.addItem(new MetalStyle());
            styles.addItem(new DarkPurple());

        } catch (IOException e){
            e.printStackTrace();
        }

        jPanel.add(new Field("Style", styles));

        return jPanel;
    }

    private JPanel buildFontOptions(){
        Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

        fonts = new FontBox(allFonts);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        jPanel.add(new Field("Font", fonts));
        jPanel.add(new Field("Size", fontSizes));
        return jPanel;
    }

    private void setSettings(Settings currentSettings) {
        styles.getModel().setSelectedItem(currentSettings.style);
        fonts.setFont(currentSettings.font);
        fontSizes.setValue(currentSettings.font.getSize());
        wordWrap.setSelected(currentSettings.wordWrapEnabled);
        tabSizes.setValue(currentSettings.tabSize);
        embedMenuBar.setSelected(currentSettings.embedMenuBar);
    }





    //Get all the values from the GUI controls into a Settings object
    public Settings getSettings() throws IOException {
        Settings settings = new Settings();
        settings.style = (Style) styles.getSelectedItem();
        int fontSize = (int) fontSizes.getValue();
        String fontName = ((RenderedFont) fonts.getSelectedItem()).fontName;

        settings.font = new Font(fontName, Font.PLAIN, fontSize);
        settings.wordWrapEnabled = wordWrap.isSelected();
        settings.tabSize = (int) tabSizes.getValue();
        settings.embedMenuBar = embedMenuBar.isSelected();
        return settings;
    }

    private class ReloadSettingsDialogListener implements ActionListener {
        private final SettingsDialog settingsDialog;

        public ReloadSettingsDialogListener(SettingsDialog settingsDialog) {
            this.settingsDialog = settingsDialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.updateComponentTreeUI(settingsDialog);
        }
    }
}
