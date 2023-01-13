package chess.pieces;

import chess.Board;
import chess.moves.BaseMove;
import chess.moves.CaptureMove;
import chess.moves.DeselectMove;
import chess.moves.MovementMove;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a chess knight
 */
public class Knight extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_nlt64.png"), new GreenfootImage("Chess_ndt64.png")};

    /**
     * Generates the knight and adds it to the world and the board.
     * @param world the world to add the piece to
     * @param board the board to add the piece to
     * @param side the side of the piece
     * @param x the x-position on the board
     * @param y the y-position on the board
     */
    public Knight(World world, Board board, int side, int x, int y) {
        super(world, board, side, x, y);
    }

    /**
     * Returns the image of the piece for the given side.
     * @param side the side (color) of the piece
     * @return the image of the piece
     */
    @SuppressWarnings("unused")
    public static GreenfootImage getPieceImage(int side) {
        return image[side];
    }

    Optional<BaseMove> genMove(int x, int y){
        if(!(0 <= x && x < 8 && 0 <= y && y < 8)){
            return Optional.empty();
        }
        Optional<Piece> otherPiece = board.get(x, y);
        if(otherPiece.isEmpty()){
            return Optional.of(new MovementMove(board, this, x, y));
        }
        if(otherPiece.get().side == this.side){
            return Optional.empty();
        }
        return Optional.of(new CaptureMove(board, this, otherPiece.get()));
    }

    /** {@inheritDoc} */
    @Override
    public ArrayList<BaseMove> getMoves() {
        ArrayList<BaseMove> retMoves = new ArrayList<>();

        retMoves.add(new DeselectMove(board, this));

        // check movement and add movement and capture moves

        // top
        this.genMove(x+1, y+2).ifPresent(retMoves::add);
        this.genMove(x-1, y+2).ifPresent(retMoves::add);

        // bottom
        this.genMove(x+1, y-2).ifPresent(retMoves::add);
        this.genMove(x-1, y-2).ifPresent(retMoves::add);

        // right
        this.genMove(x+2, y+1).ifPresent(retMoves::add);
        this.genMove(x+2, y-1).ifPresent(retMoves::add);

        // left
        this.genMove(x-2, y+1).ifPresent(retMoves::add);
        this.genMove(x-2, y-1).ifPresent(retMoves::add);

        return retMoves;
    }
}
