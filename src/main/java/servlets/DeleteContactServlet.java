package servlets;

import ConModel.Contact;
import ConModel.Group;
import ConModel.services.Services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteContactServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
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
        Services.delContact(new Contact(name,ph_number,new Group(groupName)));
        response.sendRedirect(request.getContextPath() + "/contactlist");

    }
}