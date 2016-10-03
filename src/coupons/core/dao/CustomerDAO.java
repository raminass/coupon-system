package coupons.core.dao;

import java.util.Collection;

import coupons.core.beans.Coupon;
import coupons.core.beans.Customer;
import coupons.core.exceptions.CouponSystemException;

public interface CustomerDAO {

	public void createCustomer(Customer customer) throws CouponSystemException;

	public void removeCustomer(Customer customer) throws CouponSystemException;

	public void updateCustomer(Customer customer) throws CouponSystemException;

	public Customer getCustomer(long id) throws CouponSystemException;

	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	public Collection<Coupon> getCoupons(long id) throws CouponSystemException;

	public long login(String userName, String userPass) throws CouponSystemException;
	public boolean checkCustomerNameExist(String custName) throws CouponSystemException;

}
