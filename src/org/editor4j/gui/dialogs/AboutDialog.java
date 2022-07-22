package org.editor4j.gui.dialogs;

import org.editor4j.gui.components.DialogBase;

import javax.swing.*;
import java.awt.*;


public class AboutDialog extends DialogBase {
    JButton close = new JButton("Close");
    public String text = "Editor4J - Making coding accessible to anyone\n" +
            "Running on " + System.getProperty("java.vm.name") + "\n by " + System.getProperty("java.vendor");
    public AboutDialog() {
        super("About Editor4J", 350, 210);
        setModalityType(ModalityType.APPLICATION_MODAL);

        JPanel content = new JPanel();


        content.setLayout(new BorderLayout());

        JPanel iconPanel = new JPanel();
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon("resources/icons/i64x64.png"));
        iconPanel.add(iconLabel);

        JPanel infoPanel = new JPanel();



        JTextArea infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setText(text);

        infoPanel.add(infoText);






        content.add(iconPanel, BorderLayout.CENTER);
        content.add(infoPanel, BorderLayout.SOUTH);


        setContent(content);

        setDefaultButtonOnly(close);
        close.addActionListener(e -> dispose());
    }
}
