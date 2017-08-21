package servlets;

import ConModel.services.Services;
import Utils.HtmlCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UpdateGroupServlet extends HttpServlet {
    private String name = "";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        Enumeration<String> en = request.getParameterNames();
        String paramName = "";
        while (en.hasMoreElements()) {
            paramName = en.nextElement();
            if (name.equals(""))
                name = request.getParameter(paramName);
        }
        writer.println(HtmlCreator.createUpdateGroupHTML(name));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        Enumeration<String> en = request.getParameterNames();
        String paramName = "";
        String newName ="";
        while (en.hasMoreElements()) {
            paramName = en.nextElement();
            if (newName.equals(""))
                newName = request.getParameter(paramName);
        }
        Services.updateGroup(name,newName);
        response.sendRedirect(request.getContextPath() + "/grouplist");

    }
}