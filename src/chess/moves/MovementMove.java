package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

public class MovementMove extends BaseMove{

    public MovementMove(Board board, Piece piece, int x, int y) {
        super(board, piece, x, y);
    }

    @Override
    public void execute() {
        this.board.del(piece.getX(), piece.getY());
        this.board.set(x, y, piece);
        piece.move(x, y);
        this.board.togglePlayingSide();
        this.board.setLastMove(this);
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public int getMargin() {
        return 16;
    }
}
