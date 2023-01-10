package chess.moves;

import chess.Board;
import chess.pieces.*;
import greenfoot.Color;
import greenfoot.GreenfootImage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class PromotionMove extends ChoiceMove{
    private final Class<? extends Piece> optionClass;

    private PromotionMove(Board board, Piece piece, int y, Class<? extends Piece> option) {
        super(board, piece, y);
        this.optionClass = option;
    }

    public static ArrayList<PromotionMove> generate(Board board, Piece piece) {
        ArrayList<PromotionMove> retMoves = new ArrayList<>();

        retMoves.add(new PromotionMove(board, piece, 7, Queen.class));
        retMoves.add(new PromotionMove(board, piece, 6, Rook.class));
        retMoves.add(new PromotionMove(board, piece, 5, Bishop.class));
        retMoves.add(new PromotionMove(board, piece, 4, Knight.class));

        return retMoves;
    }

    @Override
    public void execute() {
        Piece newPiece;
        // generate new piece
        try {
            Constructor<?>[] constructors = this.optionClass.getConstructors();
            newPiece = (Piece) constructors[0].newInstance(piece.getWorld(), board, piece.getSide(), piece.getX(), piece.getY());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // remove old piece
        piece.capture();
        board.del(piece.getX(), piece.getY());

        // add new piece
        board.togglePlayingSide(this);
        board.set(piece.getX(), piece.getY(), newPiece);

        board.getPieces(piece.getSide()).addPiece(newPiece);

    }

    @Override
    public Color getColor() {
        return new Color(151, 0, 151);
    }

    @Override
    public int getMargin() {
        return 0;
    }

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
}
