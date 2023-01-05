package chess;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class Pieces {
    final ArrayList<Piece> pieces;
    final ArrayList<Rook> rooks;
    final ArrayList<Bishop> bishops;

    final King king;

    public Pieces(King king, List<Rook> rooks, List<Bishop> bishops){
        this.pieces = new ArrayList<>();

        pieces.add(king);
        this.king = king;

        pieces.addAll(rooks);
        this.rooks = new ArrayList<>(rooks);

        pieces.addAll(bishops);
        this.bishops = new ArrayList<>(bishops);

    }

    public ArrayList<Piece> all(){
        return this.pieces;
    }

    public King king(){
        return this.king;
    }

    public ArrayList<Rook> rooks(){
        return this.rooks;
    }

    public ArrayList<Bishop> bishops(){
        return this.bishops;
    }
}
