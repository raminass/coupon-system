package z.createDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//building the database

public class BuildDBTables {

	public static void main(String[] args) {

		// loading JDBC driver
		String driverName = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driverName);
			System.out.println("driver loaded");

			// create a database (if not existed) and establish connection
			String url = "jdbc:derby://localhost:1527/couponSystemDB;create=true;";
			try (Connection con = DriverManager.getConnection(url);) {

				System.out.println("connectin to database ..." + url);

				Statement stmt = con.createStatement();

				// clean any previous tables

				try {
					String sql = "Drop table company";
					stmt.executeUpdate(sql);
					System.out.println(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "Drop table customer";
					stmt.executeUpdate(sql);
					System.out.println(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "Drop table coupon";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "Drop table company_coupon";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "Drop table customer_coupon";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				// creating the DB

				try {
					String sql = "CREATE TABLE company(id BIGINT PRIMARY KEY, comp_name VARCHAR(25), password VARCHAR(25), email VARCHAR(50))";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "CREATE TABLE customer(id BIGINT PRIMARY KEY, cust_name VARCHAR(25), password VARCHAR(25))";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				// ENUM('electronic', 'food','fun')

				try {
					String sql = "CREATE TABLE coupon(id BIGINT PRIMARY KEY, title VARCHAR(25), start_date DATE, end_date DATE, amount INTEGER, type INT, message VARCHAR(25), price Double, image VARCHAR(200))";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "CREATE TABLE company_coupon(comp_id BIGINT, coupon_id BIGINT, PRIMARY KEY (comp_id, coupon_id))";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					String sql = "CREATE TABLE customer_coupon(cust_id BIGINT, coupon_id BIGINT, PRIMARY KEY (cust_id, coupon_id))";
					stmt.executeUpdate(sql);
					System.out.println(sql);

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("disconnect from " + url);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
