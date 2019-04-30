package Factory;

import Enums.ChessPieceType;
import Enums.GameColor;
import javafx.scene.image.ImageView;

/**
 * A factory class for returning an ImageView node for a specified chess piece.
 *
 * @author Kevin Filanowski
 * @version April 19, 2019
 */
public class ImageFactory {

    /**
     * Default constructor for an ImageFactory.
     */
    public ImageFactory() {
    }

    /**
     * Retrieves the proper ImageView of a chess piece, given specific
     * parameters to specify which piece to return.
     *
     * @param type  - The chess piece type enumeration.
     * @param color - The color enumeration of the piece.
     * @return - An ImageView object containing the selected chess piece.
     */
    public ImageView getImage(ChessPieceType type, GameColor color) {
        // An ImageView node to return.
        ImageView i;

        switch (type) {
        case ROOK: {
            if (color == GameColor.BLACK)
                i = new ImageView("images/chessPieces/BR.png");
            else
                i = new ImageView("images/chessPieces/WR.png");
        }
            break;
        case KNIGHT: {
            if (color == GameColor.BLACK)
                i = new ImageView("images/chessPieces/BN.png");
            else
                i = new ImageView("images/chessPieces/WN.png");
        }
            break;
        case BISHOP: {
            if (color == GameColor.BLACK)
                i = new ImageView("images/chessPieces/BB.png");
            else
                i = new ImageView("images/chessPieces/WB.png");
        }
            break;
        case KING: {
            if (color == GameColor.BLACK)
                i = new ImageView("images/chessPieces/BK.png");
            else
                i = new ImageView("images/chessPieces/WK.png");
        }
            break;
        case QUEEN: {
            if (color == GameColor.BLACK)
                i = new ImageView("images/chessPieces/BQ.png");
            else
                i = new ImageView("images/chessPieces/WQ.png");
        }
            break;
        case PAWN: {
            if (color == GameColor.BLACK)
                i = new ImageView("images/chessPieces/BP.png");
            else
                i = new ImageView("images/chessPieces/WP.png");
        }
            break;
        default:
            return null;
        }
        i.setPreserveRatio(true);
        i.setSmooth(true);
        i.setCache(true);
        i.getStyleClass().add("piece");
        return i;
    }
}