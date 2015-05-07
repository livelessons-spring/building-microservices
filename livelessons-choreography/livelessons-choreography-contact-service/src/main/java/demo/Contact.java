package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Contact {

	@Id
	@GeneratedValue
	private Long id;

	private String userId;

	private String firstName;

	private String lastName;

	private String email;

	private String relationship;

	public Contact() {
	}

	public Contact(String userId, String firstName, String lastName, String email,
			String relationship) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.relationship = relationship;
	}

	public Long getId() {
		return id;
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

	public String getRelationship() {
		return relationship;
	}

}
