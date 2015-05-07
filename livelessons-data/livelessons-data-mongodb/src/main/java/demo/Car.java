package demo;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Car {

	@Id
	private BigInteger id;

	private String make;

	private String model;

	private int year;

	@GeoSpatialIndexed(name = "position")
	private Point position;

	Car() {
	}

	public Car(String make, String model, int year, Point position) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.position = position;
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
