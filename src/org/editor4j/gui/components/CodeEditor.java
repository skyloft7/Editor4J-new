package org.editor4j.gui.components;

import org.editor4j.models.SyntaxStyleFriendlyNamePair;
import org.editor4j.Utils;
import org.editor4j.managers.FileManager;
import org.editor4j.managers.SettingsManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CodeEditor extends JPanel {
    public RSyntaxTextArea rSyntaxTextArea;
    public RTextScrollPane rTextScrollPane;
    public File file;
    public boolean saved = true;
    public InfoBar infoBar = new InfoBar();
    public JPanel toolbarPanel = new JPanel();
    public ArrayList<Class> installedToolbarClasses = new ArrayList<>();
    public CodeEditor(){

        setLayout(new BorderLayout());

        toolbarPanel.setLayout(new BoxLayout(toolbarPanel, BoxLayout.Y_AXIS));



        rSyntaxTextArea = new RSyntaxTextArea();
        rTextScrollPane = new RTextScrollPane(rSyntaxTextArea);
        rSyntaxTextArea.addCaretListener(e -> {
            int row = rSyntaxTextArea.getCaretLineNumber() + 1;
            int column = 0;
            try {

                //NOTE: getLineStartOffset() gets the index (relative to line 1) of the \n on the specified line
                int lineStartOffset = rSyntaxTextArea.getLineStartOffset(row - 1);

                //subtract the index of the last line from the pos of the caret to get the index of the caret
                column = rSyntaxTextArea.getCaretPosition() - lineStartOffset;

            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            infoBar.cursorPos.setText("Line " + row + " : Column " + column);
        });

        rSyntaxTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saved = false;
                infoBar.fileStatus.setText("Not Saved");
            }
        });

        rSyntaxTextArea.setCodeFoldingEnabled(true);






        add(infoBar, BorderLayout.SOUTH);
        add(rTextScrollPane, BorderLayout.CENTER);
        add(toolbarPanel, BorderLayout.NORTH);

        applySettings();
    }

    public boolean containsToolbar(Class c){
        for(Class t : installedToolbarClasses){


            if(t.getName().equals(c.getName()))
                return true;

            System.out.println(t.getName());
        }

        return false;
    }

    public void setLanguage(String lang){
        rSyntaxTextArea.setSyntaxEditingStyle(lang);
    }

    public void setText(String t){
        rSyntaxTextArea.setText(t);
    }

    public String getText(){
        return rSyntaxTextArea.getText();
    }

    public void applyTheme(Theme t){
        t.apply(rSyntaxTextArea);
    }


    public void open(File file) {
        this.file = file;


        FileManager.readFileOffEDT(this.file, s -> SwingUtilities.invokeLater(() -> {
            try {
                setText((String) s.get());
                infoBar.fileStatus.setText("File Loaded");

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }));


        setText(FileManager.readFile(file));
        String fileExtension = Utils.getFileExtension(file.getName());


        SyntaxStyleFriendlyNamePair s = Utils.languages.getOrDefault(fileExtension, new SyntaxStyleFriendlyNamePair(SyntaxConstants.SYNTAX_STYLE_NONE, "Unknown"));


        setLanguage(s.syntaxStyle);

        String friendlyName = s.friendlyName;
        infoBar.lang.setText(friendlyName);


    }

    public void save() {
        FileManager.saveFileOffEDT(file.getPath(), getText(), () -> {
            saved = true;
            infoBar.fileStatus.setText("File Saved");
        });
    }


    public void applySettings() {
        try {
            Theme theme = Theme.load(SettingsManager.class.getResourceAsStream(SettingsManager.currentSettings.style.themePath), SettingsManager.currentSettings.font);
            applyTheme(theme);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rSyntaxTextArea.setTabSize(SettingsManager.currentSettings.tabSize);
        rSyntaxTextArea.setLineWrap(SettingsManager.currentSettings.wordWrapEnabled);
    }

    public void addToolbar(Class classOfToolbarToAdd) {

        if(!installedToolbarClasses.contains(classOfToolbarToAdd)) {
            try {
            Toolbar toolBar = (Toolbar) classOfToolbarToAdd.getDeclaredConstructor().newInstance();


            toolBar.close.addActionListener(e -> {
                        removeToolbar(classOfToolbarToAdd);
                    }
            );
            toolbarPanel.add(toolBar);
            toolbarPanel.revalidate();
            toolbarPanel.repaint();
            toolBar.isVisible = !toolBar.isVisible;
            installedToolbarClasses.add(classOfToolbarToAdd);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeToolbar(Class classOfToolbarToRemove){

        if(installedToolbarClasses.contains(classOfToolbarToRemove)){

            installedToolbarClasses.remove(classOfToolbarToRemove);

            for(Object t : toolbarPanel.getComponents()){


                if(t.getClass().getName().equals(classOfToolbarToRemove.getName())) {


                    Toolbar toolbar = (Toolbar) t;
                    for (ActionListener l : toolbar.close.getActionListeners())
                        toolbar.close.removeActionListener(l);


                    toolbarPanel.remove(toolbar);
                    toolbarPanel.revalidate();
                    toolbarPanel.repaint();
                    toolbar.isVisible = !toolbar.isVisible;

                }
            }


        }

    }

}
