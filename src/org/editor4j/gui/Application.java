package org.editor4j.gui;

import org.editor4j.Utils;
import org.editor4j.gui.components.Editor;
import org.editor4j.gui.components.Menu;
import org.editor4j.gui.components.MenuItem;
import org.editor4j.gui.ide.CodeEditorComponent;
import org.editor4j.gui.listeners.*;
import org.editor4j.managers.SettingsManager;
import org.editor4j.models.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
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

    public CodeEditorComponent codeEditorComponent = new CodeEditorComponent();

    public JFrame jFrame = new JFrame("Editor4J " + version);

    public JMenuBar jMenuBar;

    public void createNewEditor() {


        createMenuItems();
        ComponentRegistry.components.put("menuBarComponent", jMenuBar);
        jFrame.setJMenuBar(jMenuBar);

        toolBars.setLayout(new BoxLayout(toolBars, BoxLayout.Y_AXIS));

        contentPane.setLayout(new BorderLayout());
        contentPane.add(toolBars, BorderLayout.NORTH);
        contentPane.add(codeEditorComponent, BorderLayout.CENTER);

        ComponentRegistry.components.put("codeEditorComponent", codeEditorComponent);

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
            icons.add(Utils.toolkit.getImage(iconFile.getPath()));
        }

        return icons;
    }

    public void createMenuItems(){
        System.setProperty("flatlaf.menuBarEmbedded", "true");
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

        jMenuBar.add(new Menu("Help",

                //NOTE this uses the same shortcut as save
                new MenuItem("About", null, new AboutMenuItemListener()),
                new MenuItem("Editor4J on GitHub", null, new VisitGitHubRepoListener()),
                new MenuItem("License", null, new ShowLicenseListener())
        ));
    }


    public void applySettings(Settings s) {
        Utils.setLookAndFeel(s.style.lookAndFeel);
        Utils.updateComponentTreeUI(jFrame);

        for(int i = 0; i < codeEditorComponent.getTabCount(); i++) {

            Editor editor = codeEditorComponent.getEditorAt(i);
            editor.applySettings();
        }
    }


}
