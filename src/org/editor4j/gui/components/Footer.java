package org.editor4j.gui.components;

import javax.swing.*;
import java.awt.*;

public class Footer extends JPanel {
    public JLabel cursorPos = new JLabel();
    public JLabel lang = new JLabel();
    public JLabel fileStatus = new JLabel();

    public Footer(){
        setLayout(new BorderLayout());


        add(cursorPos, BorderLayout.EAST);
        add(fileStatus, BorderLayout.CENTER);
        add(lang, BorderLayout.WEST);

    }
}
