package org.editor4j.gui.components;

import javax.swing.*;

public class Tab {
    public JPanel jPanel;
    public int index;
    public String title;

    public Tab(String title, JPanel jPanel) {
        this.title = title;
        this.jPanel = jPanel;
    }
}
