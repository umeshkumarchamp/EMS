package com.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.ems.db.DBConnection;
import com.ems.exception.ResourceNotFoundException;

public class ProjectDao {
	
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Add a New Project
	 */
	public static void addProject() {
		try (Connection con = DBConnection.connect()) {
			
			Statement st = con.createStatement(); 
			 
			System.out.print("Enter Project Name: ");
        	String projectName = sc.nextLine();

        	sc.nextLine();

        	System.out.print("Enter Project Description: ");
        	String description = sc.nextLine();
				 		
			String query = "select project_id from project order by project_id desc limit 1";
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next())
		 	{
		 		int pId = rs.getInt("project_id");
		 		int newProjectId = pId+1;
		 		String insert = "insert into project  values ('"+newProjectId+"','"+projectName+"','"+description+"')";
		 		int row	= st.executeUpdate(insert);
		 		if(row==1) {
			 		System.out.println("Project  Added Successfully!!");
		 		}else {
		 			System.out.println("Record Not Added ! ");
		 		}
		 	}else {
		 		int lastPId = 0;
		 		int newProjectId = lastPId+1;
		 		String insert = "insert into project  values ('"+newProjectId+"','"+projectName+"','"+description+"')";
		 		int row	= st.executeUpdate(insert);
		 		if(row==1) {
			 		System.out.println("Project  Added Successfully!!");
		 		}else {
		 			System.out.println("Record Not Added ! ");
		 		}
		 	}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get All Project
	 */
	public static void getProjectList() {
		try (Connection con = DBConnection.connect()) {
			Statement st = con.createStatement(); 
			String query = "select * from project"; 
			ResultSet rs = st.executeQuery(query); 
			
			if(rs!=null) {
				System.out.println("\n<--------- Project List --------->\n");
				while(rs.next()) {
					System.out.println("Project Id : " + rs.getInt("project_id"));
					System.out.println("Project Name : " + rs.getString("project_name"));
					System.out.println("Description  : " + rs.getString("project_description"));
					System.out.println();
				}
			}else {
				throw new ResourceNotFoundException("Projects Not Found ! "); 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update  Project By Id
	 */
	public static void updateProject() {
	    try (Connection con = DBConnection.connect()) {
	        Statement st = con.createStatement();

	        System.out.print("Enter Project ID to update : ");
	        int projectIdToUpdate = sc.nextInt();
	        
	        // Check if the Project exists
	        String checkQuery = "SELECT project_id FROM project WHERE project_id = '" + projectIdToUpdate + "'";
	        ResultSet checkRs = st.executeQuery(checkQuery);

	        if (checkRs.next()) {
	        	
	        	System.out.print("Enter Project Name: ");
	        	String projectName = sc.nextLine();

	        	sc.nextLine();

	        	System.out.print("Enter Project Description: ");
	        	String description = sc.nextLine();

	            String updateQuery = "UPDATE project SET project_name = ?, project_description = ? WHERE project_id = ?";
	            
	            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
	            updateStatement.setString(1, projectName);
	            updateStatement.setString(2, description);
	            updateStatement.setInt(3, projectIdToUpdate);

	            int rowsUpdated = updateStatement.executeUpdate();
	            
	            if (rowsUpdated == 1) {
	                System.out.println("Project Updated successfully!");
	            } else {
	                System.out.println("Project Update Failed.");
	            }
	        } else {
	            System.out.println("Project with ID " + projectIdToUpdate + " does not exist.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
