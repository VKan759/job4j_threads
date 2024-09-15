package ru.job4j.io;

import java.io.*;

public class ParseFile {

    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        String output = "";
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) > 0) {
                output += (char) data;
            }
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        String output = "";
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        }
        return output;
    }
}