package Model;

import Enums.GameColor;
import Interfaces.BlackAndWhiteIF;

/**
 * Represents the two different colors to a Chess match.
 * 
 * @author Kevin Filanowski 100%
 * @version March 20, 2019
 */
public class BlackAndWhite implements BlackAndWhiteIF {
    /** A Enumeration representing the two colors to a Chess game. */
    private GameColor gameColor;

    /**
     * Retrieve the color enumeration.
     * 
     * @return - The GameColor enumeration.
     */
    @Override
    public GameColor getColor() {
        return gameColor;
    }

    /**
     * Set the GameColor to a specified GameColor.
     * 
     * @param gameColor - The GameColor to set this class's reference to.
     */
    public void setColor(GameColor gameColor) {
        this.gameColor = gameColor;
    }

    /**
     * Determine if the color is black.
     * 
     * @return - True if it is black, false if it is white.
     */
    @Override
    public boolean isBlack() {
        return gameColor == GameColor.BLACK;
    }

    /**
     * Determine if the color is white.
     * 
     * @return - True if it is white, false if it is black.
     */
    @Override
    public boolean isWhite() {
        return gameColor == GameColor.WHITE;
    }

    /**
     * Set the GameColor to black.
     */
    public void setBlack() {
        gameColor = GameColor.BLACK;
    }

    /**
     * Set the GameColor to white.
     */
    public void setWhite() {
        gameColor = GameColor.WHITE;
    }

    /**
     * Compares an object with this BlackAndWhite object.
     * 
     * @param obj - An object to compare with this BlackAndWhite object.
     * @return - True if the two objects are deeply equal, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof BlackAndWhite) {
            BlackAndWhite baw = (BlackAndWhite) obj;
            return (isWhite() && baw.isWhite()) || (isBlack() && baw.isBlack());
        }
        return false;
    }
}
