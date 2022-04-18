package org.editor4j.gui.components;

import javax.swing.*;

public class Menu extends JMenu {
    public Menu(String text, MenuItem... menuItem) {
        setText(text);
        for(MenuItem e : menuItem)
            add(e);
    }
}
