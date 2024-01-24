package ru.comavp;

import java.io.IOException;
import java.util.Random;

public class WordChooser {

    private DictionaryReader dictionaryReader;

    private final int DICTIONARY_SIZE = 67774;
    private final int MIN_WORD_LENGTH = 6;

    public WordChooser(DictionaryReader dictionaryReader) {
        this.dictionaryReader = dictionaryReader;
    }

    public String getNextWord() throws IOException {
        int randomWordNumber = new Random().nextInt(DICTIONARY_SIZE);
        String randomWord = dictionaryReader.getStringByNumber(randomWordNumber);
        while (getWordLength(randomWord) < MIN_WORD_LENGTH) {
            randomWordNumber = new Random().nextInt(DICTIONARY_SIZE);
            randomWord = dictionaryReader.getStringByNumber(randomWordNumber);
        }
        return randomWord;
    }

    private int getWordLength(String word) {
        return word.replace("-", "").length();
    }
}
