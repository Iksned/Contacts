package servlets;

import ConModel.User;
import ConModel.services.Services;
import utils.SessionStorage;
import utils.HtmlCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public final class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();

            String name = request.getParameter("uname");
            String pass = request.getParameter("pass");

            if (Services.getInstace().checkUser(name, pass)) {
                HttpSession session = request.getSession();

                Cookie cookie = new Cookie("user", name);
                response.addCookie(cookie);

                User currentUser = Services.getInstace().getUserById(name);
                Map<String, User> sessionsStorage = SessionStorage.getSessions();
                sessionsStorage.put(session.getId(), currentUser);
                SessionStorage.setSessions(sessionsStorage);

                response.sendRedirect(request.getContextPath() + "/contactlist");
            } else {
                writer.println(HtmlCreator.createFailLoginHTML());
            }
    }
} 