package chess;

import chess.moves.BaseMove;
import chess.moves.SelectMove;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;
import greenfoot.World;

import java.util.Optional;
import java.util.Vector;

public class Board {
    final Optional<Piece>[][] board;
    public final Square[][] squares;

    final Vector<Piece> piecesLight;
    final Vector<Piece> piecesDark;
    final King kingLight;
    final King kingDark;

    boolean playingSide = true;
    Vector<BaseMove> moves;
    boolean isActive = true;

    public Board(World world){
        this.piecesLight = new Vector<>();
        this.piecesDark = new Vector<>();
        this.moves = new Vector<>();

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
        this.kingLight = new King(world, this, true, 4, 0);
        this.kingDark = new King(world, this, false, 4, 7);

        for(int i=0; i<2; i++){
            new Rook(world, this, i==0, 0, i*7);
            new Rook(world, this, i==0, 7, i*7);
            new Bishop(world, this, i==0, 2, i*7);
            new Bishop(world, this, i==0, 5, i*7);
        }

        this.resetMoves();
    }

    public void addPiece(Piece piece) {
        if(piece.getIsLight()) {
            this.piecesLight.add(piece);
        }
        else {
            this.piecesDark.add(piece);
        }
        this.board[piece.getX()][piece.getY()] = Optional.of(piece);
    }

    public Vector<Piece> getPieces(boolean isLight) {
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

    public void setMoves(Vector<BaseMove> moves){
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
        Vector<BaseMove> moves = new Vector<>();
        for(Piece piece: this.getPieces(this.playingSide)){
            if(piece.canMove()) {
                moves.add(new SelectMove(this, piece));
            }
        }
        this.setMoves(moves);
    }

    public void endGame(){
        this.isActive = false;
        setMoves(new Vector<>());
    }

    public boolean isActive(){
        return this.isActive;
    }
}
