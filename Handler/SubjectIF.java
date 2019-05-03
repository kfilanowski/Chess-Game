package Handler;

public interface SubjectIF {
    public void addObserver(SettingsObserver observer);

    public void removeObserver(SettingsObserver observer);


    public void showNotify(boolean show);

    public void undoNotify(boolean undo);

    public void maxundoNotify(int numUndo);

    public void unlimNotify(boolean unlimUndo);

    public void colorNotify();
}