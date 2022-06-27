package com.bilgeadam.postgresqltutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
	
	private final String url = "jdbc:postgresql://localhost/dvdrental";
	private final String user = "postgres";
	private final String password = "2211223";
	
	public Connection connect() {
		
		Connection conn = null;
	
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to PostgreSQL server successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
		}
	
	public int getActorCount() {
		
		String sql = "SELECT count(*) FROM actor";
		int count = 0;
		
		try(Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)
			) {
			rs.next();
			count = rs.getInt(1);
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		return count;
	}
	
	public void getActors() {
		String sql = "SELECT actor_id, first_name, last_name FROM actor;";
		
		try(Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			displayActors(rs);
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}

	private void displayActors(ResultSet rs) throws SQLException {
		while(rs.next()) {
			System.out.println((rs.getString("actor_id") + "-" + rs.getString("first_name") + "\t" 
			+ rs.getString("last_name")));
		}	
	}
	
	private void findActorByID(int actorId) {
		
		String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE actor_id = ?;";
		try(Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) { //soru işareti olunca prepared statement kullanılıyor.
				pstmt.setInt(1, actorId);
				ResultSet rs = pstmt.executeQuery();
				displayActors(rs);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		App app = new App();
		//app.connect();
		//System.out.println(app.getActorCount());
		//app.getActors();
		app.findActorByID(200);
	}
	
}
