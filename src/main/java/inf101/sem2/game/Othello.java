package inf101.sem2.game;

import inf101.grid.Location;
import inf101.sem2.player.Player;


public class Othello extends Game {

    public static String newline = System.getProperty("line.separator");


    public Othello(Graphics graphics, Player p1, Player p2) {
        super(new GameBoard(8, 8), graphics);
        players.add(p1);
        players.add(p2);

        board.set(new Location(3, 4), p1);
        board.set(new Location(4, 3), p1);
        board.set(new Location(3, 3), p2);
        board.set(new Location(4, 4), p2);

    }


    public Othello(Graphics graphics) {
        super(new GameBoard(8, 8), graphics);

    }

    public Othello(Graphics graphics, Iterable<Player> players) {
        super(new GameBoard(8, 8), graphics, players);
        board.set(new Location(3, 4), getCurrentPlayer());
        board.set(new Location(4, 3), getCurrentPlayer());
        super.players.nextPlayer();
        board.set(new Location(3, 3), getCurrentPlayer());
        board.set(new Location(4, 4), getCurrentPlayer());

    }



    public char enemy(char player) {
        if (player == 'X')
            return 'O';
        else
            return 'X';
    }

    public int counter(char symbol) {
        if (Character.isUpperCase(symbol)) {
            symbol = Character.toLowerCase(symbol);
        }
        int count = 0;
        for (int c = 1; c <= 8; c++) {
            for (int r = 1; r <= 8; r++) {
                Location loc = new Location(c,r);
                if (loc.equals(symbol)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean wFlip(char player, int r, int c, int dir) {
        int dirI[] = {-1,1,0,0,1,-1,1,-1};
        int dirJ[] = {0,0,1,-1,1,-1,-1,1};
        int row = r, col = c;
        boolean flag = false;
        for (int i = 0; i <8; i++) {
            row += dirI[dir];
            col += dirJ[dir];
            Location loc = new Location(row,col);
            if (loc.equals(enemy(player))) {
                flag = true;
            }
            else if (loc.equals(player)) {
                if (flag)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        return false;
    }

    public void mFlip(char player, int r, int c, int dir) {
        int dirI[] = {-1,1,0,0,1,-1,1,-1};
        int dirJ[] = {0,0,1,-1,1,-1,-1,1};
        if (wFlip(player,r,c,dir)) {
            r += dirI[dir];
            c += dirJ[dir];
            Location loc = new Location(r, c);
            while (!loc.equals(player)) {
                loc.equals(player);
                r += dirI[dir];
                c += dirJ[dir];
            }
        }
    }

    public boolean legalMove(char player, int r, int c) {
        Location loc = new Location(r,c);
        if (loc.equals(' ')) {
            for (int k = 0; k < 8; k++)
                if (wFlip(player,r,c,k)) {
                    return true;
                }
        }
        return false;
    }

    public boolean anyLegalMoves(char player) {
        for (int i = 1; i <= 8; i++)
            for (int j = 1; j <= 8; j++)
                if (legalMove(player,i,j))
                    return true;
        return false;
    }

    public void makinMoves(char player, OthelloMove m) {
        int r = m.getRow();
        int c = m.getCol();
        Location loc = new Location(r,c);
        if (legalMove(player,r,c)) {
            loc.equals(player);
            for (int i = 0; i < 8; i++) {
                mFlip(player,r,c,i);
            }
        }
    }



    /**
    public void playGame(Player p1, Player p2, boolean show) {
        char currentMove = 'X';
        while (true) {
            OthelloMove move;

            if (currentMove != 'O') {
                move = p1.makeMove(this);
                if (move.noMoves()) {
                    if (anyLegalMoves(p2.colour)) {
                        System.out.println(p1 + "'s (Black) move");
                        if (move.gameOver()) {
                            System.out.println(p1 + " concedes. Game over!\n");
                            break;
                        }
                        else {
                            System.out.println("No valid moves. " + p1 + "must pass.");
                            currentMove = 'O';
                        }
                    }
                    else {
                        System.out.println("Game over!\n");
                        int differ = counter(p1.colour) - counter(p2.colour);
                        if (differ < 0)
                            System.out.println(p2 + " is the winner!");
                        else System.out.println(p1 + " is the winner!");
                        break;
                    }
                }
                else {
                    System.out.println(p1 + "'s (Black) move.");
                    makinMoves(p1.colour, move);
                    System.out.println(newline);
                    String moveString = "The move is   " + Integer.toString(move.getRow()) + ", " + Integer.toString(move.getCol());
                    System.out.println(moveString);
                    System.out.println(newline);
                    currentMove = 'O';
                    continue;
                }
            }
            if (currentMove != 'X') {
                move = p2.makeMove(this);
                if (move.noMoves()) {
                    if (anyLegalMoves(p1.colour)) {
                        System.out.println(p2 + "'s (White) move.");
                        if (move.gameOver()) {
                            System.out.println(p1 + " concedes. Game over!\n");
                            break;
                        }
                        else {
                            System.out.println("No valid moves." + p2 + " must pass.");
                            currentMove = 'X';
                        }
                    }
                    else {
                        System.out.println("Game over!\n");
                        int differ = counter(p1.colour) - counter(p2.colour);
                        if (differ < 0)
                            System.out.println(p2 + " is the winner.");
                        else
                            System.out.println(p1 + " is the winner.");
                        break;
                    }
                }
                else {
                    System.out.println(p2 + "'s (White) move.");
                    makinMoves(p2.colour, move);
                    currentMove = 'X';
                    System.out.println(newline);
                    String moveString = "The move is   " + Integer.toString(move.getRow()) + ", " + Integer.toString(move.getCol());
                    System.out.println(moveString);
                    System.out.println(newline);
                    continue;
                }
            }
        }
    }
    */
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
