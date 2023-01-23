package chess.moves;

import chess.Board;
import chess.pieces.Piece;
import greenfoot.Color;

/**
 * Represents the selection of a piece.
 */
public class SelectMove extends BaseMove {
    /**
     * Generates a new SelectMove
     * @param board the board to operate on
     * @param piece the piece to select
     */
    public SelectMove(Board board, Piece piece) {
        super(board, piece, piece.getX(), piece.getY());
    }

    /**
     * Select the piece displaying all its valid moves.
     */
    @Override
    public void execute() {
        this.board.setMoves(this.piece.getMoves());
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

    /** {@inheritDoc} */ @Override
    public boolean isInvalid() {
        return false;
    }
}
