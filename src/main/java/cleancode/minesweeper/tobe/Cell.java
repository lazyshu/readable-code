package cleancode.minesweeper.tobe;

public class Cell {
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";

    private int nearByLandMineCount;
    private boolean isLandMine;
    private boolean isFlagged;
    private boolean isOpened;

    // cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    //cell 의 상태: 깃발 여무, 열였다/닫혔다, 사용자가 확인함

    private Cell(int nearByLandMineCount,boolean isLandMine,boolean isFlagged,boolean isOpened) {

        this.isLandMine = isLandMine;
        this.nearByLandMineCount = nearByLandMineCount;
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static Cell create() {
        return of(0, false,false,false);
    }
    public void turnOnLandMine() {
        isLandMine = true;
    }


    //정적 팩토리? method로 생성자 만들기
    public static Cell of(int nearByLandMineCount,boolean isLandMine,boolean isFlagged,boolean isOpened) {
        return new Cell(nearByLandMineCount,isLandMine,isFlagged,isOpened);
    }


    public void updateNearByLandMineCount(int count) {
        this.nearByLandMineCount = count;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean hasLandMineCount() {
        return this.nearByLandMineCount!=0;
    }

    public boolean isChecked() {
        return isFlagged||isOpened;
    }

    public boolean isLandMine() {
        return isLandMine;
    }


    public void open() {
        this.isOpened = true;
    }
    public void flag() {
        this.isFlagged = true;
    }


    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LAND_MINE_SIGN;
            }
            if (hasLandMineCount()) {
                return String.valueOf(nearByLandMineCount);
            }
            return EMPTY_SIGN;
        }
        if (isFlagged) {
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }


}
