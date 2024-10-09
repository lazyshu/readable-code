package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapshot {
    private final CellSnapShotStatus status;
    private final int nearByLandMineCount;

    private CellSnapshot(CellSnapShotStatus status, int nearByLandMineCount) {
        this.status = status;
        this.nearByLandMineCount = nearByLandMineCount;
    }

    public static CellSnapshot of(CellSnapShotStatus status, int nearByLandMineCount) {
        return new CellSnapshot(status, nearByLandMineCount);
    }

    public static CellSnapshot ofEmpty() {
        return of(CellSnapShotStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return of(CellSnapShotStatus.FLAG, 0);
    }

    public static CellSnapshot ofLandMine() {
        return of(CellSnapShotStatus.LANDMINE, 0);
    }

    public static CellSnapshot ofNumber(int nearByLandMineCount) {
        return of(CellSnapShotStatus.NUMBER, nearByLandMineCount);
    }

    public static CellSnapshot ofUnchecked() {
        return of(CellSnapShotStatus.UNCHECKED, 0);
    }

    public CellSnapShotStatus getStatus() {
        return status;
    }

    public int getNearByLandMineCount() {
        return nearByLandMineCount;
    }

    public boolean isSameStatus(CellSnapShotStatus cellSnapShotStatus) {
        return this.status == cellSnapShotStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellSnapshot snapshot = (CellSnapshot) o;
        return nearByLandMineCount == snapshot.nearByLandMineCount && status == snapshot.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, nearByLandMineCount);
    }


}
