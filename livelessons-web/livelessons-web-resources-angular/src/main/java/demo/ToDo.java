package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDo {

	@JsonProperty("_id")
	private int id;

	private final String text;

	@JsonCreator
	public ToDo(@JsonProperty("text") String text) {
		this.text = text;
	}

	private ToDo(String text, int id) {
		this.text = text;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return this.text;
	}

	public ToDo withId(int id) {
		return new ToDo(this.text, id);
	}

}
