package com.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.ems.db.DBConnection;
import com.ems.exception.ResourceNotFoundException;

public class GuestDao {
	
		static Scanner sc = new Scanner(System.in);

		/**
		 * Get Employee By Id
		 */
		public static void getEmployeeById() {
			try (Connection con = DBConnection.connect()) {
				System.out.print("Enter Your Employee Id : ");
				int empId = sc.nextInt();
				
				String query = "SELECT emp_id, full_name, designation, department, email, mobile  FROM employee WHERE emp_id = ?"; 
				
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt(1, empId);
				ResultSet rs = pstmt.executeQuery(); 
				if(rs.next()) {
					System.out.println("\n<--------- Employee Details  --------->\n");
					System.out.println("Employee Id : " + rs.getInt("emp_id"));
					System.out.println("Full Name : " + rs.getString("full_name"));
					System.out.println("Designation : " + rs.getString("designation"));
					System.out.println("Department : " + rs.getString("department"));
					System.out.println("Email Id : " + rs.getString("email"));
					System.out.println("Mobile : " + rs.getLong("mobile"));
					System.out.println();
				}else {
					throw new ResourceNotFoundException("Employees Not Found ! "); 
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Get Department By Id
		 */
		public static void getDepartmentById() {
			
			try (Connection con = DBConnection.connect()) {
				System.out.print("Enter Department Id : ");
				String deptId = sc.nextLine().toUpperCase();
				
				String query = "SELECT department_id, department_name, total_employees  FROM department WHERE department_id = ?"; 
				
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, deptId);
				ResultSet rs = pstmt.executeQuery(); 
				if(rs.next()) {
					System.out.println("\n<--------- Department Details  --------->\n");
					System.out.println("Department Id : " + rs.getString("department_id"));
					System.out.println("Department Name : " + rs.getString("department_name"));
					System.out.println("Total No. of employees : " + rs.getInt("total_employees"));
					System.out.println();
				}else {
					throw new ResourceNotFoundException("Department Not Found ! "); 
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * Get Project By Id
		 */
		public static void getProjectById() {
			
			try (Connection con = DBConnection.connect()) {
				System.out.print("Enter Project Id : ");
				int projectId = sc.nextInt();
				
				String query = "SELECT project_id, project_name, project_description  FROM project WHERE project_id = ?"; 
				
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt(1, projectId);
				ResultSet rs = pstmt.executeQuery(); 
				if(rs.next()) {
					System.out.println("\n<--------- Project Details  --------->\n");
					System.out.println("Project Id : " + rs.getInt("project_id"));
					System.out.println("Project : " + rs.getString("project_name"));
					System.out.println("Description : " + rs.getString("project_description"));
					System.out.println();
				}else {
					throw new ResourceNotFoundException("Project Not Found ! "); 
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
}
