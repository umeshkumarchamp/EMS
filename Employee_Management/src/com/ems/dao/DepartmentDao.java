package com.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.ems.db.DBConnection;
import com.ems.exception.ResourceNotFoundException;

public class DepartmentDao {

		static Scanner sc = new Scanner(System.in);
		
		/**
		 * Add a New Department
		 */
		public static void addDepartment() {
			try (Connection con = DBConnection.connect()) {
				
				Statement st = con.createStatement(); 
				 
				System.out.print("Enter Department Name : ");
				String deptName = sc.nextLine();
				System.out.print("Enter total number of employees  : ");
				int noOfEmployee = sc.nextInt();
				
				String query = "select department_id from department order by department_id desc limit 1";
				ResultSet rs = st.executeQuery(query);
				
				if(rs.next())
			 	{
			 		String lastDeptId = rs.getString("department_id");
			 		String prefix = lastDeptId.substring(0,1);
			 		String postfix= lastDeptId.substring(1);
			 		int deptId = Integer.parseInt(postfix);  //Converting a string into integer
			 		String newDeptId = prefix+(deptId+1);
			 		
			 		String insert = "insert into department values ('"+newDeptId+"','"+deptName+"',"
			 				+ "'"+noOfEmployee+"')";
			 		
			 		int row	= st.executeUpdate(insert);
			 		if(row>=0) {
				 		System.out.println("Department  Added Successfully!!");
			 		}
			 	}	
			 	else {
			 		String lastDeptId = "D0";
			 		String prefix = lastDeptId.substring(0,1);
			 		String postfix= lastDeptId.substring(1);
			 		int deptId = Integer.parseInt(postfix);
			 		String newDeptId = prefix+(deptId+1);
			 		
			 		String insert = "insert into department values ('"+newDeptId+"','"+deptName+"',"
			 				+ "'"+noOfEmployee+"')";
			 		
			 		int row	= st.executeUpdate(insert);
			 		if(row>=0) {
				 		System.out.println("Department  Added Successfully!!");
			 		}
	
			 	}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Get All Department
		 */
		public static void getDepartmentList() {
			try (Connection con = DBConnection.connect()) {
				Statement st = con.createStatement(); 
				String query = "select * from department"; 
				ResultSet rs = st.executeQuery(query); 
				
				if(rs!=null) {
					System.out.println("\n<--------- Department List --------->\n");
					while(rs.next()) {
						System.out.println("Department Id : " + rs.getString("department_id"));
						System.out.println("Department Name : " + rs.getString("department_name"));
						System.out.println();
					}
				}else {
					throw new ResourceNotFoundException("Departments Not Found ! "); 
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Update  Department By Id
		 */
		public static void updateDepartment() {
		    try (Connection con = DBConnection.connect()) {
		        Statement st = con.createStatement();

		        System.out.print("Enter Department ID to update : ");
		        String deptIdToUpdate = sc.nextLine().toUpperCase();
		        
		        // Check if the department exists
		        String checkQuery = "SELECT department_id FROM department WHERE department_id = '" + deptIdToUpdate + "'";
		        ResultSet checkRs = st.executeQuery(checkQuery);

		        if (checkRs.next()) {
		        	System.out.print("Enter Department Name : ");
					String deptName = sc.nextLine();
					
					System.out.print("Enter total number of employees  : ");
					int noOfEmployee = sc.nextInt();

		            String updateQuery = "UPDATE department SET department_name = ?, total_employees = ? WHERE department_id = ?";
		            
		            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
		            updateStatement.setString(1, deptName);
		            updateStatement.setInt(2, noOfEmployee);
		            updateStatement.setString(3, deptIdToUpdate);

		            int rowsUpdated = updateStatement.executeUpdate();
		            
		            if (rowsUpdated == 1) {
		                System.out.println("Department Updated successfully!");
		            } else {
		                System.out.println("Department Update Failed.");
		            }
		        } else {
		            System.out.println("Department with ID " + deptIdToUpdate + " does not exist.");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
}
