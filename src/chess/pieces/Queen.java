package chess.pieces;

import chess.Board;
import chess.moves.BaseMove;
import chess.moves.DeselectMove;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;

public class Queen extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_qlt64.png"), new GreenfootImage("Chess_qdt64.png")};

    public Queen(World world, Board board, int side, int x, int y) {
        super(world, board, side, x, y);
    }

    @SuppressWarnings("unused")
    public static GreenfootImage getPieceImage(int side) {
        return image[side];
    }

    @Override
    public ArrayList<BaseMove> getMoves() {
        ArrayList<BaseMove> retMoves = new ArrayList<>();

        retMoves.add(new DeselectMove(board, this));

        // check directions and add movement and capture moves

        // towards left
        retMoves.addAll(this.moveLine(-1, 0));
        // towards right
        retMoves.addAll(this.moveLine(1, 0));
        // towards top
        retMoves.addAll(this.moveLine(0, 1));
        // towards bottom
        retMoves.addAll(this.moveLine(0, -1));
        // towards top left
        retMoves.addAll(this.moveLine(-1, 1));
        // towards bottom left
        retMoves.addAll(this.moveLine(-1, -1));
        // towards top right
        retMoves.addAll(this.moveLine(1, 1));
        // towards bottom right
        retMoves.addAll(this.moveLine(1, -1));

        return retMoves;
    }
}
