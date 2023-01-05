package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Pieces {
    final ArrayList<Piece> pieces;
    final ArrayList<Rook> rooks;
    final ArrayList<Bishop> bishops;
    final ArrayList<Knight> knights;

    final King king;
    final Queen queen;

    public Pieces(King king, Queen queen, List<Rook> rooks, List<Bishop> bishops, List<Knight> knights){
        this.pieces = new ArrayList<>();

        pieces.add(king);
        this.king = king;

        pieces.add(queen);
        this.queen = queen;

        pieces.addAll(rooks);
        this.rooks = new ArrayList<>(rooks);

        pieces.addAll(bishops);
        this.bishops = new ArrayList<>(bishops);

        pieces.addAll(knights);
        this.knights = new ArrayList<>(knights);
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
