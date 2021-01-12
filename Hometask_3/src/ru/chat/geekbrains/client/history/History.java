package ru.chat.geekbrains.client.history;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.util.Objects;

public class History {
    private File file;

    public History(String nameOfHistoryFile) {
            this.file = new File(nameOfHistoryFile);
            createNewHistoryFile(this.file);
        }

    public boolean isNotEmpty() {
        return file.length() > 0;
    }

    private void createNewHistoryFile(File tempFile) {
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHistory(String history) {
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(file, true))
        ) {
            writer.write(history);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Sww",e);
        }

    }

    public String loadMsgFromHistory(int count) {
        int counter =0;
        StringBuffer sb = new StringBuffer();
        try(ReversedLinesFileReader rlfr =new ReversedLinesFileReader(file, null) ) {
            String s = rlfr.readLine();
            while (Objects.nonNull(s) && counter < count) {

                sb.insert(0, s).insert(0, "\n");
                counter++;
                s = rlfr.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Sww",e);
        }
        return sb.toString();
    }
}
