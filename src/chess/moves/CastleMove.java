package chess.moves;

import chess.Board;
import chess.pieces.Piece;
import greenfoot.Color;

public class CastleMove extends MovementMove{
    final MovementMove rookMove;

    public CastleMove(Board board, Piece king, Piece rook) {
        super(board, king, king.getX()>rook.getX()? king.getX()-2:king.getX()+2, king.getY());
        this.rookMove = new MovementMove(board, rook, king.getX()>rook.getX()? king.getX()-1:king.getX()+1, rook.getY());
    }

    @Override
    public void execute() {
        super.execute();
        rookMove.execute();
        this.board.togglePlayingSide();
        this.board.setLastMove(this);
    }

    @Override
    public Color getColor() {
        return new Color(0, 100, 0);
    }
}
