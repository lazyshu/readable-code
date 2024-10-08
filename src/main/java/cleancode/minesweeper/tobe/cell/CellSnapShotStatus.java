package cleancode.minesweeper.tobe.cell;

public enum CellSnapShotStatus {
    EMPTY("빈셀"),
    FLAG("깃발"),
    LANDMINE("지뢰"),
    NUMBER("숫자"),
    UNCHECKED("확인 전");

    private final String description;

    CellSnapShotStatus(String description) {
        this.description = description;
    }
}
