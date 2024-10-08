package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.List;

public class GameBoard{
    private final int landMineCount;
    private static Cell[][] board;


    public GameBoard(GameLevel gameLevel) {
        int rowSize= gameLevel.getRowSize();
        int colSize= gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }


    public void initializeGame() {
        CellPositions cellPositions = CellPositions.from(board);


        initializeEmptyCells(cellPositions);
/**
 * 폭탄 수 설정해서 랜던 포지션 뽑아서 폭탄 자리 설정해준다.
 */
        List<CellPosition> landMindPositions=cellPositions.extractRandomPositions(landMineCount);
        initializeLandMindCells(landMindPositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMindPositions);
        initializeNumberCells(numberPositionCandidates);
    }

    private static void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions= cellPositions.getPositions();
        for (CellPosition position : allPositions) {
            updateCellAt(position, new EmptyCell());
        }
    }

    private static void initializeLandMindCells(List<CellPosition> landMindPositions) {
        for (CellPosition position : landMindPositions) {
            updateCellAt(position, new LandMineCell());
        }
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for (CellPosition candidatePosition : numberPositionCandidates) {
            int count = countNearByLandMines(candidatePosition);
            if (count != 0) {
                updateCellAt(candidatePosition,new NumberCell(count));
            }
        }
    }

    private static void updateCellAt(CellPosition position,Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }

    /**
     *
     * 위에 있는 삼중포문을 stream으로 줄였다!! 가독성이 있는 쪽으로 선택하장
     *
     */
    public boolean isAllCellsChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
        /**
         *.noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
         * 이건 cell이 비워있을 경우 널포인트 익셉션이 뜰수있다
         * .noneMatch(cell -> CLOSED_CELL_SIGN.equals(cell));
         * 이건 상수인 CLOSED_CELL_SIGN 과 비교하기떄문에 널포인트 익셉션이 안뜬다고 한다.
         */
    }

    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        Cell cell= findCell(cellPosition);
        return cell.getSnapShot();

    }
    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();
        return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
                || cellPosition.isColIndexMoreThanOrEqual(colSize);
    }

    private Cell findCell(CellPosition cellPosition) {

        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public int getRowSize() {
        return board.length;
    }
    public int getColSize() {
        return board[0].length;
    }

    public void openSurroundedCells(CellPosition cellPosition) {

        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMinecellAt(cellPosition)) {
            return;
        }
        openAt(cellPosition);

        if (doesCellHasLandMineCount(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition, getRowSize(), getColSize())
                .forEach(this::openSurroundedCells);


    }


    private boolean doesCellHasLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell cell= findCell(cellPosition);
        return cell.isOpened();
    }

    private int countNearByLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();


        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                .filter(this::isLandMinecellAt)
                .count();
        return (int) count;

//        if (row - 1 >= 0 && col - 1 >= 0 && isLandMinecellAt(row - 1, col - 1)) {
//            count++;
//        }
//        if (row - 1 >= 0 && isLandMinecellAt(row - 1, col)) {
//            count++;
//        }
//        if (row - 1 >= 0 && col + 1 < colSize && isLandMinecellAt(row - 1, col + 1)) {
//            count++;
//        }
//        if (col - 1 >= 0 && isLandMinecellAt(row, col - 1)) {
//            count++;
//        }
//        if (col + 1 < colSize && isLandMinecellAt(row, col + 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col - 1 >= 0 && isLandMinecellAt(row + 1, col - 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && isLandMinecellAt(row + 1, col)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col + 1 < colSize && isLandMinecellAt(row + 1, col + 1)) {
//            count++;
//        }
//        return count;
    }

    private static List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITION.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }

    boolean isLandMinecellAt(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }


    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }



}
