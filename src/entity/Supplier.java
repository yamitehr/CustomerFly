package entity;

public class Supplier {

	int SupplierID;
	String Name;
	String PhoneNumber;
	String Email;
	String Fax;

	public Supplier(int supplierID, String name, String phoneNumber, String email, String fax) {
		super();
		SupplierID = supplierID;
		Name = name;
		PhoneNumber = phoneNumber;
		Email = email;
		Fax = fax;
	}

	public int getSupplierID() {
		return SupplierID;
	}

	public void setSupplierID(int supplierID) {
		SupplierID = supplierID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	@Override
	public String toString() {
		return "Supplier [SupplierID=" + SupplierID + ", Name=" + Name + ", PhoneNumber=" + PhoneNumber + ", Email="
				+ Email + ", Fax=" + Fax + "]";
	}

}
