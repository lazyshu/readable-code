package cleancode.minesweeper.tobe.cell;

public class LandMineCell implements Cell {
    private static final String LAND_MINE_SIGN = "☼";
    private final CellState cellState = CellState.initialize();


    @Override
    public CellSnapshot getSnapShot() {
        if (cellState.isOpened()) {
            return CellSnapshot.ofLandMine();
        }
        if (cellState.isFlagged()) {
            return CellSnapshot.ofFlag();
        }
        return CellSnapshot.ofUnchecked();
    }

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
}
