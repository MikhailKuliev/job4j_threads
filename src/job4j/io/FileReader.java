package ru.job4j.io;



import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public final class FileReader {
    private final File file;
    public FileReader(File file) {
        this.file = file;
    }
    public String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new BufferedInputStream(new FileInputStream(file));
             Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            int data;
            while ((data = reader.read()) !=-1) {

                char c = (char) data;
                if (filter.test(c)) {
                    output.append(c);
                }
            }
        }
        return output.toString();
    }
    public static final class FileWriter{
        private final File file;
        public FileWriter(File file) {
            this.file = file;

        }
        public void saveContent(String content) throws IOException {
            try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
                Writer w = new OutputStreamWriter(o, StandardCharsets.UTF_8 );
                w.write(content);
            }
        }
    }
}