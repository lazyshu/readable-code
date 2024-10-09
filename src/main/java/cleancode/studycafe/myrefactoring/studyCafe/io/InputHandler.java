package cleancode.studycafe.myrefactoring.studyCafe.io;

import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePass;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePassType;

import java.util.List;

public interface InputHandler {


  StudyCafePassType getPassTypeSelectingUserAction();

  StudyCafePass selectAPass(List<StudyCafePass> passes);

  boolean getLockerSelection();
  boolean selectForPaymentOrReturn();

}
