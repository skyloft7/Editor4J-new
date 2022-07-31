package org.editor4j.gui.listeners;

import org.editor4j.ErrorLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InjectErrorMenuItemListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            throw new Exception("[!Injected Exception!]");
        } catch (Exception j){
            j.printStackTrace();
        }
        finally {
            JOptionPane.showMessageDialog(null, "Injected Exception ");
        }
    }
}
