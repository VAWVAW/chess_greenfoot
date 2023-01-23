package chess.pieces;

import chess.Board;
import chess.moves.*;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a chess pawn
 */
public class Pawn extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_plt64.png"), new GreenfootImage("Chess_pdt64.png")};

    /**
     * Generates the pawn and adds it to the world and the board.
     * @param world the world to add the piece to
     * @param board the board to add the piece to
     * @param side the side of the piece
     * @param x the x-position on the board
     * @param y the y-position on the board
     */
    public Pawn(World world, Board board, int side, int x, int y) {
        super(world, board, side, x, y);
    }

    /**
     * Returns the image of the piece for the given side.
     * @param side the side (color) of the piece
     * @return the image of the piece
     */
    @SuppressWarnings("unused")
    public static GreenfootImage getPieceImage(int side) {
        return image[side];
    }

    /** {@inheritDoc} */
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
                retMoves.add(new CaptureMove(board, this, move.piece, move.x, move.y + (side==0 ? 1:-1)));
            }
        }

        retMoves.removeIf(BaseMove::isInvalid);
        return retMoves;
    }
}
