import chess.Settings;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;

import chess.Board;

public class GameWorld extends World {

    final Board board;

    public GameWorld() {
        super(
            8 + Settings.MARGIN_LEFT + Settings.MARGIN_RIGHT,
            8 + Settings.MARGIN_TOP + Settings.MARGIN_BOTTOM,
            Settings.SQUARE_LEN
        );
        this.board = new Board(this);
        Greenfoot.start();
    }

    @Override
    public void act(){
        if(!board.isActive()){
            this.showText((board.getPlayingSide() == 1? "Light": "Dark") + " won", 7, 1);
            Greenfoot.stop();
            return;
        }
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if(mouseInfo != null && mouseInfo.getButton() == 1 && mouseInfo.getClickCount() > 0){
            int x = mouseInfo.getX() - Settings.MARGIN_LEFT;
            int y = 7 + Settings.MARGIN_TOP - mouseInfo.getY();
            if(0 <= x && x < 8 && 0 <= y && y < 8){
                this.board.squares[x][y].onClick();
            } else if (x == Settings.CHOICE_SQUARE_X && 0 <= y && y < 8) {
                this.board.choiceSquares[y].onClick();
            }
        }
    }
}
