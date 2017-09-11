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

public final class UpdateContactServlet extends HttpServlet {

    private Contact updatedContact;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
            GroupService groupService = (GroupService) SpringUtils.getBean("groupService");
            ContactService contactService = (ContactService) SpringUtils.getBean("contactService");
            UserService userService = (UserService)SpringUtils.getBean("userService");
        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = userService.getUserById(currentUserName);
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
            UserService userService = (UserService)SpringUtils.getBean("userService");
            User currentUser = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                currentUser = userService.getUserById(currentUserName);
            }
            List<Group> groupList = new ArrayList<>();
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            String newPhNumber = request.getParameter("newphnum");
            Group defGroup = groupService.getGroupById(0);
            for (int i =0;i<currentUser.getGrouplist().size();i++){
            boolean b = false;
            String grouid = request.getParameter("groupname"+i);
                for (int j = 0;j< groupList.size();j++) {
                if (Integer.parseInt(grouid)==groupList.get(j).getId())
                    b = true;
            }
            if (!b){
                Group chosenGroup = groupService.getGroupById(Integer.parseInt(grouid));
                groupList.add(chosenGroup);
            }
        }
            updatedContact.setName(newName);
            updatedContact.setPh_number(Integer.parseInt(newPhNumber));
            updatedContact.setGroup(groupList);
            updatedContact.addGroup(defGroup);
            contactService.updateContact(updatedContact);
            response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}