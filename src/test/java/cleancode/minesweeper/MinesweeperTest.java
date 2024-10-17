package cleancode.minesweeper;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapShotStatus;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellState;
import cleancode.minesweeper.tobe.minesweeper.board.cell.EmptyCell;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MinesweeperTest {
  @DisplayName("0보다 작은 수의 좌표를 가질수 없습니다.")
  @Test
  void CellPositionTest(){
    //when //given
    int rowSize = -1;
    int colSize = 0;
    // then
    assertThatThrownBy(() -> CellPosition.of(rowSize,colSize))
      .isInstanceOf(IllegalArgumentException.class)//exception 검증
      .hasMessage("올바르지 않은 좌표입니다.")//메세지 검증
    ;
  }
  @DisplayName("cellpostion의 가로길이가 입력받은수보다 크거나같을때 true반환")
  @Test
  void cellPositionIsRowIndexMoreThanOrEqual(){
  //given
    int rowSize=20;
    int colSize= 30;
    CellPosition cellPosition = CellPosition.of(rowSize,colSize);

  //when
    boolean result = cellPosition.isRowIndexMoreThanOrEqual(20);

  //then
    assertThat(result).isTrue();
  }
  @DisplayName("cellpostion의 가로길이가 입력받은 수보다 작을때 false반환")
  @Test
  void throwExceptionWhencellPositionIsRowIndexIsLesserThanValue(){
  //given
    int rowSize=20;
    int colSize= 30;
    CellPosition cellPosition = CellPosition.of(rowSize,colSize);

  //when
    boolean result = cellPosition.isRowIndexMoreThanOrEqual(21);

  //then
    assertThat(result).isFalse();
  }



  @DisplayName("셀이 열렸을땐 true 반환, 열린적이 없을땐 false를 반환한다")
  @Test
  void CheckCellState_OPEN(){
  //given
    CellState cellState = CellState.initialize();
    assertThat(cellState.isOpened()).isFalse();
  //when
    cellState.open();

  //then
    assertThat(cellState.isOpened()).isTrue();
  }

  @DisplayName("셀에 깃발이 꽂여있을땐 true 반환, 열린적이 없을땐 false를 반환한다")
  @Test
  void CheckCellState_FLAG(){
  //given
    CellState cellState = CellState.initialize();
    assertThat(cellState.isFlagged()).isFalse();
  //when
    cellState.flag();

  //then
    assertThat(cellState.isFlagged()).isTrue();
  }

  @DisplayName("입력받은 셀 근처의 지뢰수가 잘 저장되었는지 확인 3->3")
  @Test
  void getNearByLandMineCountFromCell(){
  //given
    int landmines=3;
    CellSnapshot cellSnapshot =CellSnapshot.ofNumber(landmines);

  //when
    int nearByLandMines = cellSnapshot.getNearByLandMineCount();

  //then
    assertThat(landmines).isEqualTo(nearByLandMines);

  }
  @DisplayName("셀 지정후 상태 확인하기 flag생성후 flag들어있으면 true, 아니면 false반환")
  @Test
  void CheckingCellSnapShopStatus(){
  //given
    CellSnapshot cellSnapshot=CellSnapshot.ofFlag();
    CellSnapshot cellSnapshot2=CellSnapshot.ofFlag();

  //when
    CellSnapShotStatus cellSnapShotStatus = CellSnapShotStatus.FLAG;
    CellSnapShotStatus cellSnapShotStatus2 = CellSnapShotStatus.UNCHECKED;

  //then
    assertThat(cellSnapshot.isSameStatus(cellSnapShotStatus)).isTrue();
    assertThat(cellSnapshot2.isSameStatus(cellSnapShotStatus2)).isFalse();

  }
}
