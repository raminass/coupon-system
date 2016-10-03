package facade;

import java.util.Collection;
import java.util.Date;
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

public class CompanyFacade implements CouponClientFacade {

	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	private long companyId;

	public CompanyFacade(long companyId) {
		super();
		this.companyId = companyId;
	}

	public void createCoupon(Coupon coupon) throws CouponSystemException {

		if (couponDAO.checkCouponNameExist(coupon.getTitle())) {
			throw new CouponSystemException("Coupon: " + coupon.getTitle() + "already exist");

		}

		else {

			couponDAO.createCoupon(coupon);
			couponDAO.updateCompanyCoupon(coupon.getId(), this.companyId);
		}

	}

	public void removeCoupon(Coupon coupon) throws CouponSystemException {

		couponDAO.removeCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		Coupon oldCoupon = couponDAO.getCoupon(coupon.getId());

		if (oldCoupon.getTitle() != coupon.getTitle()) {

			throw new CouponSystemException("can't change coupon title");

		}

		else if (oldCoupon.getStartDate() != coupon.getStartDate()) {

			throw new CouponSystemException("can't change coupon startDate");

		}

		else if (oldCoupon.getAmount() != coupon.getAmount()) {

			throw new CouponSystemException("can't change coupon amount");
		}

		else if (oldCoupon.getType() != coupon.getType()) {

			throw new CouponSystemException("can't change coupon Type");
		}

		else if (oldCoupon.getMessage() != coupon.getMessage()) {

			throw new CouponSystemException("can't change coupon Message");
		}

		else if (oldCoupon.getImage() != coupon.getImage()) {

			throw new CouponSystemException("can't change coupon Image");
		}

		else {

			couponDAO.updateCoupon(coupon);
			System.out.println("coupon updated succeffuly!");
		}

	}

	public Coupon getCoupon(long couponId) throws CouponSystemException {

		return couponDAO.getCoupon(couponId);

	}

	public Collection<Coupon> getAllCoupon() throws CouponSystemException {

		return companyDAO.getCoupons(this.companyId);

	}

	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();
		Stream<Coupon> filteredCoupons = coupons.stream().filter(p -> p.getType() == type);

		return (Collection<Coupon>) filteredCoupons;
	}

	public Collection<Coupon> getCouponUpToPrice(Double price) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();
		Stream<Coupon> filteredCoupons = coupons.stream().filter(p -> p.getPrice() < price);

		return (Collection<Coupon>) filteredCoupons;
	}

	public Collection<Coupon> getCouponUpToDate(Date endDate) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();
		Stream<Coupon> filteredCoupons = coupons.stream().filter(p -> p.getEndDate().compareTo(endDate) < 0);

		return (Collection<Coupon>) filteredCoupons;
	}
	
	

}
