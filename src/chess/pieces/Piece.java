package chess.pieces;

import chess.Settings;
import chess.moves.BaseMove;
import chess.Board;
import greenfoot.Actor;
import java.util.Vector;

public abstract class Piece extends Actor {
    int x;
    int y;
    final boolean isLight; //also called white
    final Board board;

    public Piece(Board board, boolean isLight, int x, int y) {
        this.board = board;
        this.isLight = isLight;
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean getIsLight(){
        return this.isLight;
    }

    public void move(){
        this.setLocation(x + Settings.MARGIN_LEFT, 7 + Settings.MARGIN_TOP - y);
    }

    public abstract Vector<BaseMove> getMoves();

}