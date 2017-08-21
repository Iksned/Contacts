package ConModel.services;


import concontrol.CatalogObserver;
import concontrol.Observable;

import java.util.ArrayList;
import java.util.List;

public class ObserverService implements Observable {
    private List<CatalogObserver> observers = new ArrayList<>();
    private final Object MONITOR = new Object();
    @Override
    public void notifyObserver() {
        List<CatalogObserver> tempObservers;
        synchronized (MONITOR) {
            tempObservers = new ArrayList<>(observers);
        }
            for (CatalogObserver observer : tempObservers)
                observer.update();
    }

    @Override
    public void registerObserver(CatalogObserver obs) {
        synchronized (MONITOR) {
            observers.add(obs);
        }
    }

    @Override
    public void removeObsever(CatalogObserver obs) {
        synchronized (MONITOR) {
            observers.remove(obs);
        }
    }

}
