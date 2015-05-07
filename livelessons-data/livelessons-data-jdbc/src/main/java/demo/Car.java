package demo;

public class Car {

	private long id;

	private String make;

	private String model;

	private int year;

	Car(long id, String make, String model, int year) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.year = year;
	}

	long getId() {
		return id;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return make + " " + model + " " + year;
	}

}
