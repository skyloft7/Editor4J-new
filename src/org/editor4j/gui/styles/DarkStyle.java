package org.editor4j.gui.styles;

import org.editor4j.models.Style;

import java.awt.*;
import java.io.IOException;

public class DarkStyle extends Style {
    public DarkStyle() throws IOException {
        this.name = "Dark";
        this.lookAndFeel = "com.formdev.flatlaf.FlatDarkLaf";
        this.themePath = "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml";
    }
}
