package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import by.htp.library.bean.Order;
import by.htp.library.dao.OrderDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.ConnectionPool;
import by.htp.library.util.STATUS;

public class OrderDAOImpl implements OrderDAO {

	private static final String SQL_CREATE_ORDER = "INSERT INTO employee_book (book_id, employee_id, days, date, status) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_READ_ORDERS = "SELECT * FROM employee_book";
	private static final String SQL_READ_ORDER_BY_ID = "SELECT * FROM employee_book WHERE order_id = ?";
	private static final String SQL_READ_ORDERS_BY_STATUS = "SELECT * FROM employee_book WHERE status = ? order by order_id desc";
	private static final String SQL_READ_ORDERS_BY_EMPLOYEE_ID = "SELECT * FROM employee_book WHERE employee_id = ?";
	private static final String SQL_READ_LAST_ORDERS_BY_EMPLOYEE_ID = "SELECT * FROM employee_book WHERE employee_id = ? order by order_id desc limit ?";
	private static final String SQL_UPDATE_ORDER_BY_ID = "UPDATE employee_book SET book_id = ?, employee_id = ?, days = ?, date = ?, status = ? WHERE order_id = ?";
	private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM employee_book WHERE order_id = ?";

	@Override
	public void create(Order entity) throws DAOException {

		Connection connection = ConnectionPool.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_ORDER);

			Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+3"));
			ps.setInt(1, entity.getBookId());
			ps.setInt(2, entity.getEmplId());
			ps.setInt(3, entity.getDays());
			ps.setDate(4, Date.valueOf(LocalDate.now()), calendar);
			ps.setString(5, entity.getStatus().name());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

	}

	@Override
	public Order read(int id) throws DAOException {
		
		Connection connection = ConnectionPool.getConnection();
		Order order = new Order();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ORDER_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));
				order.setStatus(STATUS.valueOf(rs.getString("status")));

			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return order;
	}

	@Override
	public List<Order> readAll() throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Order> orders = new ArrayList<>();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_ORDERS);

			while (rs.next()) {

				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));
				order.setStatus(STATUS.valueOf(rs.getString("status")));

				orders.add(order);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return orders;
	}

	@Override
	public List<Order> readOrdersByStatus(String status) throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Order> orders = new ArrayList<>();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ORDERS_BY_STATUS);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));
				order.setStatus(STATUS.valueOf(rs.getString("status")));

				orders.add(order);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return orders;
	}

	@Override
	public List<Order> readOrdersByEmployeeId(int emplId) throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Order> orders = new ArrayList<>();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ORDERS_BY_EMPLOYEE_ID);
			ps.setInt(1, emplId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));
				order.setStatus(STATUS.valueOf(rs.getString("status")));

				orders.add(order);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return orders;
	}

	public List<Order> readLastOrdersByEmployeeId(int emplId, int count) throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Order> orders = new ArrayList<>();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_LAST_ORDERS_BY_EMPLOYEE_ID);
			ps.setInt(1, emplId);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Order order = new Order();
				order.setId(rs.getInt("order_id"));
				order.setBookId(rs.getInt("book_id"));
				order.setEmplId(rs.getInt("employee_id"));
				order.setDays(rs.getInt("days"));
				order.setDate(rs.getDate("date"));
				order.setStatus(STATUS.valueOf(rs.getString("status")));

				orders.add(order);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return orders;
	}

	@Override
	public void update(int id, Order entity) throws DAOException {
		
		Connection connection = ConnectionPool.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_ORDER_BY_ID);
			Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+3"));

			ps.setInt(1, entity.getBookId());
			ps.setInt(2, entity.getEmplId());
			ps.setInt(3, entity.getDays());
			ps.setDate(4, Date.valueOf(entity.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
					calendar);
			ps.setString(5, entity.getStatus().name());
			ps.setInt(6, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

	}

	@Override
	public void delete(int id) throws DAOException {

		Connection connection = ConnectionPool.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID);
			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);
	}

}
