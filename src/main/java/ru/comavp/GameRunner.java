package ru.comavp;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class GameRunner {

    private boolean gameFinished = false;
    private Scanner scanner = new Scanner(System.in);
    private int LAST_GALLOWS_STATE = 7;

    public void run() {
        try (DictionaryReader dictionaryReader = new DictionaryReader()) {
            ConsoleMessageBuilder consoleMessageBuilder = new ConsoleMessageBuilder();
            WordChooser wordChooser = new WordChooser(dictionaryReader);

            while (!gameFinished) {
                startGame(wordChooser, consoleMessageBuilder);

                System.out.println("Если Вы хотите продолжить игру, введите 'y'. (В противном случае - любую клавишу");
                String str = scanner.next();
                if (!"y".equals(str)) {
                    gameFinished = true;
                }
            }

            scanner.close();
        } catch (Exception e) {
            scanner.close();
            e.printStackTrace();
        }
    }

    private void startGame(WordChooser wordChooser, ConsoleMessageBuilder consoleMessageBuilder) throws IOException {
        String chosenWord = wordChooser.getNextWord();
        boolean[] opened = new boolean[chosenWord.length()];
        openSpecialSymbol(chosenWord, opened);
        int gallowsState = 1;
        StringBuilder wrongLetters = new StringBuilder();

        System.out.println("Загаданное слово: " + consoleMessageBuilder.getWordState(chosenWord, opened));
        System.out.println(consoleMessageBuilder.getGallowsState(gallowsState));

        while (gallowsState < LAST_GALLOWS_STATE && !wordGuessed(opened)) {
            System.out.println("Введите букву: ");

            String letter = scanner.next();
            if (chosenWord.contains(letter.toLowerCase())) {
                openLetters(chosenWord, opened, letter.charAt(0));
            } else if (!wrongLetters.toString().toString().contains(letter)) {
                gallowsState++;
                wrongLetters.append(letter);
            }

            System.out.println("Загаданное слово: " + consoleMessageBuilder.getWordState(chosenWord, opened));
            if (wrongLetters.toString().length() > 0) {
                System.out.print("Буквы, которых нет в слове: ");
                IntStream.range(0, wrongLetters.length()).forEach(i -> {
                    StringBuilder res = new StringBuilder().append(wrongLetters.charAt(i));
                    if (i != wrongLetters.length() - 1) res.append(", ");
                    System.out.print(res);
                });
                System.out.println();
            }
            System.out.println(consoleMessageBuilder.getGallowsState(gallowsState));
        }

        System.out.println(consoleMessageBuilder.getGameOverMessage(gallowsState < LAST_GALLOWS_STATE || wordGuessed(opened)));
        System.out.println("Загаданное слово: " + chosenWord);
    }

    private void openSpecialSymbol(String word, boolean[] opened) {
        if (word.contains("-")) {
            opened[word.indexOf("-")] = true;
        }
    }

    private void openLetters(String word, boolean[] opened, Character letter) {
        for (int i = 0; i < word.length(); i++) {
            if (!opened[i] && Character.valueOf(word.charAt(i)).equals(Character.toLowerCase(letter))) {
                opened[i] = true;
            }
        }
    }

    private boolean wordGuessed(boolean[] opened) {
        return IntStream.range(0, opened.length).mapToObj(idx -> opened[idx]).allMatch(item -> item);
    }
}
