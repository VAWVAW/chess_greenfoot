package chess.moves;

import chess.pieces.Piece;
import chess.Board;
import greenfoot.Color;

public abstract class BaseMove {
    final Piece piece;
    final Board board;

    BaseMove(Board board, Piece piece) {
        this.board = board;
        this.piece = piece;
    }

    public abstract void execute();

    public abstract Color getColor();
}
