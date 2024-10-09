package cleancode.studycafe.myrefactoring.KioskConfig;

import cleancode.studycafe.myrefactoring.studyCafe.io.InputHandler;
import cleancode.studycafe.myrefactoring.studyCafe.io.OutputHandler;

public class KioskIOConfig {
  final InputHandler inputHandler;
  final OutputHandler outputHandler;

    public KioskIOConfig(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

  public InputHandler getInputHandler() {
    return inputHandler;
  }

  public OutputHandler getOutputHandler() {
    return outputHandler;
  }
}
