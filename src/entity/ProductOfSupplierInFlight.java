package entity;

public class ProductOfSupplierInFlight {

	private String flightId;
	private int supplierID;
	private int productId;
	private String feedback;
	
	public ProductOfSupplierInFlight(String flightId, int supplierID, int productId, String feedback) {
		super();
		this.flightId = flightId;
		this.supplierID = supplierID;
		this.productId = productId;
		this.feedback = feedback;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
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

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "flightId=" + flightId + ", supplierID=" + supplierID + ", productId="
				+ productId + ", feedback=" + feedback;
	}
	
	
}
