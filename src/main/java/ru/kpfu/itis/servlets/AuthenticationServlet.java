package ru.kpfu.itis.servlets;

import ru.kpfu.itis.encryption.PasswordValidator;
import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.NoSuchUserException;
import ru.kpfu.itis.repositories.ProfilesRepository;
import ru.kpfu.itis.repositories.impl.ProfilesRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.impl.UsersRepositoryJdbcTemplateImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@WebServlet(urlPatterns = "/authentication/", name = "AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/authentication.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ((
                req.getParameter("email") != null) &&
                req.getParameter("password") != null
        ) {
            try {
                String email = req.getParameter("email");
                String password = req.getParameter("password");

                UsersRepository users = new UsersRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));
                ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));

                User new_user = new User(email, password);
                User user = users.getUserByEmail(new_user);
                Profile profile = profiles.getProfileById(user.getId());

                HttpSession session = req.getSession();
                if (PasswordValidator.validatePassword(password, user.getPassword())) {
                    req.setAttribute("email", user.getEmail());
                    session.setAttribute("user", user);

                    if (profile.getName() == null || profile.getCity() == null || profile.getDescription() == null
                    || profile.getAge() == null || profile.getSex() == null) {
                        resp.sendRedirect("/Finder/profile/");
                        return;
                    }
                    else {
                        resp.sendRedirect("/Finder/profiles/");
                        return;
                    }
                } else {
                    throw new NoSuchUserException();
                }
            } catch (NoSuchUserException ex) {
                req.setAttribute("message", "Wrong email or password. Try again.");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("message", "You must fill all fields. ");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/authentication.jsp").forward(req, resp);
    }
}