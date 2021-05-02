package inf101.sem2.game;

public class OthelloMove {

    private int toRow, toCol;
    private boolean noMove = false;
    private boolean gg = false;

    public OthelloMove(int r, int c) {
        toRow = r;
        toCol = c;
    }

    public int getRow() {
        return toRow;
    }

    public int getCol() {
        return toCol;
    }

    public void concede() {
        noMove = true;
        gg = true;
    }

    public boolean gameOver() {
        return gameOver();
    }

    public boolean noMoves() {
        return noMove;
    }
}
