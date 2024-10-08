package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShotStatus;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

public class LandMIneCellSignProvider implements CellSignProvidable {
    private static final String LAND_MINE_SIGN = "☼";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapShotStatus.LANDMINE);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return LAND_MINE_SIGN;
    }
}
