package facade;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import coupons.core.beans.Coupon;
import coupons.core.beans.CouponType;
import coupons.core.dao.CompanyDAO;
import coupons.core.dao.CouponDAO;
import coupons.core.dao.CustomerDAO;
import coupons.core.db.dao.CompanyDBDAO;
import coupons.core.db.dao.CouponDBDAO;
import coupons.core.db.dao.CustomerDBDAO;
import coupons.core.exceptions.CouponSystemException;

public class CutomerFacade implements CouponClientFacade {

	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private long customerId;

	public CutomerFacade(long customerId) {
		super();
		this.customerId = customerId;
	}

	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {

		// check coupon is available at the Inventory
		if (couponDAO.getCoupon(coupon.getId()).getAmount() == 0) {

			throw new CouponSystemException("coupon not available");

		}

		// check if the coupon has been purchased
		else if (couponDAO.getCustomerCoupon(coupon.getId()) == this.customerId) {
			throw new CouponSystemException("coupon already purchased by another customer");

		}

		else if (coupon.checkCouponExpired()) {
			throw new CouponSystemException("coupon has been expired!!!");
		}

		else {

			coupon.setAmount(coupon.getAmount() - 1);
			couponDAO.updateCoupon(coupon);
			couponDAO.updateCustomerCoupon(this.customerId, coupon.getId());
			
		}

	}

	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		return customerDAO.getCoupons(this.customerId);
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		Stream<Coupon> filteredCoupons = coupons.stream().filter(p -> p.getType() == type);

		return (Collection<Coupon>) filteredCoupons;

	}

	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllPurchasedCoupons();
		Stream<Coupon> filteredCoupons = coupons.stream().filter(p -> p.getPrice() < price);

		return (Collection<Coupon>) filteredCoupons;
	}

}
