package chess;

import chess.moves.BaseMove;
import greenfoot.*;

import java.util.Optional;

/**
 * Represents a field on the chess board.
 * This class is used to represent the possible moves a player can make.
 */
public class Square extends Actor {

    final int x;
    final int y;
    final GreenfootImage image;
    Color standardColor;
    Optional<Color> highlightColor;
    Optional<BaseMove> move;

    /**
     * Generates a new Square. The bottom left square has coordinates (0, 0).
     * @param world the world to operate on
     * @param x the x-coordinate on the board
     * @param y the y-coordinate on the board
     */
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
        this.highlightColor = Optional.empty();
    }

    void drawImage() {
        int margin = 0;

        if(this.highlightColor.isPresent()) {
            this.image.setColor(this.highlightColor.get());
            this.image.fill();

            margin = Settings.HIGHLIGHT_MARGIN;
        }
        this.image.setColor(this.standardColor);
        this.image.fillRect(margin, margin, Settings.SQUARE_LEN - margin*2, Settings.SQUARE_LEN - margin*2);

        if(this.move.isPresent()){
            BaseMove move = this.move.get();
            this.image.setColor(move.getColor());
            margin = Math.max(margin, move.getMargin());
            this.image.fillRect(margin, margin, Settings.SQUARE_LEN - margin*2, Settings.SQUARE_LEN - margin*2);
        }

        this.setImage(image);
    }

    /**
     * Set the color to highlight this square with.
     */
    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = Optional.of(highlightColor);
        this.drawImage();
    }

    public void resetHighlightColor() {
        this.highlightColor = Optional.empty();
        this.drawImage();
    }

    /**
     * Executes the associated {@link BaseMove Move}.
     */
    public void onClick(){
        this.move.ifPresent(BaseMove::execute);
    }

    /**
     * Associate a {@link BaseMove Move} to this square and display its color.
     * @param move the move to add
     */
    public void addMove(BaseMove move){
        this.move = Optional.of(move);
        this.drawImage();
    }

    /**
     * Clear the associated {@link BaseMove Move}.
     */
    public void removeMove(){
        this.move = Optional.empty();
        this.drawImage();
    }
}
