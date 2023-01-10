package chess;

import chess.moves.BaseMove;
import greenfoot.*;

import java.util.Optional;

public class Square extends Actor {

    final int x;
    final int y;
    final GreenfootImage image;
    Color standardColor;
    Optional<BaseMove> move;

    public Square(World world, int x, int y){
        this.x = x;
        this.y = y;
        this.image = new GreenfootImage(Settings.SQUARE_LEN, Settings.SQUARE_LEN);
        if((x + y) % 2 == 0) {
            this.standardColor = new Color(244, 139,71);
        }
        else{
            this.standardColor = new Color(255, 206, 159);
        }
        this.image.setColor(this.standardColor);
        this.image.fill();
        this.setImage(image);

        world.addObject(this, this.x + Settings.MARGIN_LEFT, 7 + Settings.MARGIN_TOP - this.y);
        this.move = Optional.empty();
    }

    public void onClick(){
        this.move.ifPresent(BaseMove::execute);
    }

    public void addMove(BaseMove move){
        this.move = Optional.of(move);
        this.image.setColor(move.getColor());
        int margin = move.getMargin();
        this.image.fillRect(margin, margin, Settings.SQUARE_LEN - margin*2, Settings.SQUARE_LEN - margin*2);
        this.setImage(image);
    }

    public void removeMove(){
        this.move = Optional.empty();
        this.image.setColor(this.standardColor);
        this.image.fill();
    }
}
