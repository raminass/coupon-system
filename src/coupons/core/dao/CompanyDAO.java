package coupons.core.dao;

import java.util.Collection;
import java.util.Date;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.exceptions.CouponSystemException;

public interface CompanyDAO {

	public void createCompany(Company company) throws CouponSystemException;

	public void removeCompany(Company company) throws CouponSystemException;

	public void updateCompany(Company company) throws CouponSystemException;

	public Company getCompany(long id) throws CouponSystemException;

	public Collection<Company> getAllCompanies() throws CouponSystemException;

	public Collection<Coupon> getCoupons(long id) throws CouponSystemException;
	public Collection<Coupon> getCouponsByType(Long id,CouponType type) throws CouponSystemException;
	public Collection<Coupon> getCouponsByPrice(Long id,double price) throws CouponSystemException;
	public Collection<Coupon> getCouponsUpToDate(Long id,Date endDate) throws CouponSystemException;

	public long login(String userName, String userPass) throws CouponSystemException;
	
	public boolean checkCompNameExist(String name) throws CouponSystemException;

}
