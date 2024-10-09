package cleancode.studycafe.myrefactoring.studyCafe;


import cleancode.studycafe.asis.exception.AppException;
import cleancode.studycafe.myrefactoring.KioskConfig.KioskIOConfig;
import cleancode.studycafe.myrefactoring.kioskMachine.KioskRunnable;
import cleancode.studycafe.myrefactoring.studyCafe.io.InputHandler;
import cleancode.studycafe.myrefactoring.studyCafe.io.OutputHandler;
import cleancode.studycafe.myrefactoring.studyCafe.io.StudyCafeCSVFileHandler;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafeLockerPass;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePass;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine implements KioskRunnable {

  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;


  public StudyCafePassMachine(KioskIOConfig kioskIOConfig) {
    this.inputHandler = kioskIOConfig.getInputHandler();
    this.outputHandler = kioskIOConfig.getOutputHandler();
  }

  private final StudyCafeCSVFileHandler studyCafeCSVFileHandler = new StudyCafeCSVFileHandler();
  List<StudyCafePass> studyCafePasses = studyCafeCSVFileHandler.readStudyCafePasses();


  public void run() {
    try {
      outputHandler.showStartingMessages();

      StudyCafePassType chosenPassType = inputHandler.getPassTypeSelectingUserAction();
      StudyCafePass selectedPass = selectPassFromListOf(chosenPassType);

      //validation!-numbers and character


      if (StudyCafePassType.isFixed(chosenPassType)) {

        StudyCafeLockerPass lockerPass = getStudyCafeLockerPassInfo(selectedPass);
        outputHandler.askLockerPass(lockerPass);
        boolean lockerSelection = inputHandler.getLockerSelection();

        if (lockerSelection) {
          outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } else {
          outputHandler.showPassOrderSummary(selectedPass);
        }

      } else {
        outputHandler.showPassOrderSummary(selectedPass);
      }

      outputHandler.askForPaymentOrReturn();
      if (chosenToReturnToMenu()) {
        run();
      }

    } catch (AppException e) {
      outputHandler.showSimpleMessage(e.getMessage());
    } catch (Exception e) {
      outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
    }
  }

  private StudyCafeLockerPass getStudyCafeLockerPassInfo(StudyCafePass selectedPass) {
    List<StudyCafeLockerPass> lockerPasses = studyCafeCSVFileHandler.readLockerPasses();

      return lockerPasses.stream()
      .filter(option ->
        option.getPassType() == selectedPass.getPassType()
          && option.getDuration() == selectedPass.getDuration())
      .findFirst()
      .orElse(null);
  }

  private boolean chosenToReturnToMenu() {
    return inputHandler.selectForPaymentOrReturn();
  }

  private StudyCafePass selectPassFromListOf(StudyCafePassType chosenPassType) {
    List<StudyCafePass> timePassList = studyCafePasses.stream()
      .filter(studyCafePass -> studyCafePass.getPassType() == chosenPassType)
      .toList();

    outputHandler.showPassListForSelection(timePassList);

    return inputHandler.selectAPass(timePassList);
  }


}
