package entity;

public class ProductOfSupplier {

	private int supplierID;
	private int productId;
	
	public ProductOfSupplier(int supplierID, int productId) {
		super();
		this.supplierID = supplierID;
		this.productId = productId;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "supplierID=" + supplierID + ", productId=" + productId;
	}
	
	
}
