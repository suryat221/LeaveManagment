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
import java.sql.SQLException;

@WebServlet(name = "ApplyLeaveServlet", urlPatterns = "/employee/apply_leave")
public class ApplyLeaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employee_id"));
        String leaveReason = request.getParameter("leave_reason");
        String fromDate = request.getParameter("from_date"); // Retrieve from_date from the request
        String toDate = request.getParameter("to_date"); 

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO leave_requests (employee_id, leave_reason, from_date, to_date) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.setString(2, leaveReason);
            statement.setDate(3, java.sql.Date.valueOf(fromDate)); // Set from_date
            statement.setDate(4, java.sql.Date.valueOf(toDate)); 
            
            statement.executeUpdate();

            response.getWriter().println("Leave applied successfully.");
        } catch (SQLException e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}