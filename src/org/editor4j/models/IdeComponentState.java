package org.editor4j.models;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class IdeComponentState implements Serializable {
    public ArrayList<String> openFiles = new ArrayList<>();
}
