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

        String userName = null;
        String sessionID = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
            }
            HashMap<String,String> sessionsStorage = (HashMap<String, String>) SessionStorage.getSessions();
            userName = sessionsStorage.get(sessionID);
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        List<Group> groupList = Services.getGroups(userName);
        String[] groupNames = new String[groupList.size()];
        for (int i = 0;i<groupList.size();i++)
            groupNames[i] = groupList.get(i).getName();

        Enumeration<String> en = request.getParameterNames();
        String paramName = "";
        while (en.hasMoreElements()) {
            paramName = en.nextElement();
            if (name.equals(""))
                name = request.getParameter(paramName);
            else
            if (ph_number.equals(""))
                ph_number = request.getParameter(paramName);
            else
                groupName = request.getParameter(paramName);
        }
        writer.println(HtmlCreator.createUpdateContactHTML(groupNames));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        Enumeration<String> en = request.getParameterNames();
        String paramName = "";
        String newName ="";
        String newPhNumber = "";
        String newgroupName = "";
        while (en.hasMoreElements()) {
            paramName = en.nextElement();
            if (newName.equals(""))
                newName = request.getParameter(paramName);
            else
            if (newPhNumber.equals(""))
                newPhNumber = request.getParameter(paramName);
            else
                newgroupName = request.getParameter(paramName);
        }
        Services.updateContact(new Contact(name,ph_number,new Group(groupName)),newName,newPhNumber,new Group(newgroupName));
        response.sendRedirect(request.getContextPath() + "/contactlist");

    }
}