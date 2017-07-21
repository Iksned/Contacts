package concontrol;

public interface Observable {

    public void notifyObserver();
    public void addObserver(CatalogObserver obs);
}
