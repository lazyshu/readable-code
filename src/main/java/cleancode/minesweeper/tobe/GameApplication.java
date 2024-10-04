package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.Middle;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Middle();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        MineSweeper mineSweeper = new MineSweeper(gameLevel,inputHandler,outputHandler);
        mineSweeper.initialize();
        mineSweeper.run();
    }

    /**
     *DIP-Dependency Inversion Principle
     * 인터페이스를 사용해서 주입한다.
     *
     *Di- Dependency Injection
     *
     *
     *Ioc- Inversion of Control
     * 스프링에선 IOC Container가 di를 해주는 역활을 해준다. 프로그램이 제어권을 가지고 생성하고 관리한다. (개발자가 아닌 프레임워크가 관리한다)
     */


    
}
