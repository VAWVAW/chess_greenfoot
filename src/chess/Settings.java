package chess;

import greenfoot.Color;

/**
 * Some global and static setting applied at compile time.
 */
public class Settings {
    /** The edge length of single squares. */
    public static final int SQUARE_LEN = 64;

    /** The number of squares to leave at the left of the chess board. */
    public static final int MARGIN_LEFT = 3;

    /** The number of squares to leave at the right of the chess board. */
    public static final int MARGIN_RIGHT = 3;

    /** The number of squares to leave at the top of the chess board. */
    public static final int MARGIN_TOP = 2;

    /** The number of squares to leave at the bottom of the chess board. */
    public static final int MARGIN_BOTTOM = 1;

    /** The width of a highlight at the edges of a {@link Square}. */
    public static final int HIGHLIGHT_MARGIN = 6;

    /** The background color of the world. */
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    /** The x-coordinate of the {@link ChoiceSquare ChoiceSquares} relative to the chess board. */
    public static final int CHOICE_SQUARE_X = -2;
}
