package chess.pieces;

import chess.Settings;
import chess.moves.BaseMove;
import chess.Board;
import chess.moves.CaptureMove;
import chess.moves.MovementMove;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.Optional;
import java.util.Vector;

public abstract class Piece extends Actor {
    int x;
    int y;
    boolean wasMoved;
    boolean wasCaptured = false;
    final boolean isLight; //also called white
    final Board board;
    final GreenfootImage image;

    public Piece(World world, Board board, boolean isLight, int x, int y, GreenfootImage image) {
        this.board = board;
        this.isLight = isLight;

        this.image = image;
        this.setImage(image);

        world.addObject(this, 0, 0);
        this.move(x, y);
        this.wasMoved = false;

        board.addPiece(this);
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

    public void move(int x, int y){
        this.x = x;
        this.y = y;
        this.wasMoved = true;
        this.setLocation(x + Settings.MARGIN_LEFT, 7 + Settings.MARGIN_TOP - y);
    }

    public boolean wasMoved(){
        return this.wasMoved;
    }

    public void capture(){
        this.wasCaptured = true;
        this.getWorld().removeObject(this);
    }

    protected Vector<BaseMove> moveLine(int xChange, int yChange){
        return this.moveLine(xChange, yChange, -1);
    }
    protected Vector<BaseMove> moveLine(int xChange, int yChange, int maxValues){
        Vector<BaseMove> retMoves = new Vector<>();
        int x = this.x + xChange;
        int y = this.y + yChange;
        Optional<Piece> otherPiece = board.get(x, y);
        while(maxValues != 0 && 0 <= x && x < 8 && 0 <= y && y < 8 && otherPiece.isEmpty()) {
            retMoves.add(new MovementMove(this.board, this, x, y));
            x += xChange;
            y += yChange;
            maxValues -= 1;
            otherPiece = board.get(x, y);
        }
        if(maxValues != 0 && otherPiece.isPresent() && otherPiece.get().isLight != this.isLight) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }
        return retMoves;
    }

    public boolean canMove(){
        return !this.wasCaptured && this.getMoves().size() != 1;
    }

    public abstract Vector<BaseMove> getMoves();
}
