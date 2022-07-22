package org.editor4j.gui.dialogs;

import com.formdev.flatlaf.FlatLightLaf;
import org.editor4j.ErrorLogger;
import org.editor4j.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FirstTimeLicenseDialog extends LicenseDialog {
    public JButton accept = new JButton("I accept");

    public FirstTimeLicenseDialog(){
        super();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            ErrorLogger.log(e);
        }

        ButtonGroup buttonGroup = new ButtonGroup();

        JButton deny = new JButton("I do not accept");

        buttonGroup.add(accept);
        buttonGroup.add(deny);

        setButtons(deny, accept);



        deny.addActionListener(e -> System.exit(0));

        SwingUtilities.updateComponentTreeUI(this);

        Dimension screenSize = Utils.toolkit.getScreenSize();
        Dimension selfSize = getSize();

        Point centered = new Point((screenSize.width / 2) - (selfSize.width / 2), (screenSize.height / 2) - (selfSize.height / 2));

        setLocation(centered);
    }
}
