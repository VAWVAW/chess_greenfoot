package chess.pieces;

import chess.Board;
import chess.moves.*;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Optional;

public class Pawn extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_plt64.png"), new GreenfootImage("Chess_pdt64.png")};

    public Pawn(World world, Board board, int side, int x, int y) {
        super(world, board, side, x, y);
    }

    @SuppressWarnings("unused")
    public static GreenfootImage getPieceImage(int side) {
        return image[side];
    }

    @Override
    public ArrayList<BaseMove> getMoves() {
        ArrayList<BaseMove> retMoves = new ArrayList<>();
        int newY;

        retMoves.add(new DeselectMove(board, this));

        newY = y + (side==0 ? 1:-1);
        if(newY < 0 || newY > 7){
            retMoves.addAll(PromotionMove.generate(board, this));
            return retMoves;
        }

        // move forward
        if(board.get(x, newY).isEmpty()){
            retMoves.add(new MovementMove(board, this, x, newY));

            if(!this.wasMoved){
                newY = y + (side==0 ? 2:-2);
                if(board.get(x, newY).isEmpty()){
                    retMoves.add(new PawnDoubleMove(board, this, x, newY));
                }
            }
        }

        // captures
        Optional<Piece> otherPiece;

        newY = y + (side==0 ? 1:-1);
        otherPiece = board.get(x+1, newY);
        if(otherPiece.isPresent() && (otherPiece.get().side != this.side)) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        otherPiece = board.get(x-1, newY);
        if(otherPiece.isPresent() && (otherPiece.get().side != this.side)) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        // en passant
        if(board.getLastMove().isPresent() && board.getLastMove().get() instanceof PawnDoubleMove){
            PawnDoubleMove move = (PawnDoubleMove) board.getLastMove().get();
            if(move.y == this.y && Math.abs(move.x - this.x) == 1){
                retMoves.add(new CaptureMove(board, this, move.getPiece(), move.x, move.y + (side==0 ? 1:-1)));
            }
        }

        return retMoves;
    }
}
