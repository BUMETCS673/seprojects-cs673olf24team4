package team4.teambuilder.observer;
import team4.teambuilder.model.User;

public class SimpleObserver implements Observer {
	private String value;
	private Subject simpleSubject;
	private User observerName;
	
	public SimpleObserver(Subject simpleSubject, User observerName) {
		this.simpleSubject = simpleSubject;
		this.observerName = observerName;
		simpleSubject.registerObserver(this);
	}
	
	public void update(String value) {
		this.value = value;
		display();
		//When value is updated, a message is displayed
	}
	//Shows the name of the user as well as the value that they are observing is updated to
	//For this project, this value should always be in relation to what team the user is assigned to
	public void display() {
		System.out.println("Message received by " + observerName.getName()+ " : " + value);
	}
}