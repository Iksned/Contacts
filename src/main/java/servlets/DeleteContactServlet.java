package servlets;

import ConModel.Contact;
import ConModel.Group;
import ConModel.services.Services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteContactServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            response.setContentType("text/html");
            String contactId = request.getParameter("id");
            Contact contact = Services.getInstace().getContactById(Integer.parseInt(contactId));
            Services.getInstace().delContact(contact);
            response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}