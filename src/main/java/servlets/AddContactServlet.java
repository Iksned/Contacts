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

public final class AddContactServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        GroupService services = (GroupService) SpringUtils.getBean("groupService");

        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = ((UserService)SpringUtils.getBean("userService")).getUserById(currentUserName);
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        List<Group> groupList = services.getAllGroups(currentUser.getUsername());
        writer.println(HtmlCreator.createAddContactHTML(groupList));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        GroupService services = (GroupService) SpringUtils.getBean("groupService");
        ContactService contactService = (ContactService) SpringUtils.getBean("contactService");

        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = ((UserService)SpringUtils.getBean("userService")).getUserById(currentUserName);
        }

        response.setContentType("text/html");

        String name = request.getParameter("name");
        String ph_number = request.getParameter("ph_num");
        String grouid = request.getParameter("groupname");
        Group chosenGroup = services.getGroupById(Integer.parseInt(grouid));
        Contact newContact = new Contact(name,Integer.parseInt(ph_number));
        newContact.setUser(currentUser);
        newContact.setGroup(chosenGroup);
        contactService.addContact(newContact);
        response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}