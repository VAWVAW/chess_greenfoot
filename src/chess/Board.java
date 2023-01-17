package chess;

import chess.moves.BaseMove;
import chess.moves.ChoiceMove;
import chess.moves.SelectMove;
import chess.pieces.*;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Manages a chess game.
 */
public class Board {
    final Optional<Piece>[][] board;
    public final Square[][] squares;
    public final ChoiceSquare[] choiceSquares;

    final Pieces[] pieces;

    // 0 => light (white); 1 => dark (black)
    int playingSide = 0;
    ArrayList<BaseMove> moves;
    boolean isActive = true;

    Optional<BaseMove> lastMove;

    /**
     * Initialises the game.
     * @param world the world to display the game on
     */
    public Board(World world){
        this.moves = new ArrayList<>();
        this.lastMove = Optional.empty();

        //noinspection unchecked
        this.board = (Optional<Piece>[][]) new Optional[8][8];
        this.squares = new Square[8][8];
        this.choiceSquares = new ChoiceSquare[8];
        for(int i=0; i<8; i++){
            for(int y=0; y<8; y++){
                this.board[i][y] = Optional.empty();
                this.squares[i][y] = new Square(world, i, y);
            }
            this.choiceSquares[i] = new ChoiceSquare(world, i);
        }

        // initialise Pieces
        this.pieces = new Pieces[2];
        for(int i=0; i<2; i++){
            ArrayList<Pawn> pawns = new ArrayList<>();
            for(int j=0; j<8; j++){
                pawns.add(new Pawn(world, this, i, j, 1 + i*5));
            }
            List<Rook> rooks = Arrays.asList(
                new Rook(world, this, i, 0, i*7),
                new Rook(world, this, i, 7, i*7)
            );
            List<Bishop> bishops = Arrays.asList(
                new Bishop(world, this, i, 2, i*7),
                new Bishop(world, this, i, 5, i*7)
            );
            List<Knight> knights = Arrays.asList(
                new Knight(world, this, i, 1, i*7),
                new Knight(world, this, i, 6, i*7)
            );
            Pieces pieces = new Pieces(
                new King(world, this, i, 4, i*7),
                new Queen(world, this, i, 3, i*7),
                rooks,
                bishops,
                knights,
                pawns
            );
            this.pieces[i] = pieces;
        }
        this.resetMoves();
    }

    /**
     * Returns the pieces for the given side.
     * @param side 0 => light (white); 1 => dark (black)
     */
    public Pieces getPieces(int side) {
        return this.pieces[side];
    }

    /**
     * Returns the piece at the given position.
     * @param x x-coordinate (0 <= x < 8)
     * @param y y-coordinate (0 <= y < 8)
     */
    public Optional<Piece> get(int x, int y){
        if(0 <= x && x < 8 && 0 <= y && y < 8) {
            return board[x][y];
        }
        return Optional.empty();
    }

    /**
     * Sets the piece at the given position if it is empty.
     * @param x x-coordinate (0 <= x < 8)
     * @param y y-coordinate (0 <= y < 8)
     */
    public void set(int x, int y, Piece piece) {
        if(this.board[x][y].isEmpty()){
            this.board[x][y] = Optional.of(piece);
        }
    }

    /**
     * Removes the piece at the given position.
     * @param x x-coordinate (0 <= x < 8)
     * @param y y-coordinate (0 <= y < 8)
     */
    public void del(int x, int y) {
        if(this.board[x][y].isPresent()){
            this.board[x][y] = Optional.empty();
        }
    }

    /**
     * Toggles the player and generates all possible moves.
     * @param lastMove the move that was executed
     */
    public void togglePlayingSide(BaseMove lastMove){
        this.lastMove = Optional.of(lastMove);
        this.playingSide = (this.playingSide + 1) % 2;
        this.resetMoves();
    }

    /**
     * Replaces all active moves with new ones.
     */
    public void setMoves(ArrayList<BaseMove> moves){
        for(BaseMove move: this.moves) {
            if(move instanceof ChoiceMove){
                this.choiceSquares[move.y].removeMove();
                continue;
            }
            this.squares[move.x][move.y].removeMove();
        }
        this.moves = moves;
        if(!this.isActive){
            return;
        }
        for(BaseMove move: this.moves) {
            if(move instanceof ChoiceMove){
                this.choiceSquares[move.y].addChoiceMove((ChoiceMove) move);
                continue;
            }
            this.squares[move.x][move.y].addMove(move);
        }
    }

    /**
     * Clears all moves and generates {@link SelectMove SelectMoves} for all pieces.
     */
    public void resetMoves(){
        ArrayList<BaseMove> moves = new ArrayList<>();
        for(Piece piece: this.getPieces(this.playingSide).all()){
            if(piece.canMove()) {
                moves.add(new SelectMove(this, piece));
            }
        }
        this.setMoves(moves);
    }

    /**
     * Returns the last move that was executed.
     * @return empty on first move
     */
    public Optional<BaseMove> getLastMove(){
        return this.lastMove;
    }

    /**
     * Ends the game.
     */
    public void endGame(){
        this.isActive = false;
        setMoves(new ArrayList<>());
    }

    /**
     * Returns whether the game is running.
     */
    public boolean isActive(){
        return this.isActive;
    }
}
