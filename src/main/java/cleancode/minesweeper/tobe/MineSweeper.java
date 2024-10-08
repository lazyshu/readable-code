package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

public class MineSweeper implements GameRunnable, GameInitializable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private GameStatus gameStatus;
    public MineSweeper(GameConfig gameConfig) {
        gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
        gameStatus = GameStatus.IN_PROGRESS; //여기서 처음 초기화하고 혹시모르니 initialize에서 또 초기화해준당
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
        gameStatus = GameStatus.IN_PROGRESS;
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outputHandler.showGameLosingComment();
                    break;
                }

                CellPosition cellPosition = getCellInputFromUser();
                UserAction userActionInput = getUserActionInputFromUser();
                actOnCell(cellPosition, userActionInput);
            } catch (GameException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다");
            }

        }

    }
    private void actOnCell(CellPosition cellPosition, UserAction userActionInput) {

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (DoesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMinecellAt(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(cellPosition);
            checkIfGameIsOver();
            return;
        }
        throw new GameException("잘못된 번호를 선택하셨습니다.");

    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }



    private boolean DoesUserChooseToOpenCell(UserAction userActionInput) {

        return userActionInput==UserAction.OPEN;
    }

    private boolean doesUserChooseToPlantFlag(UserAction userActionInput) {
        return userActionInput==UserAction.FLAG;
    }



    private UserAction getUserActionInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        return inputHandler.getUserActionFromUser();

    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForUserAction();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();
        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException("잘못된 좌표를 선택하셨습니다");
        }
        return cellPosition;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == GameStatus.LOSE;
    }

    private boolean doesUserWinTheGame() {

        return gameStatus == GameStatus.WIN;
    }

    private  void checkIfGameIsOver() {
        if (gameBoard. isAllCellsChecked()) {
            changeGameStatusToWIn();
        }
    }

    private  void changeGameStatusToWIn() {
        gameStatus = GameStatus.WIN;
    }

//    private static boolean isAllCellsOpened() {
//        boolean isAllOpened = true;
//        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
//            for (int col = 0; col < BOARD_COL_SIZE; col++) {
//                if (BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
//                    isAllOpened = false;
//                }
//            }
//        }
//        return isAllOpened;
//    }













}
