package servlets;

import ConModel.Group;
import ConModel.services.Services;
import Utils.HtmlCreator;
import Utils.SessionStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GroupList extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        String userName = null;
        synchronized (this) {
            String sessionID = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
                }
                HashMap<String, String> sessionsStorage = (HashMap<String, String>) SessionStorage.getSessions();
                userName = sessionsStorage.get(sessionID);
            }

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            List<Group> groupList = Services.getInstace().getGroups(userName);
            String[] groupNames = new String[groupList.size()];
            for (int i = 0; i < groupList.size(); i++)
                groupNames[i] = groupList.get(i).getName();

            writer.println(HtmlCreator.createGroupListHTML(userName, groupNames));
        }
    }
}