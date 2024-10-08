package cleancode.minesweeper.tobe.cell;

public interface Cell  {




    CellSnapshot getSnapShot();
    boolean isLandMine();
    boolean hasLandMineCount();


    boolean isChecked();
    void open();
    void flag();
    boolean isOpened();


}
