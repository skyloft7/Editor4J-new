package org.editor4j.managers;

import org.editor4j.gui.IdeComponentRegistry;
import org.editor4j.gui.ide.IdeComponent;
import org.editor4j.models.IdeComponentState;

import javax.swing.*;
import java.io.*;
import java.util.Collection;

public class PersistenceManager {

    public static void saveAllOffEDT(Collection<IdeComponent> ideComponents) {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws IOException {

                for(IdeComponent c : ideComponents){
                    if(c.getState() != null) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(c.getClass().getName()));
                        c.saveState();
                        objectOutputStream.writeObject(c.getState());
                        objectOutputStream.close();
                    }
                }
                return null;
            }
        };
        swingWorker.execute();
    }

    public static void loadAllStatesOffEDT() {

        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                for(IdeComponent c : IdeComponentRegistry.ideComponents.values()){

                    File stateFile = new File(c.getClass().getName());

                    if(stateFile.exists()) {

                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(stateFile));
                        IdeComponentState ideComponentState = (IdeComponentState) objectInputStream.readObject();
                        objectInputStream.close();

                        c.loadState(ideComponentState);
                    }

                }
                return null;
            }
        };
        swingWorker.execute();

    }
}
