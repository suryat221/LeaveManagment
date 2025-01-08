package com.example.servlets;

import com.example.util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "EmployeeLoginServlet", urlPatterns = "/employee/login")
public class EmployeeLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve username and password from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate input to prevent SQL injection and empty fields
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.getWriter().println("Username and password cannot be empty.");
            return;
        }
        

        // Database interaction
        try (Connection connection = DatabaseUtil.getConnection()) {
            // SQL query to authenticate user
            String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username.trim());
                statement.setString(2, password.trim());

                // Execute query
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        response.getWriter().println("Login successful. Welcome, " + username + "!");
                    } else {
                        response.getWriter().println("Invalid username or password.");
                    }
                }
            }
        } catch (SQLException e) {
            response.getWriter().println("An error occurred while processing your request: " + e.getMessage());
        }
    }
}
