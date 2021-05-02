package inf101.sem2.game;

import inf101.grid.Grid;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.player.Player;

import java.util.ArrayList;
import java.util.List;


public class Othello extends Game {


    @Override
    public String getName() {
        return "Othello";
    }

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

    public int counter(Player player) {

        int i = 0;
        for(Location l : board.locations()) {
            if(board.get(l) == player) {
                i++;
            }
        }
        return i;
    }

    public boolean legalMoves(Location l) {
        return board.canPlace(l);
    }

    public void makinMoves(Location l) {

        try {
            if (anyLegalMoves(l) != null) {
                players.nextPlayer();
            }
        }
        catch (Exception e) {
            System.out.println("Invalid move.");
        }
    }

    /**
    public boolean theHood(Location l) {
        for (GridDirection egd : GridDirection.EIGHT_DIRECTIONS) {
            Location hood = l.getNeighbor(egd);

            try {
                if (board.get(l) == getCurrentPlayer()) {
                    return false;
                }
                if (board.get(hood) instanceof Player) {
                    if (board.get(hood) != getCurrentPlayer() && board.get(hood) != null) {
                        return true;
                    }

                }

        } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }
    */

    public ArrayList<GridDirection> anotherHood(Location l) {
        ArrayList<GridDirection> hood = new ArrayList<GridDirection>();
        for (GridDirection gd : GridDirection.EIGHT_DIRECTIONS) {
            Location traphouse = l.getNeighbor(gd);
            if(!board.isOnGrid(traphouse)) {
                continue;
            }
                if(board.get(traphouse) != null) {
                    if(board.get(traphouse) instanceof Player) {
                        if (board.get(traphouse) != getCurrentPlayer()) {
                            hood.add(gd);
                        }
                    }
                }
        }
        return hood;
    }

    public ArrayList<Location> waysToGo(Location l, GridDirection gd) {
        ArrayList<Location> locationsForFlippin = new ArrayList<>();
        while (true) {
            Location house = l.getNeighbor(gd);
            if(!board.isOnGrid(house)) {
                locationsForFlippin.clear();
                break;
            }
            if (board.get(l.getNeighbor(gd)) == getCurrentPlayer()) {
                locationsForFlippin.add(l);
                break;
            }
            if (l.getNeighbor(gd) == null) {
                locationsForFlippin.clear();
                break;
            }
            if (board.get(l.getNeighbor(gd)) != getCurrentPlayer()) {
                locationsForFlippin.add(l);
                l = l.getNeighbor(gd);
            }
        }
        return locationsForFlippin;
    }


    public Location anyLegalMoves(Location l) {

        ArrayList<Location> finnaFlip = new ArrayList<>();

        if (theHood(l)) {
            for (GridDirection gd : anotherHood(l)) {
                finnaFlip.addAll(waysToGo(l, gd));
                continue;
            }
        }
        if (!finnaFlip.isEmpty()) {
            wFlip(finnaFlip);
            return l;
        }
        return null;
    }

    @Override
    public boolean isWinner(Player player) {

        for (Player gamer : players) {
            if (gamer != player) {
                if (counter(gamer) == counter(player)) {
                    graphics.displayMessage("Draw!");
                    return false;
                }
                else if (counter(gamer) < counter(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Game copy() {

        Othello game = new Othello(graphics);
        copyTo(game);
        return game;
    }

    @Override
    public boolean gameOver() {

        if (getPossibleMoves().isEmpty())
            players.nextPlayer();
            if (getPossibleMoves().isEmpty())
                return true;
        return board.isFull();
    }

    public void flippin(ArrayList<Location> list) {
        for (Location l : list) {
            board.flip(l, getCurrentPlayer());
        }
    }

    public List<Location> moves() {
        ArrayList<Location> move = new ArrayList<>();
        for (Location l : super.getPossibleMoves()) {
            if (!hoodMembers(l).isEmpty() && board.isOnGrid(l) && fakeHood(l)) {
                for (GridDirection gd : hoodMembers(l)) {
                    //Location neighbor = loc.getNeighbor(gd)
                    if (!checkin(l, gd).isEmpty() && !move.contains(l)) {
                        move.add(l);
                    }
                }
            }
        }
        return move;
    }

    public void restart() {
        board.clear();
        players.restart();
        board.set(new Location(3, 3), getCurrentPlayer());
        board.set(new Location(4, 4), getCurrentPlayer());
        board.set(new Location(3, 4), players.nextPlayer());
        board.set(new Location(4, 3), getCurrentPlayer());
        graphics.display(board);
    }


}
