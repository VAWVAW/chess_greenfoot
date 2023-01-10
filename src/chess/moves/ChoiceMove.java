package chess.moves;

import chess.Board;
import chess.Settings;
import chess.pieces.Piece;
import greenfoot.GreenfootImage;

abstract public class ChoiceMove extends BaseMove{
    ChoiceMove(Board board, Piece piece, int y) {
        super(board, piece, Settings.CHOICE_SQUARE_X, y);
    }

    public abstract GreenfootImage getImage();
}
