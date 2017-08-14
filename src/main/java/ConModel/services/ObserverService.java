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
        for(CatalogObserver observer:observers)
        observer.update();
    }

    @Override
    public void registerObserver(CatalogObserver obs) {
        observers.add(obs);
    }

    @Override
    public void removeObsever(CatalogObserver obs) {
        observers.remove(obs);
    }

}
