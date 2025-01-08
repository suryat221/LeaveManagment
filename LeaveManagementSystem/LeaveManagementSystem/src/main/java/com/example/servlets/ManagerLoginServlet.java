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

@WebServlet(name = "ManagerLoginServlet", urlPatterns = "/manager/login")
public class ManagerLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM managers WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                response.getWriter().println("Login successful. Welcome, " + username + "!");
            } else {
                response.getWriter().println("Invalid username or password.");
            }
        } catch (SQLException e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}