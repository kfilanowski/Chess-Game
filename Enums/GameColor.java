package Enums;

/**
 * Enum that represents the Black and White colors in our chess game
 *
 * @author Matt Lutz 100%
 * @version March 20, 2019
 */
public enum GameColor {
    /** Enum for Black */
    BLACK,

    /** Enum for White */
    WHITE;

    /**
     * Gets the color that matches the supplied string
     * @param str the string to match to a color
     * @return the color that the string matches to or null if it did not match
     */
    public static GameColor getColorFromString(String str){
        str = str.trim();
        if(str.equalsIgnoreCase("black")){
            return BLACK;
        }else if(str.equalsIgnoreCase("white")){
            return WHITE;
        }
        return null;
    }
}
