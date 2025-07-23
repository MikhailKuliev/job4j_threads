package ru.job4j.io;



import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

   public ParseFile(File file) {
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

                output.toString();
            }
        }
        return output.toString();
    }
    public String GetContent() throws IOException {
       return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }
     public String getContentWithoutUnicode() throws IOException {

           return content(c ->c<0x80);



               }
       public final class WriterFile{
       private final File file;
       public WriterFile(File file) {
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
