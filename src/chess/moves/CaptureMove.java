package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

/**
 * Represents the move to capture a piece in chess.
 */
public class CaptureMove extends MovementMove{
    final Piece capturedPiece;

    /**
     * Generates a new CaptureMove.
     * @param board the board to operate on
     * @param piece the capturing piece
     * @param capturedPiece the captured piece
     */
    public CaptureMove(Board board, Piece piece, Piece capturedPiece) {
        super(board, piece, capturedPiece.getX(), capturedPiece.getY());
        this.capturedPiece = capturedPiece;
    }

    /**
     * Generates a new CaptureMove using a custom endpoint.
     * @param board the board to operate on
     * @param piece the capturing piece
     * @param capturedPiece the captured piece
     * @param x the x-coordinate where the capturing piece should end
     * @param y the y-coordinate where the capturing piece should end
     */
    public CaptureMove(Board board, Piece piece, Piece capturedPiece, int x, int y) {
        super(board, piece, x, y);
        this.capturedPiece = capturedPiece;
    }

    /**
     * Captures and removes the captured piece and moves the capturing piece.
     */
    @Override
    public void execute(){
        capturedPiece.capture();
        super.execute();
    }

    /** {@inheritDoc} */
    @Override
    public Color getColor(){
        return Color.RED;
    }

    /** {@inheritDoc} */
    @Override
    public int getMargin(){
        return 0;
    }
}
