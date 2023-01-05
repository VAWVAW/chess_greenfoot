import chess.Settings;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
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

    @Override
    public void act(){
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if(mouseInfo != null && mouseInfo.getButton() == 1){
            int x = mouseInfo.getX() - Settings.MARGIN_LEFT;
            int y = 7 + Settings.MARGIN_TOP - mouseInfo.getY();
            if(0 <= x && x < 8 && 0 <= y && y < 8){
                this.board.squares[x][y].onClick();
            }
        }
    }

    void start(){
        this.board = new Board(this);
        new Rook(this, board, true, 0, 0);
        new Rook(this, board, false, 0, 7);
        new Rook(this, board, false, 7, 7);
        board.resetMoves();
    }
}
