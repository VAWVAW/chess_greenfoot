package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

/**
 * Represents a possible move in the game that can be executed.
 */
public abstract class BaseMove {
    final Board board;

    public final Piece piece;
    public final int x;
    public final int y;

    /**
     * Generates a move and store the used data.
     * @param board the board to operate on
     * @param piece the piece to operate on
     * @param x the x-coordinate for display
     * @param y the y-coordinate for display
     */
    BaseMove(Board board, Piece piece, int x, int y) {
        this.board = board;
        this.piece = piece;
        this.x = x;
        this.y = y;
    }


    /**
     * Execute the action associated with this move
     */
    public abstract void execute();

    /**
     * Returns the Color to be displayed for this move.
     */
    public abstract Color getColor();

    /**
     * Returns the margin of the colored space on a {@link chess.Square Square}.
     */
    public abstract int getMargin();

    /**
     * Returns whether the move is valid.
     */
    public abstract boolean isInvalid();
}
