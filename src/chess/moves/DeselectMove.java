package chess.moves;

import chess.Board;
import chess.pieces.Piece;
import greenfoot.Color;

/**
 * Represents the deselection of a piece.
 */
public class DeselectMove extends BaseMove{
    /**
     * Generates a new DeselectMove
     * @param board the board to operate on
     * @param piece the piece to deselect
     */
    public DeselectMove(Board board, Piece piece) {
        super(board, piece, piece.getX(), piece.getY());
    }

    /**
     * Deselect the piece displaying all valid {@link SelectMove SelectMoves} for all pieces.
     */
    @Override
    public void execute() {
        this.board.resetMoves();
    }

    /** {@inheritDoc} */
    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    /** {@inheritDoc} */
    @Override
    public int getMargin() {
        return 0;
    }
}
