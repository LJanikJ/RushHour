package rushhour;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

/**
* The game window.
*/
public class WindowUI {
    private TerminalScreen screen;

    public WindowUI() {
        super();

        try {
            screen = new TerminalScreen(new UnixTerminal());
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.clear();

            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char getInput() {
        KeyStroke keyStroke = null;
        char returnChar;
        while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return getKeyStroke(keyStroke);
    }

    public void clearScreen() {
        try {
            screen.clear();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    Prints a string to the screen starting at the indicated column and row.
    @param toDisplay the string to be printed
    @param column the column in which to start the display
    @param row the row in which to start the display
    **/
    public void putString(String toDisplay, int column, int row) {
        Terminal t = screen.getTerminal();

        try {
            t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    Returns the correct char for a keystroke.
    @param keyStroke
    @return character
    **/
    private char getKeyStroke(KeyStroke keyStroke) {
        if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            return 'a';
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            return 'd';
        }

        return keyStroke.getCharacter();
    }

    public static void main(String[] args) {
        System.out.println("Solving...");
        RushHourParser parser = new RushHourParser("./src/main/resources/RushHourEx2.json");
        RushHour game = new RushHour(parser);
        WindowUI gameUI = new WindowUI();

        int solutionLength = game.getPath().size();
        int index = 0;
        char userInput = 'h';
        gameUI.putString(game.getPath().get(index).displayState(), 0, 1);

        while (userInput != 'q') {
            userInput = gameUI.getInput();
            if (userInput == 'a' && index > 0) {
                index--;
                gameUI.putString(game.getPath().get(index).displayState(), 0, 1);
            } else if (userInput == 'd' && index < solutionLength - 1) {
                index++;
                gameUI.putString(game.getPath().get(index).displayState(), 0, 1);
            }
        }
    }
}
