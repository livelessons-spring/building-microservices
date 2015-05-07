package demo;

public class Bookmark {

	private Long id;

	private String href;

	private String label;

	private String description;

	private String userId;

	public Bookmark() {
	}

	public Long getId() {
		return id;
	}

	public String getHref() {
		return href;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "Bookmark{" + "id=" + id + ", href='" + href + '\'' + ", label='" + label
				+ '\'' + ", description='" + description + '\'' + ", userId='" + userId
				+ '\'' + '}';
	}

}
