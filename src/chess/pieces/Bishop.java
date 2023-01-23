package chess.pieces;

import chess.Board;
import chess.moves.BaseMove;
import chess.moves.DeselectMove;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;

/**
 * Represents a chess bishop
 */
public class Bishop extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_blt64.png"), new GreenfootImage("Chess_bdt64.png")};

    /**
     * Generates the bishop and adds it to the world and the board.
     * @param world the world to add the piece to
     * @param board the board to add the piece to
     * @param side the side of the piece
     * @param x the x-position on the board
     * @param y the y-position on the board
     */
    public Bishop(World world, Board board, int side, int x, int y) {
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

    /** {@inheritDoc} */
    @Override
    public ArrayList<BaseMove> getMoves() {
        ArrayList<BaseMove> retMoves = new ArrayList<>();

        retMoves.add(new DeselectMove(board, this));

        // check directions and add movement and capture moves

        // towards top left
        retMoves.addAll(this.moveLine(-1, 1));

        // towards bottom left
        retMoves.addAll(this.moveLine(-1, -1));

        // towards top right
        retMoves.addAll(this.moveLine(1, 1));

        // towards bottom right
        retMoves.addAll(this.moveLine(1, -1));

        retMoves.removeIf(BaseMove::isInvalid);
        return retMoves;
    }
}
