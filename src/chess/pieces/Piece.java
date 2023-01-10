package chess.pieces;

import chess.Settings;
import chess.moves.BaseMove;
import chess.Board;
import chess.moves.CaptureMove;
import chess.moves.MovementMove;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Optional;

public abstract class Piece extends Actor {
    int x;
    int y;
    boolean wasMoved;
    boolean wasCaptured = false;
    // 0 => light (white); 1 => dark (black)
    final int side;
    final Board board;

    public Piece(World world, Board board, int side, int x, int y) {
        this.board = board;
        this.side = side;

        this.setImage(this.getPieceImage(side));

        world.addObject(this, 0, 0);
        this.move(x, y);
        this.wasMoved = false;

        board.set(x, y, this);
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public abstract GreenfootImage getPieceImage(int side);

    public void move(int x, int y){
        this.x = x;
        this.y = y;
        this.wasMoved = true;
        this.setLocation(x + Settings.MARGIN_LEFT, 7 + Settings.MARGIN_TOP - y);
    }

    public void capture(){
        this.wasCaptured = true;
        this.getWorld().removeObject(this);
    }

    protected ArrayList<BaseMove> moveLine(int xChange, int yChange){
        return this.moveLine(xChange, yChange, -1);
    }
    protected ArrayList<BaseMove> moveLine(int xChange, int yChange, int maxValues){
        ArrayList<BaseMove> retMoves = new ArrayList<>();
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
        if(maxValues != 0 && otherPiece.isPresent() && otherPiece.get().side != this.side) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }
        return retMoves;
    }

    public boolean canMove(){
        return !this.wasCaptured && this.getMoves().size() != 1;
    }

    public abstract ArrayList<BaseMove> getMoves();
}
