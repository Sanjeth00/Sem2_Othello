package inf101.sem2.player;

import inf101.grid.Location;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.game.Othello;
import inf101.sem2.game.OthelloMove;

public class DumbPlayer extends AbstractPlayer {

	static int counter = 1;

	public DumbPlayer(char symbol) {
		super(symbol, "DumbPlayer " + counter++);
	}

	@Override
	public Location getMove(Game game) {
		GameBoard board = game.getGameBoard();
		for(Location loc : board.locations()) {
			if(game.canPlace(loc)) {
				return loc;
			}
		}
		return null;
	}


}
