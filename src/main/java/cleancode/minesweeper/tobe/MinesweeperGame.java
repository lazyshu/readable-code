package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {
    private static final int BOARD_ROW_SIZE = 8;
    private static final int BOARD_COL_SIZE = 10;
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final int LAND_MINE_COUNT = 10 ;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        initializeGame();


        while (true) {
            try {
                showBoard();

                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }
                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("프로그램에 문제가 생겼습니다");
            }
            
        }
    }

    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();
            return;
        }

        if (DoesUserChooseToOpenCell(userActionInput)) {
            if (isLandMinecell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                 changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
            throw new AppException("잘못된 번호를 선택하셨습니다.");

    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMinecell(int selectedRowIndex, int selectedColIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }

    private static boolean DoesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private static int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private static String getUserActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();

    }

    private static String getCellInputFromUser() {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
         boolean isAllOpened = isAllCellsChecked();
        if (isAllOpened) {
             changeGameStatusToWIn();
        }
    }

    private static void changeGameStatusToWIn() {
        gameStatus = 1;
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

    /**
     *
     * 위에 있는 삼중포문을 stream으로 줄였다!! 가독성이 있는 쪽으로 선택하장
     *
     */
    private static boolean isAllCellsChecked() {
        return Arrays.stream(BOARD)
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

    private static int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (BOARD_ROW_SIZE < rowIndex||1>rowIndex) {
            throw new AppException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private static int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException("잘못된 입력입니다.");
        }
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                System.out.print(BOARD[row][col].getSign() + " ");
            }
            System.out.println();
        }
    }

    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] =Cell.create();
            }
        }

        for (int i = 0; i <   LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            BOARD[row][col].turnOnLandMine();

        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {

                if (isLandMinecell(row, col)) {
                    continue;
                }
                int count = countNearByLandMines(row, col);
                BOARD[row][col].updateNearByLandMineCount(count);

            }
        }
    }

    private static int countNearByLandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMinecell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMinecell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < 10 && isLandMinecell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMinecell(row, col - 1)) {
            count++;
        }
        if (col + 1 < 10 && isLandMinecell(row, col + 1)) {
            count++;
        }
        if (row + 1 < 8 && col - 1 >= 0 && isLandMinecell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < 8 && isLandMinecell(row + 1, col)) {
            count++;
        }
        if (row + 1 < 8 && col + 1 < 10 && isLandMinecell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= 10) {
            return;
        }
        if (BOARD[row][col].isOpened()) {
            return;
        }
        if (isLandMinecell(row, col)) {
            return;
        }
        BOARD[row][col].open();
        if (BOARD[row][col].hasLandMineCount()) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
