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
import java.util.ArrayList;
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
        UserService userService = (UserService)SpringUtils.getBean("userService");
        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = userService.getUserById(currentUserName);
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
        UserService userService = (UserService)SpringUtils.getBean("userService");

        User currentUser = null;
        List<Group> groupList = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = userService.getUserById(currentUserName);
        }

        response.setContentType("text/html");
        String name = request.getParameter("name");
        String ph_number = request.getParameter("ph_num");
        Contact newContact = new Contact(name,Integer.parseInt(ph_number));
        newContact.setGroup(groupList);
        Group defGroup = services.getGroupById(0);
        for (int i =0;i<currentUser.getGrouplist().size();i++){
            boolean b = false;
            String grouid = request.getParameter("groupname"+i);
            for (int j = 0;j< groupList.size();j++) {
                if (Integer.parseInt(grouid)==groupList.get(j).getId())
                    b = true;
            }
            if (!b){
                Group chosenGroup = services.getGroupById(Integer.parseInt(grouid));
                newContact.addGroup(chosenGroup);
            }
        }
        newContact.addGroup(defGroup);
        newContact.setUser(currentUser);
        contactService.addContact(newContact);
        response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}