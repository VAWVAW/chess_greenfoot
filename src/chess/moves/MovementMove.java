package chess.moves;

import chess.pieces.King;
import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

/**
 * Represents a move that changes the location of a piece.
 */
public class MovementMove extends BaseMove {
    /**
     * Generates a new MovementMove.
     * @param board the board to operate on
     * @param piece the piece to move
     * @param x the x-coordinate for display
     * @param y the y-coordinate for display
     */
    public MovementMove(Board board, Piece piece, int x, int y) {
        super(board, piece, x, y);
    }


    /**
     * {@inheritDoc}
     * and change the location of the associated piece.
     */
    @Override
    public void execute() {
        this.board.del(piece.getX(), piece.getY());
        this.board.set(x, y, piece);
        piece.move(x, y);
        this.board.togglePlayingSide(this);
    }


    /** {@inheritDoc} */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }


    /** {@inheritDoc} */
    @Override
    public int getMargin() {
        return 16;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isInvalid() {
        King king = this.board.getKing();
        // can't counter knight with movement
        if(king.getAttackingKnights().size() != 0) {
            return true;
        }

        return this.board.testMoveCheck(this);
    }
}
