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

@WebServlet(name = "ViewLeaveStatusServlet", urlPatterns = "/employee/view_leave_status")
public class ViewLeaveStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the employee ID from the request parameter
        String employeeIdStr = request.getParameter("employee_id");
        if (employeeIdStr == null || employeeIdStr.isEmpty()) {
            response.getWriter().println("Error: Employee ID is required.");
            return;
        }

        int employeeId;
        try {
            employeeId = Integer.parseInt(employeeIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("Error: Invalid Employee ID.");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            // SQL query to fetch leave requests for the employee
            String sql = "SELECT id, leave_reason, leave_status, applied_at FROM leave_requests WHERE employee_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            response.setContentType("text/html");
            response.getWriter().println("<h3>Leave Requests for Employee ID: " + employeeId + "</h3>");
            response.getWriter().println("<table border='1'>");
            response.getWriter().println("<tr><th>Request ID</th><th>Reason</th><th>Status</th><th>Applied At</th></tr>");

            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                int requestId = resultSet.getInt("id");
                String reason = resultSet.getString("leave_reason");
                String status = resultSet.getString("leave_status");
                String appliedAt = resultSet.getTimestamp("applied_at").toString();

                response.getWriter().println("<tr>");
                response.getWriter().println("<td>" + requestId + "</td>");
                response.getWriter().println("<td>" + reason + "</td>");
                response.getWriter().println("<td>" + status + "</td>");
                response.getWriter().println("<td>" + appliedAt + "</td>");
                response.getWriter().println("</tr>");
            }

            response.getWriter().println("</table>");

            if (!hasResults) {
                response.getWriter().println("<p>No leave requests found for this employee.</p>");
            }
        } catch (SQLException e) {
            response.getWriter().println("Error: Unable to fetch leave requests. " + e.getMessage());
        }
    }
}