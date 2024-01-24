package ru.comavp;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WordChooserTest {

    @Test
    public void testGetNextWord() throws Exception {
        try (DictionaryReader dictionaryReader = new DictionaryReader()) {
            WordChooser wordChooser = new WordChooser(dictionaryReader);
            int minLength = 6;

            for (int i = 0; i < 100_000; i++) {
                String str = wordChooser.getNextWord();
                assertTrue("Длина строки '" + str + "' меньше " + minLength, str.length() >= minLength);
            }
        }
    }
}
