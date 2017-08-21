package servlets;

import ConModel.Group;
import ConModel.services.Services;
import Utils.HtmlCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AddGroupServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println(HtmlCreator.createAddGroupHTML());
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
        while (en.hasMoreElements()) {
            paramName = en.nextElement();
            if (name.equals(""))
                name = request.getParameter(paramName);
        }
        Services.addGroup(userName,new Group(name));
        response.sendRedirect(request.getContextPath() + "/grouplist");
    }
}