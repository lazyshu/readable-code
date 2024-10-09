package cleancode.studycafe.myrefactoring;


import cleancode.studycafe.myrefactoring.KioskConfig.KioskIOConfig;
import cleancode.studycafe.myrefactoring.studyCafe.StudyCafePassMachine;
import cleancode.studycafe.myrefactoring.studyCafe.io.ConsoleInputHandler;
import cleancode.studycafe.myrefactoring.studyCafe.io.ConsoleOutputHandler;
import cleancode.studycafe.myrefactoring.studyCafe.io.StudyCafeCSVFileHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
      KioskIOConfig kioskIOConfig = new KioskIOConfig(
        new ConsoleInputHandler(),
        new ConsoleOutputHandler()
        //csf file은 어캐 넣지 :(
      );

      StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(kioskIOConfig);
      studyCafePassMachine.run();
    }

}
