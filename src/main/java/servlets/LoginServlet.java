package servlets;

import model.Role;
import service.AuthorizationService;
import utils.DBConnector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.nonNull;

public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // метод get
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) // метод post
            throws ServletException, IOException {
        final HttpSession session = request.getSession();
        final String userName = request.getParameter("login"); // получить введённый логин
        final String password = request.getParameter("password"); // получить введённый пароль
        try {
            if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) { // если пользователь уже вошёл в систему, перенаправить его на нужную страницу
                final Role role = (Role) session.getAttribute("role");
                moveToMainPage(request, response, role);
            } else { // иначе используя AuthorizationService вытащить из бд данные о пользователе с таким логином и паролем
                Class.forName("org.postgresql.Driver");
                Connection connection = DBConnector.connect();
                AuthorizationService authorizationService = new AuthorizationService(connection);
                Role loginResult = authorizationService.login(userName, password);
                if (loginResult != null) { // если пользователь найден
                    request.getSession().setAttribute("password", password);
                    request.getSession().setAttribute("login", userName);
                    request.getSession().setAttribute("role", loginResult);
                    moveToMainPage(request, response, loginResult); // добавить в сессию аттрибуты и перенаправить на нужнкю страницу
                } else { // если пользователь не найден
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
                    request.setAttribute("Error", "Введены неверные данные!");
                    dispatcher.forward(request, response); // вывести сообщение о том, что введены неверные данные
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void moveToMainPage(final HttpServletRequest request, final HttpServletResponse response, final Role role) throws ServletException, IOException { // переводит на страницу в зависимости от роли
        if (role.equals(Role.ADMIN)) {
            request.getRequestDispatcher("/WEB-INF/view/admin_main_page.jsp").forward(request, response);
        } else if (role.equals(Role.USER)) {
            request.getRequestDispatcher("/WEB-INF/view/user_main_page.jsp").forward(request, response);
        }
    }

}