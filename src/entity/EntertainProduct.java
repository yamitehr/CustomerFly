package entity;

import util.ProductType;

public class EntertainProduct {

	int ProductID;
	String Name;
	String Description;
	ProductType Type;

	public EntertainProduct(int productID, String name, String description, ProductType type) {
		super();
		ProductID = productID;
		Name = name;
		Description = description;
		Type = type;
	}

	public int getProductID() {
		return ProductID;
	}

	public void setProductID(int productID) {
		ProductID = productID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public ProductType getType() {
		return Type;
	}

	public void setType(ProductType type) {
		Type = type;
	}

	@Override
	public String toString() {
		return "EntertainProduct [ProductID=" + ProductID + ", Name=" + Name + ", Description=" + Description
				+ ", Type=" + Type + "]";
	}

}
