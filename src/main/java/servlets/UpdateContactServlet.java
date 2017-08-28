package servlets;

import ConModel.Contact;
import ConModel.Group;
import ConModel.User;
import ConModel.services.Services;
import utils.HtmlCreator;
import utils.SessionStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UpdateContactServlet extends HttpServlet {

    Contact updatedContact;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
            User currentUser = null;
            String sessionID = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
                }
                Map<String, User> sessionsStorage = SessionStorage.getSessions();
                currentUser = sessionsStorage.get(sessionID);
            }

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            List<Group> groupList = Services.getInstace().getGroups(currentUser.getUsername());
            String contactId = request.getParameter("id");
            updatedContact = Services.getInstace().getContactById(Integer.parseInt(contactId));

            writer.println(HtmlCreator.createUpdateContactHTML(updatedContact,groupList));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            String newPhNumber = request.getParameter("newphnum");
            String groupid = request.getParameter("groupid");
            Group newGroup = Services.getInstace().getGroupById(Integer.parseInt(groupid));
            updatedContact.setName(newName);
            updatedContact.setPh_number(Integer.parseInt(newPhNumber));
            updatedContact.setGroup(newGroup);
            Services.getInstace().updateContact(updatedContact);
            response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}