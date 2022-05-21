package org.editor4j.gui.components;


import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;

import javax.swing.*;
import java.awt.*;


public class Toolbar extends JPanel {
    public boolean isVisible;
    public JButton close = new JButton(new FlatTabbedPaneCloseIcon());
    public JPanel content = new JPanel();
    public Toolbar(){
        setLayout(new BorderLayout());

        close.setContentAreaFilled(false);
        add(close, BorderLayout.EAST);
        add(content, BorderLayout.WEST);
    }
}
