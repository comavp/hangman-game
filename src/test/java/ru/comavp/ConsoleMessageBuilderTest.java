package ru.comavp;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConsoleMessageBuilderTest {

    ConsoleMessageBuilder consoleMessageBuilder = new ConsoleMessageBuilder();

    @Test
    public void testPrintWordState() {
        String result1 = consoleMessageBuilder.getWordState("абиссаль", new boolean[] {true, false, false, true, true, true, false, false});
        String result2 = consoleMessageBuilder.getWordState("агар-агар", new boolean[] {true, false, true, false, true, true, false, true, false});

        assertEquals("А__ССА__", result1);
        assertEquals("А_А_-А_А_", result2);
    }

    @Test
    public void testGetGallowsState() throws IOException {
        String expectedResult = "|---------------\n" +
                "|/       |\n" +
                "|       ( )\n" +
                "|      \\ | /\n" +
                "|       | |\n" +
                "|       | |\n" +
                "|       / \\\n" +
                "|\n" +
                "|\n" +
                "---";
        String actualResult = consoleMessageBuilder.getGallowsState(7);
        assertEquals(expectedResult, actualResult);
    }
}
