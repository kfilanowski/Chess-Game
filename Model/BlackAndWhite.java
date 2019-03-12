package Model;

import Enums.GameColor;
import Interfaces.BlackAndWhiteIF;

/**
 * Represents the two different colors to a Chess match.
 * @author Kevin Filanowski
 * @version March 11, 2019
 */
public class BlackAndWhite implements BlackAndWhiteIF {
    private GameColor gameColor;

    @Override
    public GameColor getColor() {
        return gameColor;
    }

    public void setColor(GameColor gameColor) {
        this.gameColor = gameColor;
    }

    @Override
    public boolean isBlack() {
        return gameColor == GameColor.Black;
    }

    @Override
    public boolean isWhite() {
        return gameColor == GameColor.White;
    }

    public void setBlack(boolean isBlack){
        gameColor = GameColor.Black;
    }

    private void setWhite(boolean isWhite){
        gameColor = GameColor.White;
    }

}
