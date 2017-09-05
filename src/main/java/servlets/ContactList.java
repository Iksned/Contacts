package servlets;

import model.Contact;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import services.ContactService;
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

public final class ContactList extends HttpServlet {
    private static final Logger log = Logger.getLogger(ContactList.class);
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

            User currentUser = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                currentUser = ((UserService)SpringUtils.getBean("userService")).getUserById(currentUserName);
            }
            log.info("Current user in contactlist");
            log.info(currentUser.getUsername());
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            ContactService contactService = (ContactService) SpringUtils.getBean("contactService");
            List<Contact> contactNames = contactService.getAllContacts(currentUser.getUsername());
            writer.println(HtmlCreator.createContactListHTML(currentUser.getUsername(), contactNames));

    }
}