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

@WebServlet(name = "ViewLeaveRequestsServlet", urlPatterns = "/manager/view_leave_requests")
public class ViewLeaveRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM leave_requests";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                response.getWriter().println("Request ID: " + resultSet.getInt("id"));
                response.getWriter().println("Employee ID: " + resultSet.getInt("employee_id"));
                response.getWriter().println("Reason: " + resultSet.getString("leave_reason"));
                response.getWriter().println("Status: " + resultSet.getString("leave_status"));
                response.getWriter().println("-------------------------");
            }
        } catch (SQLException e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}