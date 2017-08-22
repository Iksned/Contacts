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
        synchronized (this) {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            name = request.getParameter("name");
            writer.println(HtmlCreator.createUpdateGroupHTML(name));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        synchronized (this) {
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            Services.getInstace().updateGroup(name, newName);
            response.sendRedirect(request.getContextPath() + "/grouplist");
        }
    }
}