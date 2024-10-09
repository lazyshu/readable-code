package cleancode.studycafe.myrefactoring.studyCafe.model;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }


  public static boolean isHourly(StudyCafePassType studyCafePassType) {
    return HOURLY.equals(studyCafePassType);
  }

  public static boolean isWeekly(StudyCafePassType studyCafePassType) {
    return WEEKLY.equals(studyCafePassType);
  }

  public static boolean isFixed(StudyCafePassType studyCafePassType) {
    return FIXED.equals(studyCafePassType);
  }
}
