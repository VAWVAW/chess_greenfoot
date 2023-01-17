package chess.moves;

import chess.Board;
import chess.Settings;
import chess.pieces.Piece;
import greenfoot.GreenfootImage;

/**
 * Represents a move where a player has multiple choices that are not associated with a single field.
 */
abstract public class ChoiceMove extends BaseMove{
    /**
     * Generates a new ChoiceMove.
     * @param board the board to operate on
     * @param piece the piece to operate on
     * @param y the y-coordinate to display the choice on
     */
    ChoiceMove(Board board, Piece piece, int y) {
        super(board, piece, Settings.CHOICE_SQUARE_X, y);
    }

    /**
     * Returns the image to display for this choice.
     */
    public abstract GreenfootImage getImage();
}
