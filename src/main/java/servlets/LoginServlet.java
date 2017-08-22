package servlets;

import ConModel.services.Services;
import Utils.SessionStorage;
import Utils.HtmlCreator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public final class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        synchronized (this) {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();

            Enumeration<String> en = request.getParameterNames();
            String paramName = "";
            String name = "";
            String pass = "";
            while (en.hasMoreElements()) {
                paramName = en.nextElement();
                if (name.equals(""))
                    name = request.getParameter(paramName);
                else
                    pass = request.getParameter(paramName);
            }

            if (Services.getInstace().checkUser(name, pass)) {
                HttpSession session = request.getSession();

                Cookie cookie = new Cookie("user", name);
                response.addCookie(cookie);

                HashMap<String, String> sessionsStorage = (HashMap<String, String>) SessionStorage.getSessions();
                sessionsStorage.put(session.getId(), name);
                SessionStorage.setSessions(sessionsStorage);

                response.sendRedirect(request.getContextPath() + "/contactlist");
            } else {
                writer.println(HtmlCreator.createFailLoginHTML());
            }
        }
    }
} 