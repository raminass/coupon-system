package coupons.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupons.core.exceptions.CouponSystemException;

public class ConnectionPool {

	private Set<Connection> cons = new HashSet<>();
	public static final int POOL_SIZE = 10;
	private String url = "jdbc:derby://localhost:1527/couponSystemDB;create=true";
	private static ConnectionPool instance = null;

	// CTOR
	private ConnectionPool() throws SQLException {

		for (int i = 0; i < POOL_SIZE; i++) {
			cons.add(DriverManager.getConnection(url));
		}
	}

	public static ConnectionPool getInstance()  throws CouponSystemException{

		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CouponSystemException("connection pool exception", e);
			}
		}
		return instance;
	}

	public synchronized Connection getConnection() {

		while (cons.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Iterator<Connection> it = cons.iterator();
		Connection con = it.next();
		it.remove();
		return con;
	}
	
	public synchronized void returnConnection(Connection con) {
		cons.add(con);
		notify();
	}
	
	public void closeAllCons() throws SQLException{
		
		for (Connection curr : cons) {
			curr.close();
		}
		
	}

}
