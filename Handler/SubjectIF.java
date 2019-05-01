package Handler;

public interface SubjectIF {
    public void addObserver(SettingsObserver observer);
    public void removeObserver(SettingsObserver observer);
    public void notifyGame();
}
