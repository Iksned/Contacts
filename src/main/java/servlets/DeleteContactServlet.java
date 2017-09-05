package servlets;

import model.Contact;
import services.ContactService;
import utils.SpringUtils;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteContactServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            ContactService contactService = (ContactService) SpringUtils.getBean("contactService");
            response.setContentType("text/html");
            String contactId = request.getParameter("id");
            Contact contact =contactService.getContactById(Integer.parseInt(contactId));
            contactService.delContact(contact);
            response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}