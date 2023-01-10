package chess.pieces;

import chess.Board;
import chess.moves.BaseMove;
import chess.moves.CaptureMove;
import chess.moves.DeselectMove;
import chess.moves.MovementMove;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Optional;

public class Knight extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_nlt64.png"), new GreenfootImage("Chess_ndt64.png")};

    public Knight(World world, Board board, int side, int x, int y) {
        super(world, board, side, x, y);
    }

    @Override
    public GreenfootImage getPieceImage(int side) {
        return image[side];
    }

    Optional<BaseMove> genMove(int x, int y){
        if(!(0 <= x && x < 8 && 0 <= y && y < 8)){
            return Optional.empty();
        }
        Optional<Piece> otherPiece = board.get(x, y);
        if(otherPiece.isEmpty()){
            return Optional.of(new MovementMove(board, this, x, y));
        }
        if(otherPiece.get().side == this.side){
            return Optional.empty();
        }
        return Optional.of(new CaptureMove(board, this, otherPiece.get()));
    }

    @Override
    public ArrayList<BaseMove> getMoves() {
        ArrayList<BaseMove> retMoves = new ArrayList<>();

        retMoves.add(new DeselectMove(board, this));

        // check movement and add movement and capture moves

        // top
        this.genMove(x+1, y+2).ifPresent(retMoves::add);
        this.genMove(x-1, y+2).ifPresent(retMoves::add);

        // bottom
        this.genMove(x+1, y-2).ifPresent(retMoves::add);
        this.genMove(x-1, y-2).ifPresent(retMoves::add);

        // right
        this.genMove(x+2, y+1).ifPresent(retMoves::add);
        this.genMove(x+2, y-1).ifPresent(retMoves::add);

        // left
        this.genMove(x-2, y+1).ifPresent(retMoves::add);
        this.genMove(x-2, y-1).ifPresent(retMoves::add);

        return retMoves;
    }
}
