package com.gudperna.dao.impl;

import com.gudperna.dao.UserDAO;
import com.gudperna.model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {
	
	private Connection connection;

	public UserDAOImpl(Connection connection) {
		this.connection = connection;
	}

	public ArrayList<User> getAll() {
		ArrayList<User> result = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;


 		try {
 			if(connection != null) {
	 			stmt = connection.createStatement();
	 			rs = stmt.executeQuery("SELECT * FROM users");
	 			while(rs.next()) {
	 				User user = new User();
	 				user.setId(rs.getInt("id"));
	 				user.setEmail(rs.getString("email"));
	 				user.setPassword(rs.getString("password"));
	 				user.setName(rs.getString("name"));
	 				result.add(user);
	 			}
 			}
 		} catch (SQLException ex) {
 			Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 		} finally {
 			if(connection != null) try {
 				connection.close();
 			} catch (SQLException ex) {
 				Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 			}
 
 			if(stmt != null) try {
 				stmt.close();
 			} catch (SQLException ex) {
 				Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 			}
 
 			if(rs != null) try {
 				rs.close();
 			} catch (SQLException ex) {
 				Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 			}
 		}

		return result;
	}

	@Override
	public User getById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = new User();
		try {
			ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				user.setId(id);
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
			}
		} catch(SQLException ex) {
			Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
	 	}

		return user;
	}

	private int getLastId() {
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT id FROM users ORDER BY id DESC LIMIT 1");
			if(rs.next()) {
				result = rs.getInt("id");
				result += 1;
			} else {
				result = 1;
			}
		} catch(SQLException ex) {
			Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

		return result;
	}

	@Override
	public User prepareFormInsert() {
		User user = new User();

		user.setId(getLastId());
		user.setEmail("");
		user.setPassword("");
		user.setName("");

		return user;
	}

	@Override
	public User prepareFormEdit(int id) {
		User user = getById(id);
		return user;
	}

	@Override
	public void insert(User user) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO users(id, email, password, name) values(?,?,?,?)");
			ps.setInt(1, user.getId());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getName());
			ps.executeUpdate();
			connection.commit();
		} catch(SQLException ex) {
			Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void edit(User user) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE users SET email=?, password=?, name=? WHERE id=?");
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getId());
			ps.executeUpdate();
			connection.commit();
		} catch(SQLException ex) {
			Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void delete(int id) {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("DELETE FROM users WHERE id="+id);
			connection.commit();
		} catch(SQLException ex) {
			Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
 			if(connection != null) try {
 				connection.close();
 			} catch (SQLException ex) {
 				Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 			}
 
 			if(stmt != null) try {
 				stmt.close();
 			} catch (SQLException ex) {
 				Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
 			}
 		}
	}
}