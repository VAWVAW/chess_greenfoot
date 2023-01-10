package chess;

import chess.moves.BaseMove;
import chess.moves.SelectMove;
import chess.pieces.*;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Board {
    final Optional<Piece>[][] board;
    public final Square[][] squares;

    final Pieces[] pieces;

    int playingSide = 0;
    ArrayList<BaseMove> moves;
    boolean isActive = true;

    Optional<BaseMove> lastMove;

    public Board(World world){
        this.moves = new ArrayList<>();
        this.lastMove = Optional.empty();

        //noinspection unchecked
        this.board = (Optional<Piece>[][]) new Optional[8][8];
        this.squares = new Square[8][8];
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                this.board[x][y] = Optional.empty();
                this.squares[x][y] = new Square(world, x, y);
            }
        }

        // initialise Pieces
        this.pieces = new Pieces[2];
        for(int i=0; i<2; i++){
            ArrayList<Pawn> pawns = new ArrayList<>();
            for(int j=0; j<8; j++){
                pawns.add(new Pawn(world, this, i, j, 1 + i*5));
            }
            Pieces pieces = new Pieces(
                new King(world, this, i, 4, i*7),
                new Queen(world, this, i, 3, i*7),
                Arrays.asList(
                    new Rook(world, this, i, 0, i*7),
                    new Rook(world, this, i, 7, i*7)
                ),
                Arrays.asList(
                    new Bishop(world, this, i, 2, i*7),
                    new Bishop(world, this, i, 5, i*7)
                ),
                Arrays.asList(
                    new Knight(world, this, i, 1, i*7),
                    new Knight(world, this, i, 6, i*7)
                ),
                pawns
            );

            this.pieces[i] = pieces;
        }

        this.resetMoves();
    }

    public Pieces getPieces(int side) {
        return this.pieces[side];
    }

    public Optional<Piece> get(int x, int y){
        if(0 <= x && x < 8 && 0 <= y && y < 8) {
            return board[x][y];
        }
        return Optional.empty();
    }

    public void set(int x, int y, Piece piece) {
        if(this.board[x][y].isEmpty()){
            this.board[x][y] = Optional.of(piece);
        }
    }

    public void del(int x, int y) {
        if(this.board[x][y].isPresent()){
            this.board[x][y] = Optional.empty();
        }
    }

    public void togglePlayingSide(){
        this.playingSide = (this.playingSide + 1) % 2;
        this.resetMoves();
    }

    public void setMoves(ArrayList<BaseMove> moves){
        for(BaseMove move: this.moves) {
            this.squares[move.x][move.y].removeMove();
        }
        this.moves = moves;
        if(!this.isActive){
            return;
        }
        for(BaseMove move: this.moves) {
            this.squares[move.x][move.y].addMove(move);
        }
    }

    public void resetMoves(){
        ArrayList<BaseMove> moves = new ArrayList<>();
        for(Piece piece: this.getPieces(this.playingSide).all()){
            if(piece.canMove()) {
                moves.add(new SelectMove(this, piece));
            }
        }
        this.setMoves(moves);
    }

    public Optional<BaseMove> getLastMove(){
        return this.lastMove;
    }

    public void setLastMove(BaseMove move){
        this.lastMove = Optional.of(move);
    }

    public void endGame(){
        this.isActive = false;
        setMoves(new ArrayList<>());
    }

    public boolean isActive(){
        return this.isActive;
    }
}
