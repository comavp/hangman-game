package ru.comavp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConsoleMessageBuilder {

    private Map<Integer, String> gallowsStatesMap = new HashMap<>();

    private final String FILES_PATH = "gallows/state%d.txt";
    private final Integer STATES_NUMBER = 7;

    public String getGallowsState(int state) throws IOException {
        if (gallowsStatesMap.isEmpty()) initGallowsStatesMap();

        return gallowsStatesMap.get(state);
    }

    public String getWordState(String word, boolean[] opened) {
        if (word.length() != opened.length) return "";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (opened[i]) {
                builder.append(Character.toUpperCase(word.charAt(i)));
            } else {
                builder.append("*");
            }
        }
        return builder.toString();
    }

    public String getGameOverMessage(boolean result) {
        StringBuilder builder = new StringBuilder("Игра завершена! ");
        if (result) {
            builder.append("Вы выиграли!");
        } else {
            builder.append("Вы проиграли!");
        }
        return builder.toString();
    }

    private void initGallowsStatesMap() throws IOException {
        for (int i = 0; i < STATES_NUMBER; i++) {
            String path = FILES_PATH.formatted(i + 1);
            BufferedReader reader = new BufferedReader(new FileReader(
                    ClassLoader.getSystemClassLoader().getResource(path).getPath()
            ));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            builder.deleteCharAt(builder.lastIndexOf("\n"));
            gallowsStatesMap.put(i + 1, builder.toString());
        }
    }
}
