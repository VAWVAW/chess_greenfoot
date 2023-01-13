package chess.pieces;

import chess.Settings;
import chess.moves.BaseMove;
import chess.Board;
import chess.moves.CaptureMove;
import chess.moves.MovementMove;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a chess piece
 */
public abstract class Piece extends Actor {
    int x;
    int y;
    boolean wasMoved;
    boolean wasCaptured = false;
    // 0 => light (white); 1 => dark (black)
    final int side;
    final Board board;

    /**
     * Generates the piece and adds it to the world and the board.
     * @param world the world to add the piece to
     * @param board the board to add the piece to
     * @param side the side of the piece
     * @param x the x-position on the board
     * @param y the y-position on the board
     */
    public Piece(World world, Board board, int side, int x, int y) {
        this.board = board;
        this.side = side;

        try {
            this.setImage((GreenfootImage) this.getClass().getMethod("getPieceImage", int.class).invoke(this, side));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        world.addObject(this, 0, 0);
        this.move(x, y);
        this.wasMoved = false;

        board.set(x, y, this);
    }

    /**
     * Returns the logical x-coordinate on the board.
     */
    public int getX(){
        return this.x;
    }
    /**
     * Returns the logical y-coordinate on the board.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Returns the side of the piece.
     */
    public int getSide(){
        return side;
    }

    /**
     * Moves the piece to the specified location on the visible chess board.
     * @param x the x-coordinate to move to starting with 0
     * @param y the y-coordinate to move to starting with 0 at the bottom
     */
    public void move(int x, int y){
        this.x = x;
        this.y = y;
        this.wasMoved = true;
        this.setLocation(x + Settings.MARGIN_LEFT, 7 + Settings.MARGIN_TOP - y);
    }

    /**
     * Remove the piece from the world and future evaluations.
     */
    public void capture(){
        this.wasCaptured = true;
        this.getWorld().removeObject(this);
    }

    /**
     * Returns all {@link chess.moves.MovementMove MovementMoves} in a straight line from the piece and a
     * {@link chess.moves.CaptureMove} if possible.
     * @param xChange the change in x-direction between checked fields
     * @param yChange the change in y-direction between checked fields
     * @return list of moves in that direction
     *
     * @see Piece#moveLine(int, int, int)
     */
    protected ArrayList<BaseMove> moveLine(int xChange, int yChange){
        return this.moveLine(xChange, yChange, -1);
    }
    /**
     * Returns a number of {@link chess.moves.MovementMove MovementMoves} in a straight line from the piece and a
     * {@link chess.moves.CaptureMove} if possible.
     * @param xChange the change in x-direction between checked fields
     * @param yChange the change in y-direction between checked fields
     * @param maxValues max number of returned {@link chess.moves.BaseMove BaseMoves}
     * @return list of moves in that direction
     */
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

    /**
     * Returns whether the piece can make a valid move.
     */
    public boolean canMove(){
        return !this.wasCaptured && this.getMoves().size() != 1;
    }

    /**
     * Returns all valid moves the piece can make including the move to deselect it.
     * @return all valid moves
     */
    public abstract ArrayList<BaseMove> getMoves();
}
