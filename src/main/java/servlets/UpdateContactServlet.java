package servlets;

import ConModel.Contact;
import ConModel.Group;
import ConModel.services.Services;
import Utils.HtmlCreator;
import Utils.SessionStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UpdateContactServlet extends HttpServlet {
    private String name = "";
    private String ph_number = "";
    private String groupName = "";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        synchronized (this) {
            String userName = null;
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
            name = request.getParameter("name");
            ph_number = request.getParameter("phnum");
            groupName = request.getParameter("groupname");

            writer.println(HtmlCreator.createUpdateContactHTML(name,ph_number,groupNames));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        synchronized (this) {
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            String newPhNumber = request.getParameter("newphnum");
            String newgroupName = request.getParameter("newgroupname");
            Services.getInstace().updateContact(new Contact(name, ph_number, new Group(groupName)), newName, newPhNumber, new Group(newgroupName));
            response.sendRedirect(request.getContextPath() + "/contactlist");
        }
    }
}