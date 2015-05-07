package demo;

public class Contact {

	private Long id;

	private String relationship;

	private String userId;

	private String firstName;

	private String lastName;

	private String email;

	public Long getId() {
		return id;
	}

	public String getRelationship() {
		return relationship;
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Contact{" + "id=" + id + ", relationship='" + relationship + '\''
				+ ", userId='" + userId + '\'' + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + '}';
	}

}
