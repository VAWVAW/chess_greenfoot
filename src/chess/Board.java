package chess;

import chess.pieces.Piece;
import greenfoot.World;

import java.util.Optional;
import java.util.Vector;

public class Board {
    private final Optional<Piece>[][] board;
    private final Square[][] squares;
    private final Vector<Piece> piecesLight;
    private final Vector<Piece> piecesDark;

    public Board(World world){
        this.piecesLight = new Vector<>();
        this.piecesDark = new Vector<>();

        //noinspection MoveFieldAssignmentToInitializer,unchecked
        this.board = (Optional<Piece>[][]) new Optional[8][8];
        this.squares = new Square[8][8];
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                this.board[x][y] = Optional.empty();
                this.squares[x][y] = new Square(world, x, y);
            }
        }
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
}
