package servlets;

import ConModel.Group;
import ConModel.services.Services;
import utils.HtmlCreator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UpdateGroupServlet extends HttpServlet {
    private Group updatedGroup;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            updatedGroup = Services.getInstace().getGroupById(id);
            writer.println(HtmlCreator.createUpdateGroupHTML(updatedGroup));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            updatedGroup.setName(newName);
            Services.getInstace().updateGroup(updatedGroup);
            response.sendRedirect(request.getContextPath() + "/grouplist");
    }
}