package org.editor4j.managers;

import org.editor4j.gui.listeners.FileLoadedListener;
import org.editor4j.gui.listeners.FileSavedListener;

import javax.swing.*;
import java.io.*;

public class FileManager {


    public static void readFileOffEDT(File file, FileLoadedListener f){
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                System.out.println(Thread.currentThread().getName());
                return readFile(file);
            }

            @Override
            protected void done() {
                f.fileLoaded(this);
            }
        };

        swingWorker.execute();


    }


    public static String readFile(File file) {

        String total = "";

        String line;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while((line = bufferedReader.readLine()) != null){
                total += line + "\n";
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return total;
    }

    public static void saveFile(String filePath, String text) {
        System.out.println(Thread.currentThread().getName());

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write(text);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileOffEDT(String path, String text, FileSavedListener f) {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                saveFile(path, text);
                return null;
            }

            @Override
            protected void done() {
                f.fileSaved();
            }
        };
        swingWorker.execute();
    }
}
