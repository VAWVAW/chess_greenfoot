package chess.pieces;

import chess.Board;
import chess.moves.*;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;

/**
 * Represents a chess king
 */
public class King extends Piece{
    static final GreenfootImage[] image = new GreenfootImage[] {new GreenfootImage("Chess_klt64.png"), new GreenfootImage("Chess_kdt64.png")};

    /**
     * Generates the king and adds it to the world and the board.
     * @param world the world to add the piece to
     * @param board the board to add the piece to
     * @param side the side of the piece
     * @param x the x-position on the board
     * @param y the y-position on the board
     */
    public King(World world, Board board, int side, int x, int y) {
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

        retMoves.add(new DeselectMove(board, this));

        // check castling
        if(!this.wasMoved){
            for(Rook rook: board.getPieces(side).rooks()){
                if(!rook.wasMoved) {
                    int x = this.x;
                    do{
                        x += this.x > rook.x ? -1 : 1;
                    } while(board.get(x, this.y).isEmpty());

                    if(board.get(x, this.y).get() == rook){
                        retMoves.add(new CastleMove(board, this, rook));
                    }
                }
            }
        }

        // check directions and add movement and capture moves
        // towards left
        retMoves.addAll(this.moveLine(-1, 0, 1));
        // towards right
        retMoves.addAll(this.moveLine(1, 0, 1));
        // towards top
        retMoves.addAll(this.moveLine(0, 1, 1));
        // towards bottom
        retMoves.addAll(this.moveLine(0, -1, 1));
        // towards top left
        retMoves.addAll(this.moveLine(-1, 1, 1));
        // towards bottom left
        retMoves.addAll(this.moveLine(-1, -1, 1));
        // towards top right
        retMoves.addAll(this.moveLine(1, 1, 1));
        // towards bottom right
        retMoves.addAll(this.moveLine(1, -1, 1));

        // check for invalid moves
        ArrayList<BaseMove> invalidMoves = new ArrayList<>();
        for(BaseMove move: retMoves) {
            if(!(move instanceof MovementMove))
                continue;

            int oldX = this.x, oldY = this.y;
            this.x = move.x;
            this.y = move.y;

            if(this.board.testMoveCheck((MovementMove) move)) {
                invalidMoves.add(move);
            }

            this.x = oldX;
            this.y = oldY;
        }
        for(BaseMove move: invalidMoves){
            retMoves.remove(move);
        }
        return retMoves;
    }

    /**
     * Ends the game.
     */
    @Override
    public void capture(){
        super.capture();
        board.endGame();
    }

    /**
     * Returns whether the king is currently in check.
     */
    public boolean isInCheck() {
        return this.getAttackers().size() != 0;
    }

    /**
     * Returns all knights that threaten the king.
     */
    public ArrayList<Knight> getAttackingKnights() {
        ArrayList<Knight> attackers = new ArrayList<>();
        ArrayList<BaseMove> knightMoves = new ArrayList<>();
        this.genMove(x+1, y+2).ifPresent(knightMoves::add);
        this.genMove(x-1, y+2).ifPresent(knightMoves::add);
        this.genMove(x+1, y-2).ifPresent(knightMoves::add);
        this.genMove(x-1, y-2).ifPresent(knightMoves::add);
        this.genMove(x+2, y+1).ifPresent(knightMoves::add);
        this.genMove(x+2, y-1).ifPresent(knightMoves::add);
        this.genMove(x-2, y+1).ifPresent(knightMoves::add);
        this.genMove(x-2, y-1).ifPresent(knightMoves::add);

        knightMoves.removeIf(m -> !(m instanceof CaptureMove));

        for(BaseMove move: knightMoves) {
            Piece otherPiece = move.piece;
            if(otherPiece instanceof Knight) {
                attackers.add((Knight) otherPiece);
            }
        }
        return attackers;
    }

    /**
     * Returns all pieces that threaten the king in a straight line.
     */
    public ArrayList<Piece> getAttackersLine() {
        ArrayList<Piece> attackers = new ArrayList<>();

        ArrayList<BaseMove> diagonalMoves = this.moveLine(1, 1);
        diagonalMoves.addAll(this.moveLine(1, -1));
        diagonalMoves.addAll(this.moveLine(-1, 1));
        diagonalMoves.addAll(this.moveLine(-1, -1));

        ArrayList<BaseMove> straightMoves = this.moveLine(1, 0);
        straightMoves.addAll(this.moveLine(-1, 0));
        straightMoves.addAll(this.moveLine(0, 1));
        straightMoves.addAll(this.moveLine(0, -1));

        diagonalMoves.removeIf(m -> !(m instanceof CaptureMove));
        straightMoves.removeIf(m -> !(m instanceof CaptureMove));

        for(BaseMove move: diagonalMoves) {
            Piece otherPiece = ((CaptureMove)move).getCapturedPiece();
            if(otherPiece instanceof Bishop || otherPiece instanceof Queen) {
                attackers.add(otherPiece);
            }
            if(otherPiece instanceof Pawn) {
                ArrayList<BaseMove> movesOther = otherPiece.getMoves();
                movesOther.removeIf(m -> !(m instanceof CaptureMove));
                movesOther.removeIf(m -> ((CaptureMove)m).getCapturedPiece() != this);
                if(movesOther.size() != 0){
                    attackers.add(otherPiece);
                }
            }
            if(otherPiece instanceof King && Math.abs(otherPiece.x - this.x) <= 1 && Math.abs(otherPiece.y - this.y) <= 1 ) {
                attackers.add(otherPiece);
            }
        }

        for(BaseMove move: straightMoves) {
            Piece otherPiece = ((CaptureMove)move).getCapturedPiece();
            if(otherPiece instanceof Rook || otherPiece instanceof Queen) {
                attackers.add(otherPiece);
            }
            if(otherPiece instanceof King && Math.abs(otherPiece.x - this.x) <= 1 && Math.abs(otherPiece.y - this.y) <= 1 ) {
                attackers.add(otherPiece);
            }
        }
        return attackers;
    }

    /**
     * Returns all pieces that threaten the king.
     */
    public ArrayList<Piece> getAttackers() {
        ArrayList<Piece> attackers = this.getAttackersLine();
        attackers.addAll(this.getAttackingKnights());
        return attackers;
    }
}
