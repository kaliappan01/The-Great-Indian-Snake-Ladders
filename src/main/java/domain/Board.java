package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {


    private Tile[] tiles;
    private ArrayList<Player> players;

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}