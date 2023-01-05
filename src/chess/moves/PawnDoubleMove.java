package chess.moves;

import chess.Board;
import chess.pieces.Pawn;

public class PawnDoubleMove extends MovementMove{
    public PawnDoubleMove(Board board, Pawn piece, int x, int y) {
        super(board, piece, x, y);
    }
}
