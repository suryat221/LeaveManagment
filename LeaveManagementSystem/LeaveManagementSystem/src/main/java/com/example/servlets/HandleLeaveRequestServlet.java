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

@WebServlet(name = "HandleLeaveRequestServlet", urlPatterns = "/manager/handle_leave_request")
public class HandleLeaveRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("request_id"));
        String action = request.getParameter("action"); // 'approve' or 'reject'

        String leaveStatus = action.equals("approve") ? "Approved" : "Rejected";

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE leave_requests SET leave_status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, leaveStatus);
            statement.setInt(2, requestId);
            statement.executeUpdate();

            response.getWriter().println("Leave request " + leaveStatus.toLowerCase() + ".");
        } catch (SQLException e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}