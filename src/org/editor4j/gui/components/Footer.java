package org.editor4j.gui.components;

import javax.swing.*;
import java.awt.*;

public class Footer extends JPanel {
    public JLabel cursorPos = new JLabel();
    public JLabel lang = new JLabel();
    public JLabel fileStatus = new JLabel();
    public JPanel fileStatusAndCursorPos = new JPanel();

    public Footer(){
        setLayout(new BorderLayout());


        fileStatusAndCursorPos.add(cursorPos);

        fileStatusAndCursorPos.add(fileStatus);

        add(fileStatusAndCursorPos, BorderLayout.EAST);

        add(lang, BorderLayout.WEST);

    }
}
