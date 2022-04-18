package org.editor4j.gui.styles;

import org.editor4j.models.Style;

public class MetalStyle extends Style {
    public MetalStyle(){
        this.name = "Metal";
        this.lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
        this.themePath = "/org/fife/ui/rsyntaxtextarea/themes/vs.xml";
    }
}
