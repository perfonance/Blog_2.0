package model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { // метод get
        final HttpSession session = request.getSession();
        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");
        response.sendRedirect("/login"); // удаляет все аттрибуты сессии и переводит на страницу /login
    }
}