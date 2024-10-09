package cleancode.studycafe.myrefactoring.studyCafe.io;

import cleancode.studycafe.asis.exception.AppException;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePass;
import cleancode.studycafe.myrefactoring.studyCafe.model.StudyCafePassType;


import java.util.List;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler{

    private static final Scanner SCANNER = new Scanner(System.in);

    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = SCANNER.nextLine();

        if ("1".equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if ("2".equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if ("3".equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }
    public boolean selectForPaymentOrReturn(){
      String payOrReturn = SCANNER.nextLine();
      if ("1".equals(payOrReturn)) {
        System.out.println("카드를 넣어주세요." );
      }
      if ("2".equals(payOrReturn)) {
        System.out.println("지폐을 넣어주세요." );
      }
      if ("3".equals(payOrReturn)) {
        return true;
      }
      System.out.println();
      System.out.println("감사합니다.");
      return false;
    }

    public StudyCafePass selectAPass(List<StudyCafePass> passes) {
        String userInput = SCANNER.nextLine();
        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public boolean getLockerSelection() {
        String userInput = SCANNER.nextLine();
        return "1".equals(userInput);
    }


}
