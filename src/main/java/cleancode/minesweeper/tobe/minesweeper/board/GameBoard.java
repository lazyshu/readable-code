package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.GameLevel;

import java.util.List;
import java.util.Stack;

public class GameBoard {
  private static Cell[][] board;

  private final int landMineCount;
  private GameStatus gameStatus;

  public GameBoard(GameLevel gameLevel) {
    int rowSize = gameLevel.getRowSize();
    int colSize = gameLevel.getColSize();
    board = new Cell[rowSize][colSize];
    initializeGameStatus();
    landMineCount = gameLevel.getLandMineCount();
  }

  private static void initializeEmptyCells(CellPositions cellPositions) {
    List<CellPosition> allPositions = cellPositions.getPositions();
    for (CellPosition position : allPositions) {
      updateCellAt(position, new EmptyCell());
    }
  }

  private static void initializeLandMindCells(List<CellPosition> landMindPositions) {
    for (CellPosition position : landMindPositions) {
      updateCellAt(position, new LandMineCell());
    }
  }

  private static List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
    return RelativePosition.SURROUNDED_POSITION.stream()
      .filter(cellPosition::canCalculatePositionBy)
      .map(cellPosition::calculatePositionBy)
      .filter(position -> position.isRowIndexLessThan(rowSize))
      .filter(position -> position.isColIndexLessThan(colSize))
      .toList();
  }

  private static void updateCellAt(CellPosition position, Cell cell) {
    board[position.getRowIndex()][position.getColIndex()] = cell;
  }

  /**
   * -상태 변경
   * -판별
   * -조회
   */


  //상태 변경
  public void initializeGame() {
    CellPositions cellPositions = CellPositions.from(board);
    initializeGameStatus();

    initializeEmptyCells(cellPositions);
/**
 * 폭탄 수 설정해서 랜던 포지션 뽑아서 폭탄 자리 설정해준다.
 */
    List<CellPosition> landMindPositions = cellPositions.extractRandomPositions(landMineCount);
    initializeLandMindCells(landMindPositions);

    List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMindPositions);
    initializeNumberCells(numberPositionCandidates);
  }

  public void openAt(CellPosition cellPosition) {
    if (isLandMinecellAt(cellPosition)) {
      openOneCellAt(cellPosition);
      changeGameStatusToLose();
      return;
    }

    openSurroundedCells2(cellPosition);
    checkIfGameIsOver();
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();
    checkIfGameIsOver();
  }

  //판별
  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();
    return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
      || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  public boolean isInProgress() {
    return gameStatus == GameStatus.IN_PROGRESS;
  }

  public boolean isWinStatus() {
    return gameStatus == GameStatus.WIN;
  }

  public boolean isLoseStatus() {
    return gameStatus == GameStatus.LOSE;
  }

  //조회
  public CellSnapshot getSnapshot(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.getSnapShot();
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  private void initializeGameStatus() {
    gameStatus = GameStatus.IN_PROGRESS;
  }

  private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
    for (CellPosition candidatePosition : numberPositionCandidates) {
      int count = countNearByLandMines(candidatePosition);
      if (count != 0) {
        updateCellAt(candidatePosition, new NumberCell(count));
      }
    }
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

  private boolean doesCellHasLandMineCount(CellPosition cellPosition) {
    return findCell(cellPosition).hasLandMineCount();
  }

  private void openSurroundedCells2(CellPosition cellPosition) {
    Stack<CellPosition> stack = new Stack<>();
    stack.push(cellPosition);
    while (!stack.isEmpty()) {
      openAndPushCellAt(stack);
    }
  }

  private void openAndPushCellAt(Stack<CellPosition> stack) {
    CellPosition currentCellPosition = stack.pop();
    if (isOpenedCell(currentCellPosition)) {
      return;
    }
    if (isLandMinecellAt(currentCellPosition)) {
      return;
    }
    openOneCellAt(currentCellPosition);

    if (doesCellHasLandMineCount(currentCellPosition)) {
      return;
    }

    List<CellPosition> surroundedPositions = calculateSurroundedPositions(currentCellPosition, getRowSize(), getColSize());

    for (CellPosition surroundedPosition : surroundedPositions) {
      stack.push(surroundedPosition);
    }

  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isOpened();
  }

  private boolean isLandMinecellAt(CellPosition cellPosition) {
    return findCell(cellPosition).isLandMine();
  }

  private void openOneCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  private boolean isAllCellsChecked() {
    Cells cells = Cells.from(board);
    return cells.isAllChecked();
    /**
     *.noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN));
     * 이건 cell이 비워있을 경우 널포인트 익셉션이 뜰수있다
     * .noneMatch(cell -> CLOSED_CELL_SIGN.equals(cell));
     * 이건 상수인 CLOSED_CELL_SIGN 과 비교하기떄문에 널포인트 익셉션이 안뜬다고 한다.
     */
  }

  private void checkIfGameIsOver() {
    if (isAllCellsChecked()) {
      changeGameStatusToWIn();
    }
  }

  private void changeGameStatusToWIn() {
    gameStatus = GameStatus.WIN;
  }


  private void changeGameStatusToLose() {
    gameStatus = GameStatus.LOSE;
  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }

}
