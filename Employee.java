package com.test.firstConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectionDemo2 {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "system";
	private static final String PASSWORD = "1022";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("1.insertEmployee\n2.readEmployees\n3.updateEmployee\n4.deleteEmployee");
		System.out.println("Enter Your Choice??");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter Your Name: ");
			sc.nextLine();
			String name = sc.nextLine();
			// sc.nextLine();
			System.out.println("Enter Your Salary: ");
			double salary = sc.nextDouble();
			insertEmployee(name, salary);
			main(args);
			break;
		case 2:
			readEmployees();
			main(args);
			break;
		case 3:
			System.out.println("Enter Your ID: ");
			int id1 = sc.nextInt();
			System.out.println("Enter Your Name: ");
			String name1 = sc.next();
			System.out.println("Enter Your Salary: ");
			double salary1 = sc.nextDouble();
			updateEmployee(id1, name1, salary1);
			main(args);
			break;
		case 4:
			System.out.println("Enter Your ID: ");
			int id2 = sc.nextInt();
			deleteEmployee(id2);
			main(args);
			break;
		default:
			System.out.println("Not Found This Option ❌❌❌");
		}
	}

	public static void insertEmployee(String name, double salary) {
		// String sql = "INSERT INTO employees (name, salary) VALUES (?, ?)";
		String sql = "insert into employees(name,salary) values(?,?)";
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setDouble(2, salary);
			pstmt.executeUpdate();
			System.out.println("Employee Add Successfylly");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void readEmployees() {
		String sql = "select * from employees";
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			System.out.println("ID | NAME | SALARY");
			while (rs.next()) {
				System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getDouble("salary"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmployee(int id, String name, double salary) {
		String sql = "update employees set name=?, salary=? where id=?";
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setDouble(2, salary);
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Employee updated successfully!" : "Employee not found!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteEmployee(int id) {
		String sql = "delete from employees where id=?";
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();
			System.out.println(rows > 0 ? "Employee deleted successfully!" : "Employee not found!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
