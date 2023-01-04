package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

public class MovementMove extends BaseMove{
    final int x;
    final int y;

    public MovementMove(Board board, Piece piece, int x, int y) {
        super(board, piece);
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        this.board.del(piece.getX(), piece.getY());
        this.board.set(x, y, piece);
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
