package org.editor4j.models;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;


/***
 * A Style can be applied to the whole editor. A style consists of a name (Dark, Light, Metal)
 * a Swing Look and Feel (Nimbus, Motif, System) and an RSTA theme
 */
public class Style implements Serializable {
    public String name;
    public String lookAndFeel;
    public String themePath;
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        Style style = (Style) obj;
        return name.equals(style.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
