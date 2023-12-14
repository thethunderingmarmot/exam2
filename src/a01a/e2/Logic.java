package a01a.e2;

public interface Logic {

    public boolean isCellFilled(Pair<Integer, Integer> position);
    public void toggleFillOnCell(Pair<Integer, Integer> position);

    public boolean isGameOver();

}
