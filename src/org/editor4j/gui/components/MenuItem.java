package org.editor4j.gui.components;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItem extends JMenuItem {
    public MenuItem(String text, KeyStroke keyStroke, ActionListener eventListener) {
        setText(text);
        setAccelerator(keyStroke);
        addActionListener(eventListener);
    }
}
