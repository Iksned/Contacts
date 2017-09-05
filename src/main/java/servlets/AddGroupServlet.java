package servlets;

import model.Group;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import services.GroupService;
import services.UserService;
import utils.HtmlCreator;
import utils.SpringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AddGroupServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AddGroupServlet.class);
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        synchronized (this) {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            writer.println(HtmlCreator.createAddGroupHTML());
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            GroupService services = (GroupService) SpringUtils.getBean("groupService");

        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            currentUser = ((UserService)SpringUtils.getBean("userService")).getUserById(currentUserName);
        }
            log.info(currentUser.getUsername());
            response.setContentType("text/html");
            String name = request.getParameter("name");
            Group newGroup = new Group(name);
            newGroup.setUser(currentUser);
            services.addGroup(newGroup);
            response.sendRedirect(request.getContextPath() + "/grouplist");
    }
}