package servlets;

import model.Group;
import services.GroupService;
import utils.SpringUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteGroupServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
            GroupService services = (GroupService) SpringUtils.getBean("groupService");
            response.setContentType("text/html");
            String groupid = request.getParameter("id");
            Group group = services.getGroupById(Integer.parseInt(groupid));
            services.delGroup(group);
            response.sendRedirect(request.getContextPath() + "/grouplist");
    }
}