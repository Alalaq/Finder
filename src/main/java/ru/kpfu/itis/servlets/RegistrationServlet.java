package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.DuplicateEntryException;
import ru.kpfu.itis.exceptions.NoConsentException;
import ru.kpfu.itis.exceptions.WrongEmailException;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.repositories.impl.ProfilesRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.repositories.impl.UsersRepositoryJdbcTemplateImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@WebServlet(urlPatterns = "/registration/", name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("status") != null) {
            if (req.getParameter("status").equals("1")) {
                req.setAttribute("message", "User has been successfully created.");
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (
                req.getParameter("email") != null &&
                        req.getParameter("password") != null
        ) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            HttpSession session = req.getSession();
            try {
                if (req.getParameter("consent") == null) {
                    throw new NoConsentException();
                }
                User user = new User(email, password);

                UsersRepository users = new UsersRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));
                ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));

                long curr_id = users.add(user);

                profiles.addEmptyProfile(curr_id);

                session.setAttribute("user", user);
                getServletContext().getRequestDispatcher("/profile/").forward(req, resp);
                return;
            } catch (DuplicateEntryException ex) {
                req.setAttribute("message", "Such user already exists, you need to change email.");
            }
            catch (WrongEmailException ex) {
                req.setAttribute("message", " Wrong email. Try again.");
            } catch (NoConsentException ex) {
                req.setAttribute("message", "  You need to agree with the terms.");
            }
        } else {
            req.setAttribute("message", " YOU NEED TO FILL ALL THE FIELDS TO REGISTER");
        }

        req.getRequestDispatcher("/WEB-INF/views/pages/registration.jsp").forward(req, resp);
    }
}
