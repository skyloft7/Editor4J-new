package org.editor4j;

import java.io.*;
import java.util.Date;

public class ErrorLogger {

    private static OutputStream output = new OutputStream() {
        @Override
        public void write(int b) {
            stringBuilder.append((char) b);
        }public String toString() {
            return super.toString();
        }
    };
    public static PrintStream errorStream = new PrintStream(output);

    private static StringBuilder stringBuilder = new StringBuilder();

    public static String allErrors(){
        return stringBuilder.toString();
    }

    public static void log(Exception e){

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        String date = new Date().toGMTString();

        stringBuilder.append(date);
        stringBuilder.append("\n");
        stringBuilder.append(exceptionAsString);
        stringBuilder.append("\n\n");

    }
}
