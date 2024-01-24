package ru.comavp;

import ru.comavp.exceptions.DictionaryOutOfBoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DictionaryReader implements AutoCloseable {

    private BufferedReader fileReader;
    private Map<Integer, String> linesCache = new HashMap<>();

    private final String DICTIONARY_NAME = "dictionary.txt";

    public String getStringByNumber(int number) throws IOException {
        if (fileReader == null) initFileReader();

        if (linesCache.isEmpty() || !linesCache.containsKey(number)) {
            int lineCnt = linesCache.size();
            String currLine = "";
            while (lineCnt < number) {
                if ((currLine = fileReader.readLine()) == null) {
                    throw new DictionaryOutOfBoundException("Dictionary has only " + lineCnt + " lines");
                }
                lineCnt++;
                linesCache.put(lineCnt, currLine);
            }
            return currLine;
        } else {
            return linesCache.get(number);
        }
    }

    @Override
    public void close() throws Exception {
        if (fileReader != null) {
            fileReader.close();
        }
    }

    private void initFileReader() throws FileNotFoundException {
        fileReader = new BufferedReader(new FileReader(
                ClassLoader.getSystemClassLoader().getResource(DICTIONARY_NAME).getPath()
        ));
    }
}
