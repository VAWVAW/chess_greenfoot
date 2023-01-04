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

    @Override
    public void execute(){
        board.del(x, y);
        super.execute();
    }

    @Override
    public Color getColor(){
        return Color.RED;
    }
}
