package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.exceptions.WrongDataException;
import ru.kpfu.itis.repositories.ProfilesRepository;
import ru.kpfu.itis.repositories.impl.ProfilesRepositoryJdbcTemplateImpl;

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

@WebServlet(urlPatterns = "/settings/", name = "SettingsServlet")
public class SettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));

        Profile profile = profiles.getProfileById(user.getId());

        req.setAttribute("name", profile.getName());
        req.setAttribute("city", profile.getCity());
        req.setAttribute("age", profile.getAge());
        req.setAttribute("description", profile.getDescription());
        req.setAttribute("minDesiredAge", profile.getMinAgePrefered());
        req.setAttribute("maxDesiredAge", profile.getMaxAgePrefered());
        req.setAttribute("desiredSex", profile.getSexPrefered());


        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));

        Profile profile = profiles.getProfileById(user.getId());

        try {
            if (Integer.parseInt((req.getParameter("minDesiredAge"))) > Integer.parseInt((req.getParameter("maxDesiredAge")))) {
                throw new WrongDataException();
            }
            profile.setMaxAgePrefered(Integer.valueOf((req.getParameter("minDesiredAge"))));
            profile.setMinAgePrefered(Integer.valueOf(req.getParameter("maxDesiredAge")));
            profile.setSexPrefered(req.getParameter("desiredSex"));
        }
        catch (WrongDataException exc){
            req.setAttribute("message", "The min age should be lesser than max age.");
        }

        profiles.update(profile);

        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/settings.jsp").forward(req, resp);
    }
}
