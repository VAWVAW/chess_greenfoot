package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

public class CaptureMove extends MovementMove{
    final Piece capturedPiece;

    public CaptureMove(Board board, Piece piece, Piece capturedPiece) {
        super(board, piece, capturedPiece.getX(), capturedPiece.getY());
        this.capturedPiece = capturedPiece;
    }

    public CaptureMove(Board board, Piece piece, Piece capturedPiece, int x, int y) {
        super(board, piece, x, y);
        this.capturedPiece = capturedPiece;
    }

    @Override
    public void execute(){
        capturedPiece.capture();
        board.del(capturedPiece.getX(), capturedPiece.getY());
        super.execute();
    }

    @Override
    public Color getColor(){
        return Color.RED;
    }

    @Override
    public int getMargin(){
        return 0;
    }
}
