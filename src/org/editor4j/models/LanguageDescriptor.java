package org.editor4j.models;

public class LanguageDescriptor {
    public String iconName;
    public String syntaxStyle;
    public String friendlyName;

    public LanguageDescriptor(String syntaxStyle, String friendlyName, String iconName){
        this.syntaxStyle = syntaxStyle;
        this.friendlyName = friendlyName;
        this.iconName = iconName;
    }
}
