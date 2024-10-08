package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;

import java.util.List;

public class CellSignFinder {

    private static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
            new EmptyCellSignProvider(),
            new FlagCellSIgnProvider(),
            new NumberCelllSIgnProvider(),
            new LandMIneCellSignProvider(),
            new UncheckedCellSIgnProvider()
    );

    public String findCellSignFrom(CellSnapshot snapshot) {

        return CELL_SIGN_PROVIDERS.stream()
                .filter(provider-> provider.supports(snapshot))
                .findFirst()// 없을수도 있으니 orElse 사용하기
                .map(provider-> provider.provide(snapshot))
                .orElseThrow(()-> new IllegalArgumentException("확인할수 없는 셀입니다"));
    }

}
