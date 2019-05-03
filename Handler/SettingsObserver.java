package Handler;

import javafx.scene.layout.Background;

public interface SettingsObserver {


    public void moveUpdate(boolean show);

    public void undoUpdate(boolean undo);

    public void maxundoUpdate(int numUndo);

    public void unlimUpdate(boolean unlimUndo);

    public void colorUpdate(Background white, Background black);
}
