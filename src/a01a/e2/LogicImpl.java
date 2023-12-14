package a01a.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LogicImpl implements Logic {

    private List<Pair<Integer, Integer>> filledCellsPositions = new ArrayList<>();
    private List<Pair<Integer, Integer>> filledCellsLine = new ArrayList<>();

    @Override
    public boolean isCellFilled(Pair<Integer, Integer> position) {
        return filledCellsPositions.contains(position);
    }

    @Override
    public void toggleFillOnCell(Pair<Integer, Integer> position) {
        if(isCellFilled(position)) {
            filledCellsPositions.remove(position);
            filledCellsLine.clear();
        }
        else {
            filledCellsPositions.add(position);
            filledCellsLine.add(position);
        }
    }

    private boolean areDiagonallyAligned(List<Pair<Integer, Integer>> points) {
        // USE INTELLIGENT SOLUTION WHEN I FORGET IT
        return false;
    }

    @Override
    public boolean isGameOver() {
        return filledCellsLine.size() >= 3
            && areDiagonallyAligned(filledCellsLine);
    }
}
