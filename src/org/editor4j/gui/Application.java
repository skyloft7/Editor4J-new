package org.editor4j.gui;

import org.editor4j.ErrorLogger;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import static org.editor4j.Utils.osMenuMask;


/***
 * The base class for Editor4J. This class is responsible for hosting different 'Components'
 * (code editors, file explorer), and setting them up with ComponentRegistry so that event listeners
 * (also registered here) can access them and modify their state through signals
 */
public class Application {

    public static final String version = "2022.4";

    public JPanel contentPane = new JPanel();

    public CodeEditorComponent codeEditorComponent = new CodeEditorComponent();

    public JFrame jFrame = new JFrame("Editor4J " + version);

    public JMenuBar jMenuBar;

    public void createNewEditor() {

        ComponentRegistry.components.put("menuBarComponent", jMenuBar);
        ComponentRegistry.components.put("codeEditorComponent", codeEditorComponent);


        createMenuItems();
        jFrame.setJMenuBar(jMenuBar);


        contentPane.setLayout(new BorderLayout());
        contentPane.add(codeEditorComponent, BorderLayout.CENTER);


        jFrame.setContentPane(contentPane);

        applySettings(SettingsManager.currentSettings);



        jFrame.setIconImages(searchAllIcons());

        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setSize(1280, 960);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                CodeEditorComponent c = (CodeEditorComponent) ComponentRegistry.components.get("codeEditorComponent");

                if(c.areAnyEditorsUnsaved()){

                    String unsavedEditorNames = c.getUnsavedEditorsAsString();

                    if(showUnsavedWarningDialog(jFrame, unsavedEditorNames))
                        System.exit(0);
                }
                else
                    System.exit(0);

            }
        });

        jFrame.setVisible(true);
    }

    private boolean showUnsavedWarningDialog(JFrame frame, String unsavedEditorNames) {
        boolean userWantsToQuitAnyway;

        userWantsToQuitAnyway = JOptionPane.showOptionDialog(
                frame,
                "Files " + unsavedEditorNames + " are unsaved! Quit anyway?",
                "Unsaved Files",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                null,
                null
                ) == JOptionPane.YES_OPTION;


        return userWantsToQuitAnyway;
    }

    public ArrayList<Image> searchAllIcons(){
        ArrayList<Image> icons = new ArrayList<>();

        File[] iconFiles = new File("resources/icons").listFiles();

        try {

            for (File iconFile : iconFiles) {
                icons.add(Utils.toolkit.getImage(iconFile.getPath()));
            }
        } catch (NullPointerException e){
            ErrorLogger.log(e);
        }

        return icons;
    }

    public void createMenuItems(){
        jMenuBar = new JMenuBar();

        jMenuBar.add(new Menu("File",

                new MenuItem("New File", KeyStroke.getKeyStroke((char) KeyEvent.VK_N, osMenuMask), new NewFileMenuItemListener()),
                new MenuItem("Open File", KeyStroke.getKeyStroke((char) KeyEvent.VK_O, osMenuMask), new OpenFileMenuItemListener()),
                new MenuItem("Save File", KeyStroke.getKeyStroke((char) KeyEvent.VK_S, osMenuMask), new SaveFileMenuItemListener())
        ));
        jMenuBar.add(new Menu("Edit",

                new MenuItem("Find/Replace", KeyStroke.getKeyStroke((char) KeyEvent.VK_F, osMenuMask), new FindReplaceMenuItemListener()),
                new MenuItem("Copy Path", KeyStroke.getKeyStroke((char) KeyEvent.VK_W, osMenuMask), new CopyPathMenuItemListener())
        ));
        jMenuBar.add(new Menu("Editor",

                new MenuItem("Settings", KeyStroke.getKeyStroke((char) KeyEvent.VK_S, osMenuMask | InputEvent.ALT_DOWN_MASK), new SettingsMenuItemListener(this)),
                new MenuItem("Error Log", KeyStroke.getKeyStroke((char) KeyEvent.VK_M, osMenuMask | InputEvent.ALT_DOWN_MASK), new ErrorLogMenuItemListener()),
                new MenuItem("Inject Error", KeyStroke.getKeyStroke((char) KeyEvent.VK_P, osMenuMask | InputEvent.ALT_DOWN_MASK), new InjectErrorMenuItemListener())
        ));

        jMenuBar.add(new Menu("Help",

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

        if(s.embedMenuBar)
            System.setProperty("flatlaf.menuBarEmbedded", "true");
        else
            System.setProperty("flatlaf.menuBarEmbedded", "false");

        if(s.opengl)
            System.setProperty("sun.java2d.opengl", "true");
        else
            System.setProperty("sun.java2d.opengl", "false");

    }


}
