package com.example.servlets;
////
////import com.example.util.DatabaseUtil;
////
////import javax.servlet.ServletException;
//////import javax.servlet.annotation.WebServlet;
////import javax.servlet.http.HttpServlet;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////import java.io.IOException;
////import java.sql.Connection;
////import java.sql.PreparedStatement;
////import java.sql.SQLException;
//
//public class EmployeeRegisterServlet extends HttpServlet {
////    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//
//        try (Connection connection = DatabaseUtil.getConnection()) {
//            String sql = "INSERT INTO employees (username, password, email, phone) VALUES (?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, username);
//            statement.setString(2, password);
//            statement.setString(3, email);
//            statement.setString(4, phone);
//            statement.executeUpdate();
//
//            response.getWriter().println("Employee registered successfully.");
//        } catch (SQLException e) {
//            response.getWriter().println("Error: " + e.getMessage());
//        }
//    }
//}


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeRegisterServlet extends HttpServlet {
	Connection connection;

	
	public void init(ServletConfig config) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3307/test";
		String username = "root";
		String password = "root";
		connection = DriverManager.getConnection(url, username, password); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//System.out.println("2");
		 String username=request.getParameter("username");
		 String password=request.getParameter("password");
		 String email=request.getParameter("email");
		 long phone=Long.parseLong(request.getParameter("phone"));
		 PrintWriter pw=response.getWriter();
		 String sql = "insert into employees(username,password,email,phone) values(?,?,?,?)";
		 PreparedStatement ps;
		try {
			 ps= connection.prepareStatement(sql);
			 ps.setString(1, username);
			 ps.setString(2, password);
			 ps.setString(3, email);
			 ps.setLong(4, phone);
			 int x=ps.executeUpdate();
			 if(x!=0)
				 pw.println("Registered Successfully");
			 	//response.sendRedirect("./login.html?msg=Registered Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
