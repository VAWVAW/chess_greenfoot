package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

public abstract class BaseMove {
    final Piece piece;
    final Board board;
    public final int x;
    public final int y;

    BaseMove(Board board, Piece piece, int x, int y) {
        this.board = board;
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public abstract void execute();

    public abstract Color getColor();

    public abstract int getMargin();
}
