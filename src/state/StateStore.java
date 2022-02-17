package state;

import java.util.ArrayList;
import controllers.*;

public class StateStore implements controllers.Subject {

    private ArrayList<Observer> observers;
    private static StateStore instance;

    private State currentState;
    private Reducer<State> reducer;

    private StateStore() {
        currentState = new State();
        reducer = new PoorReducer();
        observers = new ArrayList<Observer>();
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    public void updateInValues(String action, int value) {
        currentState = reducer.reduce(currentState, action, value);

        //We need to notify all interested partys that our state has changed!
        notifyObservers();
    }

    public State getState() {
        return currentState;
    }

    public static synchronized StateStore getInstance() {
        if (instance == null) {
            instance = new StateStore();
        }

        return instance;
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
