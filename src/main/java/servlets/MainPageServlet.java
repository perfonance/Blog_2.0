package servlets;

import model.Role;
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

import static java.util.Objects.nonNull;

public class MainPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод get
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.ADMIN) { // если менеджер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/admin_main_page.jsp").forward(request, response);
            } else if (session.getAttribute("role") == Role.USER) { // если мастер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/user_main_page.jsp").forward(request, response);
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод post
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.ADMIN) { // если менеджер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/admin_main_page.jsp").forward(request, response);
            } else if (session.getAttribute("role") == Role.USER) { // если мастер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/user_main_page.jsp").forward(request, response);
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}
