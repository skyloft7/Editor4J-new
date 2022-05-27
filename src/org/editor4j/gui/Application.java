package org.editor4j.gui;

import org.editor4j.Utils;
import org.editor4j.gui.components.*;
import org.editor4j.gui.components.Menu;
import org.editor4j.gui.components.MenuItem;
import org.editor4j.gui.listeners.*;
import org.editor4j.managers.SettingsManager;
import org.editor4j.models.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.editor4j.Utils.osMenuMask;


/***
 * The base class for Editor4J. This class is responsible for hosting different 'Components'
 * (code editors, file explorer), and setting them up with ComponentRegistry so that event listeners
 * (also registered here) can access them and modify their state through signals
 */
public class Application {

    public static final String version = "2022.2";

    public JPanel contentPane = new JPanel();
    public JPanel toolBars = new JPanel();

    public ClosableTabbedPane tabPane = new ClosableTabbedPane();

    public JFrame jFrame = new JFrame("Editor4J " + version);

    public JMenuBar jMenuBar;

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    public void createNewEditor() {
        //Workaround, codeEditorScrollPane isn't attached on startup, so it doesn't
        //get the new LaF
        Utils.setLookAndFeel(SettingsManager.currentSettings.style.lookAndFeel);

        createMenuItems();
        ComponentRegistry.components.put("menuBar", jMenuBar);
        jFrame.setJMenuBar(jMenuBar);

        toolBars.setLayout(new BoxLayout(toolBars, BoxLayout.Y_AXIS));

        contentPane.setLayout(new BorderLayout());
        contentPane.add(toolBars, BorderLayout.NORTH);
        contentPane.add(tabPane, BorderLayout.CENTER);

        ComponentRegistry.components.put("tabPane", tabPane);

        jFrame.setContentPane(contentPane);

        applySettings(SettingsManager.currentSettings);



        jFrame.setIconImages(searchAllIcons());

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1280, 960);
        jFrame.setVisible(true);
    }

    public ArrayList<Image> searchAllIcons(){
        ArrayList<Image> icons = new ArrayList<>();

        File[] iconFiles = new File("resources/icons").listFiles();



        for(File iconFile : iconFiles){
            icons.add(toolkit.getImage(iconFile.getPath()));
        }

        return icons;
    }

    public void createMenuItems(){
        System.setProperty("flatlaf.menuBarEmbedded", "false");
        jMenuBar = new JMenuBar();

        jMenuBar.add(new Menu("File",

                new MenuItem("New File", KeyStroke.getKeyStroke((char) KeyEvent.VK_N, osMenuMask), new NewFileMenuItemListener()),
                new MenuItem("Open File", KeyStroke.getKeyStroke((char) KeyEvent.VK_O, osMenuMask), new OpenFileMenuItemListener()),
                new MenuItem("Save File", KeyStroke.getKeyStroke((char) KeyEvent.VK_S, osMenuMask), new SaveFileMenuItemListener())
        ));
        jMenuBar.add(new Menu("Edit",

                new MenuItem("Find/Replace", KeyStroke.getKeyStroke((char) KeyEvent.VK_F, osMenuMask), new FindReplaceMenuItemListener())

        ));
        jMenuBar.add(new Menu("Editor",

                new MenuItem("Settings", KeyStroke.getKeyStroke((char) KeyEvent.VK_S, osMenuMask | InputEvent.ALT_DOWN_MASK), new SettingsMenuItemListener(this))
        ));
    }


    public void applySettings(Settings s) {
        Utils.setLookAndFeel(s.style.lookAndFeel);
        Utils.updateComponentTreeUI(jFrame);

        for(int i = 0; i < tabPane.getTabCount(); i++) {

            CodeEditor codeEditor = (CodeEditor) tabPane.getComponentAt(i);
            codeEditor.applySettings();
        }
    }


}
