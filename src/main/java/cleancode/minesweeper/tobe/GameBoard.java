package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

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
        int rowSize = getRowSize();
        int colSize = getColSize();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();

            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMindCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            board[landMineRow][landMindCol] = new LandMineCell();

        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {

                if (isLandMinecell(row, col)) {
                    continue;
                }
                int count = countNearByLandMines(row, col);
                if (count == 0) {
                    continue;
                }
                board[row][col] =  new NumberCell(count);;

            }
        }
    }
    /**
     *
     * 위에 있는 삼중포문을 stream으로 줄였다!! 가독성이 있는 쪽으로 선택하장
     *
     */
    public boolean isAllCellsChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
        /**
         *.noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
         * 이건 cell이 비워있을 경우 널포인트 익셉션이 뜰수있다
         * .noneMatch(cell -> CLOSED_CELL_SIGN.equals(cell));
         * 이건 상수인 CLOSED_CELL_SIGN 과 비교하기떄문에 널포인트 익셉션이 안뜬다고 한다.
         */
//
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public int getRowSize() {
        return board.length;
    }
    public int getColSize() {
        return board[0].length;
    }
    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex,colIndex);
        return cell.getSign();
    }
    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpened(row, col)) {
            return;
        }
        if (isLandMinecell(row, col)) {
            return;
        }
        open(row,col);

        if (doesCellhasLandMineCount(row, col)) {
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellhasLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpened(int row, int col) {
        return findCell(row, col).isOpened();
    }

    private int countNearByLandMines(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMinecell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMinecell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMinecell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMinecell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMinecell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMinecell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMinecell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMinecell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }
    boolean isLandMinecell(int selectedRowIndex, int selectedColIndex) {
        return findCell(selectedRowIndex,selectedColIndex).isLandMine();
    }


    public void flag(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }
}
