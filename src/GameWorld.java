import chess.Settings;
import greenfoot.World;

import chess.Board;
import chess.pieces.*;

public class GameWorld extends World {

    Board board;

    public GameWorld() {
        super(
                8 + Settings.MARGIN_LEFT + Settings.MARGIN_RIGHT,
                8 + Settings.MARGIN_TOP + Settings.MARGIN_BOTTOM,
                Settings.SQUARE_LEN
        );
        this.start();
    }

    void start(){
        this.board = new Board(this);
        Piece nextPiece = new Rook(board, true, 0, 0);
        board.addPiece(nextPiece);
        this.addObject(nextPiece, 0, 0);
        nextPiece.move();
        System.out.println(nextPiece.getMoves());
    }
}
