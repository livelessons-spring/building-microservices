package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Car {

	@Id
	@GeneratedValue
	private long id;

	private String make;

	private String model;

	private int year;

	private String color;

	Car() {
	}

	public Car(String make, String model, int year, String color) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return make + " " + model + " " + year + " " + this.color;
	}

}
