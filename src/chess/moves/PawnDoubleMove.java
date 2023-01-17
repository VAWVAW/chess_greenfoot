package chess.moves;

import chess.Board;
import chess.pieces.Pawn;

/**
 * In chess, a pawn can move two fields the first time it moves.
 * This is represented separately from {@link MovementMove} for internal reasons.
 */
public class PawnDoubleMove extends MovementMove{
    /**
     * Generates a new PawnDoubleMove.
     * @param board the board to operate on
     * @param piece the piece to move
     * @param x the x-coordinate for display
     * @param y the y-coordinate for display
     */
    public PawnDoubleMove(Board board, Pawn piece, int x, int y) {
        super(board, piece, x, y);
    }
}
