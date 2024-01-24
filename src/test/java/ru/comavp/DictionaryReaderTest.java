package ru.comavp;

import org.junit.Test;
import ru.comavp.exceptions.DictionaryOutOfBoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DictionaryReaderTest {

    @Test
    public void testGetStringByNumber() throws Exception {
        List<Integer> numbersList = Arrays.asList(1, 67_774, 324, 100, 5);
        Map<Integer, String> numberToExpectedResult = Map.of(
                1, "а-ля фуршет",
                67774, "ящурка",
                324, "автомотошкола",
                100, "абхазка",
                5, "абаз"
        );

        try (DictionaryReader dictionaryReader = new DictionaryReader()) {
            for (var number : numbersList) {
                assertEquals("По номеру " + number + " найдено неверное слово", numberToExpectedResult.get(number),
                        dictionaryReader.getStringByNumber(number));
            }
        }
    }

    @Test
    public void testFileHasNoMoreLines() throws Exception {
        try (DictionaryReader dictionaryReader = new DictionaryReader()) {
            assertThrows(DictionaryOutOfBoundException.class, () -> dictionaryReader.getStringByNumber(70_000));
        }
    }
}
