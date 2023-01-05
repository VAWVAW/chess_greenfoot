package chess.moves;

import chess.Board;
import chess.pieces.Piece;
import greenfoot.Color;

public class SelectMove extends BaseMove {
    public SelectMove(Board board, Piece piece) {
        super(board, piece, piece.getX(), piece.getY());
    }

    @Override
    public void execute() {
        this.board.setMoves(this.piece.getMoves());
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public int getMargin() {
        return 0;
    }
}
