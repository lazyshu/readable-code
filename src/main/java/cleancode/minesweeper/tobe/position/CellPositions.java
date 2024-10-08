package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//일급컬렉션- 하나의 final field만 가지고 있당. 리스트로 된걸 한번더 감싸서 new로 만들어줘야지 이걸 변경해도 그전 리스트드에게 영향이 없다.
public class CellPositions {
    private final List<CellPosition> positions;

    public CellPositions(List<CellPosition> positions) {
        this.positions = positions;
    }

    public static CellPositions of(List<CellPosition> positions) {
        return new CellPositions(positions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }
        return of(cellPositions);
    }

    public List<CellPosition> subtract(List<CellPosition> positionsListToSubtract) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);

        CellPositions positionToSubtract = CellPositions.of(positionsListToSubtract);

        return cellPositions.stream()
                .filter(positionToSubtract::doesNotContains)
                .toList();
    }

    private boolean doesNotContains(CellPosition position) {
        return !positions.contains(position);
    }


    public List<CellPosition> extractRandomPositions(int count) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);// 한번더 감싸줘야지 위에 final cellpositions의 순서가 바뀌지 않는다 같이 shuffle 되기떄문
        Collections.shuffle(new ArrayList<>(cellPositions));
        return cellPositions.subList(0, count);
    }
    public List<CellPosition> getPositions() {
        return new ArrayList<>(positions);
    }



}
