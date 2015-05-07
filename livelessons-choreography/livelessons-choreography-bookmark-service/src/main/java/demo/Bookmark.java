package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bookmark {

	@Id
	@GeneratedValue
	private Long id;

	private String userId;

	private String href;

	private String description;

	private String label;

	Bookmark() {
	}

	public Bookmark(String userId, String href, String description, String label) {
		this.userId = userId;
		this.href = href;
		this.description = description;
		this.label = label;
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getDescription() {
		return description;
	}

	public String getHref() {
		return href;
	}

	public String getLabel() {
		return label;
	}

}
