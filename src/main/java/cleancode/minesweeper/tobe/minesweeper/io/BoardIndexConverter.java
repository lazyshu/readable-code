package cleancode.minesweeper.tobe.minesweeper.io;


import cleancode.minesweeper.tobe.minesweeper.exception.GameException;

public class BoardIndexConverter {
    private static final char BASED_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1); //첫번째 글자를 땐 뒤에 모두 가져오기
        return convertRowFrom(cellInputRow);
    }

    public int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    public int convertRowFrom(String cellInputRow) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (0 > rowIndex) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        int colIndex = cellInputCol - BASED_CHAR_FOR_COL;
        if (colIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }
        return colIndex;
    }
}

