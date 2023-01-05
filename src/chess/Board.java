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

    Pieces piecesLight;
    Pieces piecesDark;

    boolean playingSide = true;
    ArrayList<BaseMove> moves;
    boolean isActive = true;

    Optional<BaseMove> lastMove;

    public Board(World world){
        this.moves = new ArrayList<>();
        this.lastMove = Optional.empty();

        //noinspection MoveFieldAssignmentToInitializer,unchecked
        this.board = (Optional<Piece>[][]) new Optional[8][8];
        this.squares = new Square[8][8];
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                this.board[x][y] = Optional.empty();
                this.squares[x][y] = new Square(world, x, y);
            }
        }

        // initialise Pieces
        for(int i=0; i<2; i++){
            ArrayList<Pawn> pawns = new ArrayList<>();
            for(int j=0; j<8; j++){
                pawns.add(new Pawn(world, this, i==0, j, 1 + i*5));
            }
            Pieces pieces = new Pieces(
                new King(world, this, i==0, 4, i*7),
                new Queen(world, this, i==0, 3, i*7),
                Arrays.asList(
                    new Rook(world, this, i==0, 0, i*7),
                    new Rook(world, this, i==0, 7, i*7)
                ),
                Arrays.asList(
                    new Bishop(world, this, i==0, 2, i*7),
                    new Bishop(world, this, i==0, 5, i*7)
                ),
                Arrays.asList(
                    new Knight(world, this, i==0, 1, i*7),
                    new Knight(world, this, i==0, 6, i*7)
                ),
                pawns
            );

            if(i==0){
                this.piecesLight = pieces;
            }
            else this.piecesDark = pieces;
        }

        this.resetMoves();
    }

    public Pieces getPieces(boolean isLight) {
        if(isLight) {
            return this.piecesLight;
        }
        return this.piecesDark;
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
        this.playingSide = !this.playingSide;
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
