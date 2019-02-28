package Model;

import Enums.GameColor;
import Interfaces.BlackAndWhiteIF;

public class BlackAndWhite implements BlackAndWhiteIF {
    private GameColor gameColor;

    @Override
    public GameColor getColor() {
        return null;
    }

    public void setColor(GameColor gameColor) {
        this.gameColor = gameColor;
    }

    @Override
    public boolean isBlack() {
        return false;
    }

    @Override
    public boolean isWhite() {
        return false;
    }

    public void setBlack(boolean isBlack){

    }

    private void setWhite(boolean isWhite){

    }


}
