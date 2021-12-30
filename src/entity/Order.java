package entity;

import java.sql.Date;

import util.PaymentMethod;

public class Order {
	private int orderID;
	private Date orderDate;
	PaymentMethod paymentMethod;
	
	public Order(int orderID, Date orderDate, PaymentMethod paymentMethod) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.paymentMethod = paymentMethod;
	}
	public Order(int orderID) {
		super();
		this.orderID = orderID;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
