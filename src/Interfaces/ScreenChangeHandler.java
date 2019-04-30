package Interfaces;

public interface ScreenChangeHandler {
    public static final int SCREENA = 1;
    public static final int SCREENB = 2;

    /**Sub screens must call this to switch screen.
     * @param screen The screen to show**/
    public void switchScreen(int screen);

}
