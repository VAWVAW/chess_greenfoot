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

public class Pawn extends Piece{
    public Pawn(World world, Board board, boolean isLight, int x, int y) {
        super(world, board, isLight, x, y, new GreenfootImage(isLight? "Chess_plt64.png":"Chess_pdt64.png"));
    }

    @Override
    public ArrayList<BaseMove> getMoves() {
        ArrayList<BaseMove> retMoves = new ArrayList<>();
        int newY;

        retMoves.add(new DeselectMove(board, this));

        newY = y + (isLight? 1:-1);
        if(newY < 0 || newY > 7){
            //todo add promotion
            return retMoves;
        }

        // move forward
        if(board.get(x, newY).isEmpty()){
            retMoves.add(new MovementMove(board, this, x, newY));

            if(!this.wasMoved){
                newY = y + (isLight? 2:-2);
                if(board.get(x, newY).isEmpty()){
                    retMoves.add(new MovementMove(board, this, x, newY));
                }
            }
        }

        // captures
        Optional<Piece> otherPiece;

        newY = y + (isLight? 1:-1);
        otherPiece = board.get(x+1, newY);
        if(otherPiece.isPresent() && (otherPiece.get().isLight != this.isLight)) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        otherPiece = board.get(x-1, newY);
        if(otherPiece.isPresent() && (otherPiece.get().isLight != this.isLight)) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }


        return retMoves;
    }
}
