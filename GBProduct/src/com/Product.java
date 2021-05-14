package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product {
	
	//database connection
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
			
		}catch(Exception e) {
			System.out.println("This is the error: "+e);
		}
		return con;
	}
	
	public String readProducts() {
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "error while connecting to the database for reading.";
			}
			//prepare the HTML table
			output = "<div class='table-wrapper-scroll-y my-custom-scrollbar'><table border='1' class='table'><thead class='thead-dark'><tr><th>id</th><th>Product Code</th><th>Product Name</th>" +
					"<th>Product Price</th>" +  
					"<th>Product Description</th>" +
					"<th>Update</th><th>Remove</th></tr></thead>";
			
			//query
			String query = "select * from product";
			Statement stmt = con.createStatement();
			ResultSet rs  = stmt.executeQuery(query);
			
			//iterate through the rows in the result set
			while(rs.next()) {
				String productID = Integer.toString(rs.getInt("id")); 
				String productCode = rs.getString("code"); 
				String productName = rs.getString("name"); 
				String productPrice = rs.getString("price"); 
				String productDesc = rs.getString("description");
				
				//add into HTML table
				output += "<tr><td>"+ productID +"</td>";
				output += "<td>" + productCode + "</td>"; 
				output += "<td>" + productName + "</td>"; 
				output += "<td>" + productPrice + "</td>"; 
				output += "<td>" + productDesc + "</td>"; 
				
				//buttons
				output += "<td>"
							+"<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-productid='"+ productID +"'>"
							+"</td>"+ 
							"<td>"+ 
							"<input name='btnRemove' type='button' value='Remove'  class='btnRemove btn btn-danger' data-productid='"+ productID +"'>"+ 
							"</td></tr>";
			}
			//database connection is closed.
			con.close();
			
			//HTML table is completed.
			output +="</table></div>";
			
		}catch(Exception e) {
			output = "Error while reading the products";
			System.err.println("This is the error: "+e.getMessage());
		}
		
		return output;
	}
	
	public String insertProduct(String code, String name, String price, String description) {
		String output="";
		
		try {
			//Database Connection
			Connection con = connect();
			if(con == null) {
				return "While connecting to the database for inserting.";
			}
			
			//SQL query
			String sql = ("insert into product (code,name,price,description) values (?,?,?,?)");
			PreparedStatement pdstm = con.prepareStatement(sql);
			
			//bind values
			pdstm.setString(1, code);
			pdstm.setString(2, name);
			pdstm.setString(3, price);
			pdstm.setString(4, description);
			
			//Execute the statement
			pdstm.executeUpdate();
			con.setAutoCommit(false);
			con.commit();
			con.close();
			//output="Inserted successfully";
			String newProducts = readProducts();
			output ="{\"status\":\"success\", \"data\":\""+ newProducts +"\"}";
			
			
		}catch(Exception e) {
			output ="{\"status\":\"error\", \"data\":\"Error while inserting product.\"}";
			System.err.print("This is the error in insert:"+e.getMessage());
		}
		return output;
		
	}
	
	public String updateProduct(String id, String code, String name, String price, String description) {
		String output = "";
		
		try {
			//check the database connection
			Connection con = connect();
			if(con == null) {
				return "Error while connectintg to the database for update the records.";
			}
			
			//sql query
			String sql = "update product set code=? , name=?, price=?, description=? where id=? ";
			PreparedStatement pd = con.prepareStatement(sql);
			
			//bind values
			pd.setString(1, code);
			pd.setString(2, name);
			pd.setString(3, price);
			pd.setString(4, description);
			pd.setInt(5,Integer.parseInt(id));
			
			//execute the statment
			pd.executeUpdate();
			con.setAutoCommit(false);
			con.commit();
			//output="Updated successfuly";
			
			String newProducts = readProducts();
			output ="{\"status\":\"success\", \"data\":\""+ newProducts +"\"}";
			
		}catch(Exception e) {
			output= "{\"status\":\"error\", \"data\":\"Error while updating the product.\"}";
			System.err.println("This is the error in update:"+e.getMessage());
		}
		
		return output;
	}
	
	public String deleteProduct(String id) {
		String output="";
		try {
			//Check database connection
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for delete.";
			}
//			System.out.println(id);
			
			//create prepared statement
			String query = "delete from product where id=?";
			PreparedStatement pd = con.prepareStatement(query);
			
			//binding values
			pd.setInt(1, Integer.parseInt(id));
			
			//execute the statement
			pd.executeUpdate();
			con.setAutoCommit(false);
			con.commit();
			//output="Deleted Successfully.";
			
			String newProducts = readProducts();
			output ="{\"status\":\"success\", \"data\":\""+ newProducts +"\"}";
			
		}catch(Exception e) {
			output= "{\"status\":\"error\", \"data\":\"Error while deleting the product.\"}";
			System.err.print("This is the error in deleting:"+e.getMessage());
		}
		return output;
	}
	
}
