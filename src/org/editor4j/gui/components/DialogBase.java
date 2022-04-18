package org.editor4j.gui.components;

import javax.swing.*;
import java.awt.*;

public class DialogBase extends JDialog {
    JPanel base = new JPanel();

    public DialogBase(String title, int width, int height){
        super.setTitle(title);
        super.setSize(width, height);

        base.setLayout(new BorderLayout());
        super.setContentPane(base);

    }

    public void setContent(JPanel content){
        base.add(content, BorderLayout.CENTER);
    }
    public void setButtons(JButton defaultButton, JButton... extraButtons){
        ButtonBar buttonBar = new ButtonBar(defaultButton, extraButtons);

        base.add(buttonBar, BorderLayout.SOUTH);
    }
    public void setDefaultButtonOnly(JButton defaultButton){
        ButtonBar buttonBar = new ButtonBar(defaultButton);
        base.add(buttonBar, BorderLayout.SOUTH);
    }


    class ButtonBar extends JPanel {
        public ButtonBar(JButton defaultButton, JButton... extraButtons){
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            for (JButton button : extraButtons) {
                add(button);
            }
            add(defaultButton);
        }
        public ButtonBar(JButton defaultButton){
            setLayout(new FlowLayout(FlowLayout.RIGHT));

            add(defaultButton);
        }
    }
}
