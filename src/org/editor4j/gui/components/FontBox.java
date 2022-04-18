package org.editor4j.gui.components;

import javax.swing.*;
import java.awt.*;

public class FontBox extends JComboBox {
    public Font[] fonts;
    public int size;
    public int style;

    public FontBox(int size, int style, Font[] fonts){
        this.size = size;
        this.style = style;
        this.fonts = fonts;

        for(int i = 0; i < fonts.length; i++){
            super.addItem(fonts[i].getFontName());
        }
        super.setRenderer(new FontRenderer());
    }

    class FontRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList jList, Object value, int index, boolean selected, boolean hasFocus) {

            JLabel option = new JLabel((String) value);
            option.setFont(new Font(option.getText(), style, size));

            return option;
        }
    }
}
