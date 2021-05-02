package inf101.sem2.player;

import inf101.sem2.game.Othello;
import inf101.sem2.game.OthelloMove;

public abstract class oPlayer {

    public char colour = ' ';

    public oPlayer() {
        colour = ' ';
    }

    public abstract OthelloMove makeMove(Othello game);
}
