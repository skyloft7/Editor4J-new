package org.editor4j.gui.components;

import org.editor4j.models.SyntaxStyleFriendlyNamePair;
import org.editor4j.Utils;
import org.editor4j.gui.signals.CodeEditorSignals;
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
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CodeEditor extends JPanel implements CodeEditorSignals {
    public RSyntaxTextArea rSyntaxTextArea;
    public RTextScrollPane rTextScrollPane;
    public Footer footer = new Footer();
    public File file;
    public boolean saved = true;

    public CodeEditor(){

        setLayout(new BorderLayout());


        rSyntaxTextArea = new RSyntaxTextArea();
        rTextScrollPane = new RTextScrollPane(rSyntaxTextArea);
        add(rTextScrollPane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        applySettings();


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
            footer.cursorPos.setText("Line " + row + " : Column " + column);
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
                footer.fileStatus.setText("Not Saved");
            }
        });

        rSyntaxTextArea.setCodeFoldingEnabled(true);

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


    @Override
    public void open(File file) {
        this.file = file;


        FileManager.readFileOffEDT(this.file, s -> SwingUtilities.invokeLater(() -> {
            try {
                setText((String) s.get());
                footer.fileStatus.setText("File Loaded");

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }));


        setText(FileManager.readFile(file));
        String fileExtension = Utils.getFileExtension(file.getName());


        SyntaxStyleFriendlyNamePair s = Utils.languages.getOrDefault(fileExtension, new SyntaxStyleFriendlyNamePair(SyntaxConstants.SYNTAX_STYLE_NONE, "Unknown"));


        setLanguage(s.syntaxStyle);

        String friendlyName = s.friendlyName;
        footer.lang.setText(friendlyName);


    }

    @Override
    public void save() {
        FileManager.saveFileOffEDT(file.getPath(), getText(), () -> {
            saved = true;
            footer.fileStatus.setText("File Saved");
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
}
