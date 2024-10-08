package team4.teambuilder.observer;
//basic interface for subject class

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
