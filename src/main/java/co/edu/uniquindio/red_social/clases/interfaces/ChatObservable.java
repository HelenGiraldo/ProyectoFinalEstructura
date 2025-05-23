package co.edu.uniquindio.red_social.clases.interfaces;

public interface ChatObservable {
    void addObserver(ChatObserver observer);
    void removeObserver(ChatObserver observer);
    void notifyObservers();
}
