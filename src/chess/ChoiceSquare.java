package chess;

import chess.moves.ChoiceMove;
import greenfoot.World;

/**
 * Represents a field used to display a choice the player can make.
 */
public class ChoiceSquare extends Square {
    /**
     * Generates a new ChoiceSquare. The x-coordinate is always {@link Settings#CHOICE_SQUARE_X}.
     * @param world the world to operate on
     * @param y the y-coordinate on the board
     */
    public ChoiceSquare(World world, int y) {
        super(world, Settings.CHOICE_SQUARE_X, y);

        this.standardColor = Settings.BACKGROUND_COLOR;
        this.image.setColor(this.standardColor);
        this.image.fill();
        this.setImage(image);
    }

    /**
     * Adds a ChoiceMove to the square.
     */
    public void addChoiceMove(ChoiceMove move) {
        super.addMove(move);

        this.image.drawImage(move.getImage(), 0, 0);
    }
}
