package servlets;

import ConModel.Group;
import ConModel.services.Services;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteGroupServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        Enumeration<String> en = request.getParameterNames();
        String paramName = "";
        String name = "";
        while (en.hasMoreElements()) {
            paramName = en.nextElement();
            if (name.equals(""))
                name = request.getParameter(paramName);
        }
        Services.delGroup(new Group(name));
        response.sendRedirect(request.getContextPath() + "/grouplist");

    }
}