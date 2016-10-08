package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.text.html.HTMLDocument.Iterator;

import coupons.core.beans.Company;
import coupons.core.beans.Coupon;
import coupons.core.beans.Customer;
import coupons.core.dao.CompanyDAO;
import coupons.core.dao.CouponDAO;
import coupons.core.dao.CustomerDAO;
import coupons.core.db.dao.CompanyDBDAO;
import coupons.core.db.dao.CouponDBDAO;
import coupons.core.db.dao.CustomerDBDAO;
import coupons.core.exceptions.CouponSystemException;

public class AdminFacade implements CouponClientFacade {

	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private CouponDAO couponDAO = new CouponDBDAO();

	public AdminFacade() {
		super();

	}

	public void createCompany(Company company) throws CouponSystemException {

		if (companyDAO.checkCompNameExist(company.getCompName())) {
			throw new CouponSystemException("Company: " + company.getCompName() + "already exist");

		}

		else {

			companyDAO.createCompany(company);
		}

	}

	public void removeCompany(Company company) throws CouponSystemException {

		companyDAO.removeCompany(company);
		couponDAO.deleteAllCompnayCoupons(company);

	}

	public void updateCompany(Company company) throws CouponSystemException {

		if ((companyDAO.getCompany(company.getId())).getCompName().equals(company.getCompName())) {

			companyDAO.updateCompany(company);
		}

		else {
			throw new CouponSystemException("can't change company name");
		}

	}

	public Company getCompany(long id) throws CouponSystemException {

		return companyDAO.getCompany(id);

	}

	public Collection<Company> getAllCompanies() throws CouponSystemException {

		return companyDAO.getAllCompanies();

	}

	public void createCustomer(Customer customer) throws CouponSystemException {

		if (customerDAO.checkCustomerNameExist(customer.getCustName())) {
			throw new CouponSystemException("Customer: " + customer.getCustName() + "already exist");
		}

		else {

			customerDAO.createCustomer(customer);
		}

	}

	public void removeCustomer(Customer customer) throws CouponSystemException {

		customerDAO.removeCustomer(customer);
		couponDAO.deleteAllCustomerCoupons(customer);

	}

	public void updateCustomer(Customer customer) throws CouponSystemException {

		if ((customerDAO.getCustomer(customer.getId())).getCustName().equals(customer.getCustName())) {

			customerDAO.updateCustomer(customer);
		}

		else {
			throw new CouponSystemException("can't change customer name");
		}

	}

	public Customer getCustomer(long id) throws CouponSystemException {

		return customerDAO.getCustomer(id);
	}

	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return customerDAO.getAllCustomers();

	}

}