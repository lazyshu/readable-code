package cleancode.minesweeper.tobe;



public class BoardIndexConverter {
    private static final char BASED_CHAR_FOR_COL = 'a';
    int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1); //첫번째 글자를 땐 뒤에 모두 가져오기
        return convertRowFrom(cellInputRow,rowSize);
    }

    int getSelectedColIndex(String cellInput, int colSize) {
        char cellInputCol = cellInput.charAt(1);
        return convertColFrom(cellInputCol,colSize );
    }
    private int convertRowFrom(String cellInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(cellInputRow)-1;
        if (rowSize < rowIndex||0>rowIndex) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol, int colSize) {
        int colIndex = cellInputCol - BASED_CHAR_FOR_COL;
        if (colIndex < 0 ||colIndex>colSize) {
            throw new GameException("잘못된 입력입니다.");
        }
        return colIndex;
    }
}

