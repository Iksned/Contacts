package servlets;

import ConModel.Contact;
import ConModel.Group;
import ConModel.services.Services;
import Utils.HtmlCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AddContactServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String userName = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
            }
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        List<Group> groupList = Services.getGroups(userName);
        String[] groupNames = new String[groupList.size()];
        for (int i = 0;i<groupList.size();i++)
            groupNames[i] = groupList.get(i).getName();
        writer.println(HtmlCreator.createAddContactHTML(groupNames));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        String userName = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
            }
        }

        response.setContentType("text/html");
        Enumeration<String> en = request.getParameterNames();
        String paramName = "";
        String name = "";
        String ph_number = "";
        String groupName = "";
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
        Services.addContact(userName,new Contact(name,ph_number,new Group(groupName)));
        response.sendRedirect(request.getContextPath() + "/contactlist");
    }
}