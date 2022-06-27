





package org.editor4j;

import org.editor4j.models.LanguageDescriptor;
import org.fife.ui.rtextarea.Gutter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.*;

public class Utils {

    public static HashMap<String, LanguageDescriptor> languages = new HashMap<>();

    static {

        languages.put("java", new LanguageDescriptor(SYNTAX_STYLE_JAVA, "Java", "java"));
        languages.put("py", new LanguageDescriptor(SYNTAX_STYLE_PYTHON, "Python", "python"));
        languages.put("c", new LanguageDescriptor(SYNTAX_STYLE_C, "C", "c"));
        languages.put("h", new LanguageDescriptor(SYNTAX_STYLE_CPLUSPLUS, "C/C++ Header", "c"));
        languages.put("cpp", new LanguageDescriptor(SYNTAX_STYLE_CPLUSPLUS, "C++", "cplusplus"));
        languages.put("rb", new LanguageDescriptor(SYNTAX_STYLE_RUBY, "Ruby", "ruby"));
        languages.put("as", new LanguageDescriptor(SYNTAX_STYLE_ACTIONSCRIPT, "ActionScript", null));
        languages.put("css", new LanguageDescriptor(SYNTAX_STYLE_CSS, "CSS", "css3"));
        languages.put("html", new LanguageDescriptor(SYNTAX_STYLE_HTML, "HTML", "html5"));
        languages.put("js", new LanguageDescriptor(SYNTAX_STYLE_JAVASCRIPT, "JavaScript", "javascript"));
        languages.put("kt", new LanguageDescriptor(SYNTAX_STYLE_KOTLIN, "Kotlin", "kotlin"));
        languages.put("md", new LanguageDescriptor(SYNTAX_STYLE_MARKDOWN, "Markdown", "markdown"));
        languages.put("sh", new LanguageDescriptor(SYNTAX_STYLE_UNIX_SHELL, "Unix Shell Script", "bash"));
        languages.put("bat", new LanguageDescriptor(SYNTAX_STYLE_WINDOWS_BATCH, "Windows Batch File", "windows8"));
        languages.put("cs", new LanguageDescriptor(SYNTAX_STYLE_CSHARP, "C#", "csharp"));
        languages.put("clj", new LanguageDescriptor(SYNTAX_STYLE_CLOJURE, "Clojure", null));
        languages.put("scala", new LanguageDescriptor(SYNTAX_STYLE_SCALA, "Scala", "scala"));
        languages.put("sc", new LanguageDescriptor(SYNTAX_STYLE_SCALA, "Scala", "scala"));
        languages.put("ts", new LanguageDescriptor(SYNTAX_STYLE_TYPESCRIPT, "TypeScript", null));
        languages.put("dart", new LanguageDescriptor(SYNTAX_STYLE_DART, "Dart", "dart"));
        languages.put("txt", new LanguageDescriptor(SYNTAX_STYLE_NONE, "Plain Text", null));
        languages.put("lua", new LanguageDescriptor(SYNTAX_STYLE_LUA, "Lua", "lua"));


    }

    public static void serializeToPath(File file, Object o){
        if(!file.exists()) {
            createNewFile(file);
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            ErrorLogger.log(e);
        }

    }

    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static final int osMenuMask = toolkit.getMenuShortcutKeyMaskEx();


    /**
     * It's recommended to use Utils.createNewFile() instead of File.createNewFile() where necessary
     * because this lets the file be created in a specified path, whereas File.createNewFile() cannot.
     *
     * @param f The File to create
     */
    public static void createNewFile(File f){
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.err.println("Not allowed to create file " + f.getName() + ". do you have permissions?");
            ErrorLogger.log(e);
        }
    }

    public static void setLookAndFeel(String c){
        try {
            UIManager.setLookAndFeel(c);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "LaF " + c + " caused an exception: " + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            ErrorLogger.log(e);
        }
    }

    public static String getFileExtension(String f){
        String[] tokens = f.split("\\.");
        return tokens[tokens.length - 1];
    }


    /***
     * This is a workaround for SwingUtilities.updateComponentTreeUI() not working
     * on RSyntaxTextArea instances when changing RSyntaxTextArea's Theme. Use this method when
     * updating the Look and Feel and then changing RSyntaxTextArea's Theme.
     * @param rootComponent The root component to update
     */
    public static void updateComponentTreeUI(Component rootComponent) {

        if (rootComponent instanceof JComponent) {
            JComponent comp = (JComponent) rootComponent;
            comp.updateUI();
            JPopupMenu popupMenu = comp.getComponentPopupMenu();
            if(popupMenu != null)
                updateComponentTreeUI(popupMenu);
        }

        Component[] children = null;

        if (rootComponent instanceof JMenu)
            children = ((JMenu)rootComponent).getMenuComponents();

        else if (rootComponent instanceof Container)
            children = ((Container)rootComponent).getComponents();

        if (children != null) {
            for (Component child : children) {
                /*Gutters act weird if you try to call SwingUtilities.updateComponentTreeUI()
                On it, it defaults to Monospaced Font when you apply a given font twice, e.g. Ani
                I don't think this issue has anything to do with Fonts, but rather the Gutter
                object losing its state when it's UI is updated*/
                if(!(child instanceof Gutter))
                    updateComponentTreeUI(child);
            }
        }

        rootComponent.invalidate();
        rootComponent.validate();
        rootComponent.repaint();
    }

}
