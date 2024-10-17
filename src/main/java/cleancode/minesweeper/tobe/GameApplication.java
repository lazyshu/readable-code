package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.minesweeper.MineSweeper;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.Advanced;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.Beginner;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.Middle;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.VeryBeginnner;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameConfig gameConfig = new GameConfig(
                new Middle(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        MineSweeper mineSweeper = new MineSweeper(gameConfig);
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

    /**
     * 상속보단 조합을 사용하자
     * 상속-시멘트 부은것처럼 부모자식의 결합도가 높다. 그래서 조합을 사용하자
     *
     * valueObject- 불변성, 동등성, 유효성
     * entity는 식별자가 있고 valueObject는 식별자가 없지만 hashcode&equal을 사용해서 모든 필드들이 식별자 역할을 한다.
     *
     * 일급컬랙션은 list, set, map 이런 타입들의 추상화 도구로 사용한다 new로 2차 가공해서 해서 일급 컬랙션을 변경해도 이전의 컬래션에겐 영향이 없다.
     *
     * enum-열거형 타입 상수의 직합- 상수에 대한 로직도 담을수있다. 여기에 설명도 넣을수 있다
     *
     * ocp 지키기 @.@
     */



}
