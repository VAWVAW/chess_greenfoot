package chess.moves;

import chess.Board;
import chess.pieces.*;
import greenfoot.Color;
import greenfoot.GreenfootImage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Represents a promotion choice in chess.
 */
public class PromotionMove extends ChoiceMove{
    private final Class<? extends Piece> optionClass;

    /**
     * Generates a new PromotionMove.
     * @param board the board to operate on
     * @param piece the piece to operate on
     * @param y the y-coordinate to display the choice on
     * @param option the class of the piece to promote to
        (has to implement the method `static GreenfootImage getPieceImage(int)`)
     */
    private PromotionMove(Board board, Pawn piece, int y, Class<? extends Piece> option) {
        super(board, piece, y);
        this.optionClass = option;
    }

    /**
     * Generates all PromotionMoves for a given Piece. (Queen, Rook, Bishop, Knight)
     * @param board the board to operate on
     * @param piece the piece to promote
     * @return all promotion moves in base chess
     */
    public static ArrayList<PromotionMove> generate(Board board, Pawn piece) {
        ArrayList<PromotionMove> retMoves = new ArrayList<>();

        retMoves.add(new PromotionMove(board, piece, 7, Queen.class));
        retMoves.add(new PromotionMove(board, piece, 6, Rook.class));
        retMoves.add(new PromotionMove(board, piece, 5, Bishop.class));
        retMoves.add(new PromotionMove(board, piece, 4, Knight.class));

        return retMoves;
    }

    /**
     * Swaps the old piece with a newly generated piece of the stored class.
     */
    @Override
    public void execute() {
        Piece newPiece;
        Piece oldPiece = this.piece;

        try {
            Constructor<?>[] constructors = this.optionClass.getConstructors();
            newPiece = (Piece) constructors[0].newInstance(oldPiece.getWorld(), board, oldPiece.getSide(), oldPiece.getX(), oldPiece.getY());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        oldPiece.capture();

        board.getPieces(oldPiece.getSide()).addPiece(newPiece);
        board.set(oldPiece.getX(), oldPiece.getY(), newPiece);
        board.togglePlayingSide(this);
    }

    /** {@inheritDoc} */
    @Override
    public Color getColor() {
        return new Color(151, 0, 151);
    }

    /** {@inheritDoc} */
    @Override
    public int getMargin() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public GreenfootImage getImage() {
        GreenfootImage image;
        try {
            Method m = this.optionClass.getMethod("getPieceImage", int.class);
            image = (GreenfootImage) m.invoke(null, piece.getSide());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isInvalid() {
        return this.board.getKing().isInCheck();
    }
}
