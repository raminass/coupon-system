package coupons.core.beans;

import java.util.Collection;

// java bean
/**
 * represents a customer in the system which is saved as a record in the
 * customer table
 */
public class Customer {

	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer(long id, String custName, String password, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}
	public Customer() {
		this((long)0,null,null,null);
	}
		
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}
	
	

}
