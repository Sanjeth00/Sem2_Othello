package inf101.sem2.game;

import inf101.sem2.player.Player;

public class Othello extends Game {

    public Othello(Graphics graphics, Player p1, Player p2) {
        this(graphics);
        players.add(p1);
        players.add(p2);
    }

    public Othello(Graphics graphics) {
        super(new GameBoard(8, 8), graphics);
    }

    public Othello(Graphics graphics, Iterable<Player> players) {
        super(new GameBoard(8, 8), graphics, players);
    }

    @Override
    public boolean isWinner(Player player) {
        return false;
    }

    @Override
    public boolean gameOver() {
        for(Player p : players) {
            if(isWinner(p)) {
                return true;
            }
        }
        return board.isFull();
    }

    @Override
    public Game copy() {
        Othello game = new Othello(graphics);
        copyTo(game);
        return game;
    }



    @Override
    public String getName() {
        return "Othello";
    }
}
