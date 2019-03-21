package Interfaces;

import Enums.GameColor;

/**
 * @author Matt Lutz
 * Interface that models a BlackAndWhite implementation. It uses a GameColor enum to get a color
 * and has methods to check if a color is black or white.
 */
public interface BlackAndWhiteIF {
    /**
     * Retrieve the color enumeration.
     *
     * @return - The GameColor enumeration.
     */
    public GameColor getColor();

    /**
     * Determine if the color is black.
     *
     * @return - True if it is black, false if it is white.
     */
    public boolean isBlack();

    /**
     * Determine if the color is white.
     *
     * @return - True if it is white, false if it is black.
     */
    public boolean isWhite();
}
