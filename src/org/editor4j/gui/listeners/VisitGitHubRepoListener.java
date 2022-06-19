package org.editor4j.gui.listeners;

import org.editor4j.ErrorLogger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class VisitGitHubRepoListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.github.com/skyloft7/Editor4J-new").toURI());
        } catch (IOException | URISyntaxException ex) {
            ErrorLogger.log(ex);
        }

    }
}
