package servlets;

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

public final class GroupList extends HttpServlet {

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
            writer.println(HtmlCreator.createGroupListHTML(currentUser.getUsername(), groupList));
    }
}