package org.editor4j.gui.dialogs;

import org.editor4j.ErrorLogger;
import org.editor4j.gui.components.DialogBase;
import org.editor4j.managers.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class LicenseDialog extends DialogBase {

    JPanel content = new JPanel();

    public LicenseDialog() {
        super("License", 640, 480);

        JTextArea licenseText = new JTextArea();
        licenseText.setEditable(false);

        FileManager.readFileOffEDT(new File("resources/LICENSE"), s -> {
            try {
                licenseText.setText((String) s.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        content.setLayout(new BorderLayout());

        content.add(licenseText, BorderLayout.CENTER);
        JLabel notice = new JLabel("By using Editor4J, you agree to the terms described above");

        content.add(notice, BorderLayout.SOUTH);

        setContent(content);

    }
}
