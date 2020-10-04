package com.sincro.model.customer;

public class Customer {
	
	private String customerID;
	private String name;
	private String address;
	private CustomerType type;
	
	public Customer(String customerId, String name, String address, CustomerType type) {
		super();
		this.customerID = customerId;
		this.name = name;
		this.address = address;
		this.type = type;
	}

	public String getCustomerId() {
		return customerID;
	}

	public void setCustomerId(String customerId) {
		this.customerID = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
}
