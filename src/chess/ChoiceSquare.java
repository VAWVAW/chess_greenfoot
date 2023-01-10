package chess;

import chess.moves.ChoiceMove;
import greenfoot.World;

public class ChoiceSquare extends Square {
    public ChoiceSquare(World world, int y) {
        super(world, Settings.CHOICE_SQUARE_X, y);

        this.standardColor = Settings.BACKGROUND_COLOR;
        this.image.setColor(this.standardColor);
        this.image.fill();
        this.setImage(image);
    }

    public void addChoiceMove(ChoiceMove move) {
        super.addMove(move);

        this.image.drawImage(move.getImage(), 0, 0);
    }
}
