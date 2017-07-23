package concontrol;

public interface Observable {

    public void notifyObserver();
    public void registerObserver(CatalogObserver obs);
}
