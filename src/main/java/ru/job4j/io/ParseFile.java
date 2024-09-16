package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Integer> condition) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) > 0) {
                if (condition.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}