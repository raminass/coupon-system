package coupons.core.dao;

import java.util.Collection;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.beans.Customer;
import coupons.core.exceptions.CouponSystemException;

public interface CouponDAO {
	
	public void createCoupon(Coupon coupon) throws CouponSystemException;

	public void removeCoupon(Coupon coupon) throws CouponSystemException;

	public void updateCoupon(Coupon coupon) throws CouponSystemException;

	public Coupon getCoupon(long id) throws CouponSystemException;

	public Collection<Coupon> getAllCoupons() throws CouponSystemException;

	public Collection<Coupon> getAllCouponsByType(CouponType type) throws CouponSystemException;
	public Boolean checkCouponNameExist(String name) throws CouponSystemException;

	public void deleteAllCompnayCoupons(Company company) throws CouponSystemException;
	public void deleteAllCustomerCoupons(Customer customer) throws CouponSystemException;
	
	public void updateCompanyCoupon(long couponId, long companyId) throws CouponSystemException;
	
	public long getCustomerCoupon(long couponId) throws CouponSystemException;
	public long getCompanyCoupon(long couponId) throws CouponSystemException;

	public void updateCustomerCoupon(long customerId, long couponId) throws CouponSystemException;
	
	public void removeAllExpiredCoupons() throws CouponSystemException;

}
