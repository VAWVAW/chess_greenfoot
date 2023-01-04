package chess;

import chess.moves.BaseMove;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.Optional;

public class Square extends Actor {

    final int x;
    final int y;
    final GreenfootImage image;
    final Color standardColor;
    //noinspection
    Optional<BaseMove> move;

    public Square(World world, int x, int y){
        this.x = x;
        this.y = y;
        this.image = new GreenfootImage(Settings.SQUARE_LEN, Settings.SQUARE_LEN);
        if((x + y) % 2 == 0) {
            this.standardColor = Color.BLACK;
        }
        else{
            this.standardColor = Color.LIGHT_GRAY;
        }
        this.image.setColor(this.standardColor);
        this.image.fill();
        this.setImage(image);

        world.addObject(this, this.x + Settings.MARGIN_LEFT, 7 + Settings.MARGIN_TOP - this.y);
        this.move = Optional.empty();
    }

    public void addMove(BaseMove move){
        this.move = Optional.of(move);
        this.image.setColor(move.getColor());
        this.image.fill();
    }
}
