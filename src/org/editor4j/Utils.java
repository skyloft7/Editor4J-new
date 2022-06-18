





package org.editor4j;

import org.editor4j.models.SyntaxStyleFriendlyNamePair;
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

    public static HashMap<String, SyntaxStyleFriendlyNamePair> languages = new HashMap<String, SyntaxStyleFriendlyNamePair>();

    static {

        languages.put("java", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_JAVA, "Java"));
        languages.put("py", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_PYTHON, "Python"));
        languages.put("c", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_C, "C"));
        languages.put("h", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_CPLUSPLUS, "C/C++ Header"));
        languages.put("cpp", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_CPLUSPLUS, "C++"));
        languages.put("rb", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_RUBY, "Ruby"));
        languages.put("as", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_ACTIONSCRIPT, "ActionScript"));
        languages.put("css", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_CSS, "CSS"));
        languages.put("html", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_HTML, "HTML"));
        languages.put("js", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_JAVASCRIPT, "JavaScript"));
        languages.put("kt", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_KOTLIN, "Kotlin"));
        languages.put("md", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_MARKDOWN, "Markdown"));
        languages.put("sh", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_UNIX_SHELL, "Unix Shell Script"));
        languages.put("bat", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_WINDOWS_BATCH, "Windows Batch File"));
        languages.put("cs", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_CSHARP, "C#"));
        languages.put("clj", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_CLOJURE, "Clojure"));
        languages.put("scala", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_SCALA, "Scala"));
        languages.put("sc", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_SCALA, "Scala"));
        languages.put("ts", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_TYPESCRIPT, "TypeScript"));
        languages.put("dart", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_DART, "Dart"));
        languages.put("txt", new SyntaxStyleFriendlyNamePair(SYNTAX_STYLE_NONE, "Plain Text"));


    }

    public static void serializeToPath(File file, Object o){
        if(!file.exists()) {
            createNewFile(file);
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static final int osMenuMask = toolkit.getMenuShortcutKeyMaskEx();


    /**
     * It's recommended to use Utils.createNewFile() instead of File.createNewFile() where necessary
     * because this lets the file be created in a specified path, whereas File.createNewFile() cannot.
     *
     * @param f
     */
    public static void createNewFile(File f){
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.err.println("Not allowed to create file " + f.getName() + ". do you have permissions?");
            e.printStackTrace();
        }
    }

    public static void setLookAndFeel(String c){
        try {
            UIManager.setLookAndFeel(c);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "LaF " + c + " caused an exception: " + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getFileExtension(String f){
        String[] tokens = f.split("\\.");
        return tokens[tokens.length - 1];
    }


    /***
     * This is a workaround for SwingUtilities.updateComponentTreeUI() not working
     * on RSyntaxTextArea instances when changing RSTA's Theme. Use this method when
     * updating the Look and Feel and then changing RSTA's Theme.
     * @param rootComponent
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
                On it, it defaults to Monospaced Font when you apply a given font twice, e.g Ani
                I don't think this issue has anything to do with Fonts, but rather the Gutter
                object losing it's state when it's UI is updated*/
                if(!(child instanceof Gutter))
                    updateComponentTreeUI(child);
            }
        }

        rootComponent.invalidate();
        rootComponent.validate();
        rootComponent.repaint();
    }

}
