package Interfaces;

import Enums.GameColor;

/**
 * Interface that models a BlackAndWhite implementation. It uses a GameColor enum to get a color
 * and has methods to check if a color is black or white.
 */
public interface BlackAndWhiteIF {
    public GameColor getColor();

    public boolean isBlack();

    public boolean isWhite();
}
