package servlets;

import model.Contact;
import model.Group;
import model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import services.ContactService;
import services.GroupService;
import services.UserService;
import utils.HtmlCreator;
import utils.SpringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UpdateContactServlet extends HttpServlet {

    private Contact updatedContact;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
            GroupService groupService = (GroupService) SpringUtils.getBean("groupService");
            ContactService contactService = (ContactService) SpringUtils.getBean("contactService");

        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = ((UserService)SpringUtils.getBean("userService")).getUserById(currentUserName);
        }

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            List<Group> groupList = groupService.getAllGroups(currentUser.getUsername());
            String contactId = request.getParameter("id");
            updatedContact =contactService.getContactById(Integer.parseInt(contactId));

            writer.println(HtmlCreator.createUpdateContactHTML(updatedContact,groupList));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            GroupService groupService = (GroupService) SpringUtils.getBean("groupService");
            ContactService contactService = (ContactService) SpringUtils.getBean("contactService");
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            String newPhNumber = request.getParameter("newphnum");
            String groupid = request.getParameter("groupid");
            Group newGroup = groupService.getGroupById(Integer.parseInt(groupid));
            updatedContact.setName(newName);
            updatedContact.setPh_number(Integer.parseInt(newPhNumber));
            updatedContact.setGroup(newGroup);
            contactService.updateContact(updatedContact);
            response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}