package chess.pieces;

import chess.moves.*;
import chess.Board;

import java.util.Optional;
import java.util.Vector;

public class Rook extends Piece{
    public Rook(Board board, boolean isLight, int x, int y) {
        super(board, isLight, x, y);
    }

    @Override
    public Vector<BaseMove> getMoves() {
        Vector<BaseMove> retMoves = new Vector<>();

        //TODO add castling

        // check directions
        int x, y;
        Optional<Piece> otherPiece;

        // towards left
        x = this.x - 1;
        while(x >= 0 && board.get(x, this.y).isEmpty()) {
            retMoves.add(new MovementMove(this.board, this, x, this.y));
            x -= 1;
        }
        otherPiece = board.get(x, this.y);
        if(otherPiece.isPresent() && (otherPiece.get().isLight != this.isLight)){
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        // towards right
        x = this.x + 1;
        while(x < 8 && board.get(x, this.y).isEmpty()) {
            retMoves.add( new MovementMove(this.board, this, x, this.y));
            x += 1;
        }
        otherPiece = board.get(x, this.y);
        if(otherPiece.isPresent() && otherPiece.get().isLight != this.isLight) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        // towards top
        y = this.y + 1;
        while(y < 8 && board.get(this.x, y).isEmpty()) {
            retMoves.add( new MovementMove(this.board, this, this.x, y));
            y += 1;
        }
        otherPiece = board.get(this.x, y);
        if(otherPiece.isPresent() && otherPiece.get().isLight != this.isLight) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        // towards bottom
        y = this.y - 1;
        while(y >= 0 && board.get(this.x, y).isEmpty()) {
            retMoves.add( new MovementMove(this.board, this, this.x, y));
            y -= 1;
        }
        otherPiece = board.get(this.x, y);
        if(otherPiece.isPresent() && otherPiece.get().isLight != this.isLight) {
            retMoves.add(new CaptureMove(board, this, otherPiece.get()));
        }

        return retMoves;
    }
}
