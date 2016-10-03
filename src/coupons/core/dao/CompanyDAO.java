package coupons.core.dao;

import java.util.Collection;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.exceptions.CouponSystemException;

public interface CompanyDAO {

	public void createCompany(Company company) throws CouponSystemException;

	public void removeCompany(Company company) throws CouponSystemException;

	public void updateCompany(Company company) throws CouponSystemException;

	public Company getCompany(long id) throws CouponSystemException;

	public Collection<Company> getAllCompanies() throws CouponSystemException;

	public Collection<Coupon> getCoupons(long id) throws CouponSystemException;

	public long login(String userName, String userPass) throws CouponSystemException;
	
	public boolean checkCompNameExist(String name) throws CouponSystemException;

}
