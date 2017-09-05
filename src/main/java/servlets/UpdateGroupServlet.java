package servlets;

import model.Group;
import services.GroupService;
import utils.HtmlCreator;
import utils.SpringUtils;

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
            GroupService groupService = (GroupService) SpringUtils.getBean("groupService");
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            updatedGroup = groupService.getGroupById(id);
            writer.println(HtmlCreator.createUpdateGroupHTML(updatedGroup));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            GroupService groupService = (GroupService) SpringUtils.getBean("groupService");
            response.setContentType("text/html");
            String newName = request.getParameter("newname");
            updatedGroup.setName(newName);
            groupService.updateGroup(updatedGroup);
            response.sendRedirect(request.getContextPath() + "/grouplist");
    }
}