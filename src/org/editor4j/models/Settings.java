package org.editor4j.models;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class Settings implements Serializable {
    @Serial
    private static final long serialVersionUID = 2280736603027290482L;

    public Style style;
    public Font font;
    public boolean wordWrapEnabled;
    public int tabSize;

}
