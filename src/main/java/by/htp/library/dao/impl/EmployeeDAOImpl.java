package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.bean.Employee;
import by.htp.library.dao.EmployeeDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.ConnectionPool;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static final String SQL_CREATE_EMPLOYEE = "INSERT INTO employee (name, surname, year) VALUES (?, ?, ?)";
	private static final String SQL_READ_EMPLOYEES = "SELECT * FROM employee";
	private static final String SQL_READ_EMPLOYEES_BY_NAME_SURNAME = "SELECT emp_id, name, surname FROM employee WHERE (CONCAT(surname, ' ', name) LIKE ?) OR (CONCAT(name, ' ', surname) LIKE ?)";
	private static final String SQL_READ_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE emp_id = ?";
	private static final String SQL_READ_ID_BY_EMPLOYEE = "SELECT emp_id FROM employee WHERE name = ? AND surname = ? AND year = ?";
	private static final String SQL_READ_EMPLOYEES_BY_SURNAME = "SELECT * FROM employee WHERE surname = ?";
	private static final String SQL_UPDATE_EMPLOYEE_BY_ID = "UPDATE employee SET name = ?, surname = ?, year = ?  WHERE emp_id = ?";
	private static final String SQL_DELETE_EMPLOYEE_BY_ID = "DELETE FROM employee WHERE emp_id = ?";

	public void create(Employee entity) throws DAOException {

		Connection connection = ConnectionPool.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_EMPLOYEE);

			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());
			ps.setInt(3, entity.getYear());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

	}

	public List<Employee> readAll() throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Employee> employees = new ArrayList<>();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_EMPLOYEES);

			while (rs.next()) {

				Employee employee = new Employee();
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setYear(rs.getInt("year"));

				employees.add(employee);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return employees;
	}

	public List<Employee> readByNameSurname(String fullName) throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Employee> employees = new ArrayList<>();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEES_BY_NAME_SURNAME);
			ps.setString(1, "%" + fullName + "%");
			ps.setString(2, "%" + fullName + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Employee employee = new Employee();
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));

				employees.add(employee);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return employees;
	}

	public Employee read(int id) throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		Employee employee = new Employee();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEE_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setYear(rs.getInt("year"));
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return employee;
	}

	@Override
	public int readIdEmployee(Employee employee) throws DAOException {
		
		Connection connection = ConnectionPool.getConnection();
		int id = 0;

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_ID_BY_EMPLOYEE);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getSurname());
			ps.setInt(3, employee.getYear());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				id = rs.getInt("emp_id");
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return id;
	}

	public List<Employee> readBySurname(String surname) throws DAOException {

		Connection connection = ConnectionPool.getConnection();
		List<Employee> employees = new ArrayList<>();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEES_BY_SURNAME);
			ps.setString(1, surname);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("name"));
				employee.setSurname(rs.getString("surname"));
				employee.setYear(rs.getInt("year"));

				employees.add(employee);
			}

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);

		return employees;
	}

	public void update(int id, Employee entity) throws DAOException {

		Connection connection = ConnectionPool.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_EMPLOYEE_BY_ID);

			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());
			ps.setInt(3, entity.getYear());
			ps.setInt(4, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);
	}

	public void delete(int id) throws DAOException {

		Connection connection = ConnectionPool.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_DELETE_EMPLOYEE_BY_ID);
			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("DAO error", e);
		}

		ConnectionPool.putConnection(connection);
	}
}
