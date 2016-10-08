package facade;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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
import utils.*;

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
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = formatter.format(oldCoupon.getStartDate());
		String date2 = formatter.format(coupon.getStartDate());

		if (!(oldCoupon.getTitle().equals(coupon.getTitle()))) {

			throw new CouponSystemException("can't change coupon title");

		}

		else if (!(date1.equals(date2))) {

			throw new CouponSystemException("can't change coupon startDate");

		}

		else if (oldCoupon.getAmount() != coupon.getAmount()) {

			throw new CouponSystemException("can't change coupon amount");
		}

		else if (oldCoupon.getType() != coupon.getType()) {

			throw new CouponSystemException("can't change coupon Type");
		}

		else if (!(oldCoupon.getMessage().equals(coupon.getMessage()))) {

			throw new CouponSystemException("can't change coupon Message");
		}

		else if (!(compareString.compareStrings(oldCoupon.getImage(), coupon.getImage()))) {

			throw new CouponSystemException("can't change coupon Image");
		}

		else {

			couponDAO.updateCoupon(coupon);
			System.out.println("coupon updated succeffuly!");
		}

	}

	public Coupon getCoupon(long couponId) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();

		for (Iterator iterator = coupons.iterator(); iterator.hasNext();) {
			Coupon cop = (Coupon) iterator.next();
			if (cop.getId() == couponId) {

				return couponDAO.getCoupon(couponId);

			}

		}

		throw new CouponSystemException("coupon doesn't belong to this company");

	}

	public Collection<Coupon> getAllCoupon() throws CouponSystemException {

		return companyDAO.getCoupons(this.companyId);

	}

	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();

		return companyDAO.getCouponsByType(this.companyId, type);
	}

	public Collection<Coupon> getCouponUpToPrice(Double price) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();

		return companyDAO.getCouponsByPrice(this.companyId, price);
	}

	public Collection<Coupon> getCouponUpToDate(Date endDate) throws CouponSystemException {

		Collection<Coupon> coupons = this.getAllCoupon();

		return companyDAO.getCouponsUpToDate(this.companyId, endDate);

	}

}
