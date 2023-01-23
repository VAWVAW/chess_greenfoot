package chess.moves;

import chess.Board;
import chess.pieces.Piece;
import greenfoot.Color;

/**
 * Represents the move of castling in chess.
 */
public class CastleMove extends MovementMove{
    final MovementMove rookMove;

    /**
     * Generates a new CastleMove.
     * @param board the board to operate on
     * @param king the king to move
     * @param rook the rook to move
     */
    public CastleMove(Board board, Piece king, Piece rook) {
        super(board, king, king.getX()>rook.getX()? king.getX()-2:king.getX()+2, king.getY());
        this.rookMove = new MovementMove(board, rook, king.getX()>rook.getX()? king.getX()-1:king.getX()+1, rook.getY());
    }

    /**
     * Execute the castling without checking its validity.
     */
    @Override
    public void execute() {
        super.execute();
        rookMove.execute();
        this.board.togglePlayingSide(this);
    }

    /** {@inheritDoc} */
    @Override
    public Color getColor() {
        return new Color(0, 100, 0);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isInvalid() {
        //todo check all squares for check
        return false;
    }
}
