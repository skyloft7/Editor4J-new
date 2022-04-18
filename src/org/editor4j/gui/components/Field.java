package org.editor4j.gui.components;

import javax.swing.*;

public class Field extends JPanel {
    public Field(String text, JComponent component){
        JLabel jLabel = new JLabel(text);
        jLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(jLabel);

        add(component);

    }
}
