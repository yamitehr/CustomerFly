package entity;

import java.sql.Date;

public class Customer {
	String passportID;
	String firstName;
	String lastName;
	String email;
	Date birthDate;
	String citizenship;
	String password;

	public Customer(String passportID, String firstName, String lastName, String email, Date birthDate,
			String citizenship, String password) {
		super();
		this.passportID = passportID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.citizenship = citizenship;
		this.password = password;
	}

	public Customer(String passportID, String firstName, String lastName, String email, Date birthDate,
			String citizenship) {
		super();
		this.passportID = passportID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDate = birthDate;
		this.citizenship = citizenship;
	}

	public Customer(String passportID, String password) {
		super();
		this.passportID = passportID;
		this.password = password;
	}

	public Customer(String passportID) {
		super();
		this.passportID = passportID;
	}

	public String getPassportID() {
		return passportID;
	}

	public void setPassportID(String passportID) {
		this.passportID = passportID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passportID == null) ? 0 : passportID.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (passportID == null) {
			if (other.passportID != null)
				return false;
		} else if (!passportID.equals(other.passportID))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [passportID=" + passportID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", birthDate=" + birthDate + ", citizenship=" + citizenship + "]";
	}

}
