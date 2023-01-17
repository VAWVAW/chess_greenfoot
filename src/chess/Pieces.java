package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Organises the pieces for each side.
 */
public class Pieces {
    final ArrayList<Piece> pieces;
    final ArrayList<Rook> rooks;

    public Pieces(King king, Queen queen, List<Rook> rooks, List<Bishop> bishops, List<Knight> knights, ArrayList<Pawn> pawns){

        this.pieces = new ArrayList<>();

        pieces.add(king);

        pieces.add(queen);

        pieces.addAll(rooks);
        this.rooks = new ArrayList<>(rooks);

        pieces.addAll(bishops);

        pieces.addAll(knights);

        pieces.addAll(pawns);
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }

    /**
     * Returns all pieces for this side.
     */
    public ArrayList<Piece> all(){
        return this.pieces;
    }

    /**
     * Returns all rooks for this side.
     */
    public ArrayList<Rook> rooks(){
        return this.rooks;
    }

}
