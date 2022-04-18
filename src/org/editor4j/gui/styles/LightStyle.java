package org.editor4j.gui.styles;

import org.editor4j.models.Style;

import java.awt.*;
import java.io.IOException;

public class LightStyle extends Style {
    public LightStyle() throws IOException {
        this.name = "Light";
        this.lookAndFeel = "com.formdev.flatlaf.FlatLightLaf";
        this.themePath = "/org/fife/ui/rsyntaxtextarea/themes/vs.xml";

    }
}
