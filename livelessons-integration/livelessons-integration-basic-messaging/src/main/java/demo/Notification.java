package demo;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

	private String id;

	private final String message;

	private final Date date;

	public Notification(String id, String message, Date date) {
		this.message = message;
		this.id = id;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Notification{" + "message='" + message + '\'' + ", date=" + date + '}';
	}

}
