package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.Middle;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Middle();
        MineSweeper mineSweeper = new MineSweeper(gameLevel);
        mineSweeper.run();
    }
}
