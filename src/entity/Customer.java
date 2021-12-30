package entity;

import java.sql.Date;

public class Customer {
	String passportID;
	String firstName;
	String lastName;
	String email;
	Date birthDate;
	String citizenship;
	
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
	
	
	
}
