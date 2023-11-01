package com.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

import com.ems.db.DBConnection;
import com.ems.exception.ResourceNotFoundException;

public class EmployeeDao {

		static Scanner sc = new Scanner(System.in);
		
		/**
		 * Add a New Employee
		 */
		public static void addEmployee() {
			try (Connection con = DBConnection.connect()) {
				
				Statement st = con.createStatement(); 
				 
				System.out.print("Enter Employee  Name : ");
				String empName = sc.nextLine();
				
				System.out.print("Enter Designation  : ");
				String designation = sc.nextLine();
				
				System.out.print("Enter Department  Name : ");
				String department = sc.nextLine();
				
				System.out.print("Enter Email Id  : ");
				String email = sc.nextLine();
				
				System.out.print("Enter Password : ");
				String password = sc.nextLine();
				
				System.out.print("Enter Mobile Number : ");
				Long  mobile = sc.nextLong();
				
				System.out.print("Enter date of joining (yyyy-MM-dd): ");
				String dateStr = sc.next();
				LocalDate doj = LocalDate.parse(dateStr);
				
				System.out.print("Enter Role Id  : ");
				int roleId = sc.nextInt();
				
				String query = "select emp_id from employee order by emp_id desc limit 1";
				ResultSet rs = st.executeQuery(query);
				
				if(rs.next())
			 	{
			 		int empId = rs.getInt("emp_id");
			 		int newEmpId = empId+1;
			 		
			 		String insert = "insert into employee values ('"+newEmpId+"','"+empName+"',"
			 				+ "'"+designation+"' , '"+department+"', '"+email+"', '"+mobile+"', '"+doj+"', '"+roleId+"', '"+password+"')";
			 		
			 		int row	= st.executeUpdate(insert);
			 		if(row==1) {
				 		System.out.println("Employee  Added Successfully!!");
			 		}
			 	}	
			 	else {
			 		int lastEmpId = 0;
			 		int newEmpId = lastEmpId+1;
			 		
			 		String insert = "insert into employee values ('"+newEmpId+"','"+empName+"',"
			 				+ "'"+designation+"' , '"+department+"', '"+email+"', '"+mobile+"', '"+doj+"', '"+roleId+"', '"+password+"')";
			 		
			 		int row	= st.executeUpdate(insert);
			 		if(row==1) {
				 		System.out.println("Employee  Added Successfully!!");
			 		}
	
			 	}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Get All Employees
		 */
		public static void getEmployeeList() {
			try (Connection con = DBConnection.connect()) {
				Statement st = con.createStatement(); 
				String query = "select * from employee"; 
				ResultSet rs = st.executeQuery(query); 
				if(rs != null) {
					System.out.println("\n<--------- Employee List --------->\n");
					while(rs.next()) {
						System.out.println("Employee Id : " + rs.getInt("emp_id"));
						System.out.println("Full Name : " + rs.getString("full_name"));
						System.out.println("Designation : " + rs.getString("designation"));
						System.out.println("Department : " + rs.getString("department"));
						System.out.println("Email Id : " + rs.getString("email"));
						System.out.println("Mobile : " + rs.getLong("mobile"));
						System.out.println();
					}
				}else {
					throw new ResourceNotFoundException("Employees Not Found ! "); 
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Update Employee Information
		 */
		public static void updateEmployee() {
		    try (Connection con = DBConnection.connect()) {
		        Statement st = con.createStatement();

		        System.out.print("Enter Employee ID to update: ");
		        int empIdToUpdate = sc.nextInt();

		        // Check if the employee exists
		        String checkQuery = "SELECT emp_id FROM employee WHERE emp_id = " + empIdToUpdate;
		        ResultSet checkRs = st.executeQuery(checkQuery);

		        if (checkRs.next()) {
		        	System.out.print("Enter Employee  Name : ");
					String empName = sc.nextLine();
					sc.nextLine();
					System.out.print("Enter Designation  : ");
					String designation = sc.nextLine();
					
					System.out.print("Enter Department Name: ");
		            String department = sc.nextLine();

		            System.out.print("Enter Email Id: ");
		            String email = sc.nextLine();

		            System.out.print("Enter Password: ");
		            String password = sc.nextLine();

		            System.out.print("Enter Mobile Number: ");
		            Long mobile = sc.nextLong();

		            System.out.print("Enter Date of Joining (yyyy-MM-dd): ");
		            String dateStr = sc.next();
		            LocalDate doj = LocalDate.parse(dateStr);

		            System.out.print("Enter Role Id: ");
		            int roleId = sc.nextInt();

		            String updateQuery = "UPDATE employee SET full_name = ?, designation = ?, " +
		                                 "department = ?, email = ?, password = ?, mobile = ?, " +
		                                 "doj = ?, role_id = ? WHERE emp_id = ?";
		            
		            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
		            updateStatement.setString(1, empName);
		            updateStatement.setString(2, designation);
		            updateStatement.setString(3, department);
		            updateStatement.setString(4, email);
		            updateStatement.setString(5, password);
		            updateStatement.setLong(6, mobile);
		            updateStatement.setObject(7, doj);
		            updateStatement.setInt(8, roleId);
		            updateStatement.setInt(9, empIdToUpdate);

		            int rowsUpdated = updateStatement.executeUpdate();
		            
		            if (rowsUpdated == 1) {
		                System.out.println("Employee Information Updated successfully!");
		            } else {
		                System.out.println("Employee Update Failed.");
		            }
		        } else {
		            System.out.println("Employee with ID " + empIdToUpdate + " does not exist.");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

}
