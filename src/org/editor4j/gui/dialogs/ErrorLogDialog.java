package org.editor4j.gui.dialogs;

import org.editor4j.ErrorLogger;
import org.editor4j.Utils;
import org.editor4j.gui.components.DialogBase;
import org.editor4j.managers.FileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ErrorLogDialog extends DialogBase implements ActionListener {

    JButton saveLog = new JButton("Save as .e4log");

    public ErrorLogDialog() {
        super("Error Log", 640, 480);


        JPanel content = new JPanel();


        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(new JLabel("Errors in the Editor are logged here."));
        content.add(new JLabel("If there are any errors, it's better that you restart Editor4J as it could be in an invalid state."));
        content.add(new JLabel("Pro Tip: The newest Stacktrace is always at the bottom"));

        JTextArea exceptions = new JTextArea(ErrorLogger.allErrors());
        exceptions.setEditable(false);

        JScrollPane jScrollPane = new JScrollPane(exceptions);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        content.add(jScrollPane);

        SwingUtilities.getRootPane(this).setDefaultButton(saveLog);

        setDefaultButtonOnly(saveLog);

        saveLog.addActionListener(this);

        setContent(content);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Editor4J Error Log","e4log"));

        int returnValue = jFileChooser.showSaveDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION){


            File selectedFile = new File(jFileChooser.getSelectedFile().getPath() + ".e4log");

            Utils.createNewFile(selectedFile);


            FileManager.saveFileOffEDT(selectedFile.getPath(), ErrorLogger.allErrors(), () -> {});

        }
    }
}
