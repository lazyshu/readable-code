package cleancode.studycafe.myrefactoring.studyCafe.io;


import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafeLockerPass;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePass;

import java.util.List;

public interface OutputHandler {
  void showStartingMessages();

/*  void showWelcomeMessage();

  void showAnnouncement();*/

  void askPassTypeSelection();

  void showPassListForSelection(List<StudyCafePass> passes);

  void askLockerPass(StudyCafeLockerPass lockerPass);

  void showPassOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass);
  void showPassOrderSummary(StudyCafePass selectedPass);

  void showSimpleMessage(String message);


  void askForPaymentOrReturn();
}
