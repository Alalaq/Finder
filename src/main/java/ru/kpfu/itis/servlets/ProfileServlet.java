package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.University;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.repositories.ProfilesRepository;
import ru.kpfu.itis.repositories.UniversityRepository;
import ru.kpfu.itis.repositories.UserUniversityRepository;
import ru.kpfu.itis.repositories.impl.ProfilesRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.repositories.impl.UniversityRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.repositories.impl.UserUniversityRepositoryJdbcTemplateImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@WebServlet(urlPatterns = "/profile/", name = "ProfileServlet")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UniversityRepository universityRepository = new UniversityRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));
        List<University> universities = universityRepository.getAll();

        req.setAttribute("universities", universities);

        req.setAttribute("message", "Please fill out your application form so that we can better match you");
        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (
                req.getParameter("name") != null &&
                        req.getParameter("city") != null &&
                        req.getParameter("age") != null &&
                        req.getParameter("sex") != null
        ) {
            try {
                String[] hobbiesUnfiltred = req.getParameterValues("hobbies");
                List<String> hobbies = new ArrayList<>();

                if (hobbiesUnfiltred != null) {
                    for(String value: hobbiesUnfiltred){
                        String keyValue[]= value.split(":");
                        hobbies.add(keyValue[1]);
                    }
                    if (hobbies.size() > 5) {
                        throw new IllegalArgumentException();
                    }
                }
                ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));

                HttpSession session = req.getSession();
                User user = (User) session.getAttribute("user");

                if (req.getParameter("university") != null){
                    UserUniversityRepository userUniversityRepository = new UserUniversityRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));
                    UniversityRepository universities = new UniversityRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));

                    University university = universities.getByTitle(req.getParameter("university"));

                    userUniversityRepository.add(university, user);
                }

                Profile profile = Profile.builder()
                        .id(user.getId())
                        .name(req.getParameter("name"))
                        .city(req.getParameter("city"))
                        .sex(req.getParameter("sex"))
                        .age(Integer.valueOf(req.getParameter("age")))
                        .description(req.getParameter("description"))
                        .hobbies(hobbies)
                        .minAgePrefered(Integer.valueOf(req.getParameter("minDesiredAge")))
                        .maxAgePrefered(Integer.valueOf(req.getParameter("maxDesiredAge")))
                        .sexPrefered(req.getParameter("desiredSex"))
                        .connect(req.getParameter("howToConnect"))
                        .build();

                profiles.update(profile);
                resp.sendRedirect("/Finder/profiles/");
                return;
            }
            catch (IllegalArgumentException ex){
                req.setAttribute("message", "You must choose 5 or less hobbies");
            }
        } else {
            req.setAttribute("message", "YOU NEED TO FILL ALL THE FIELDS");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/profile.jsp").forward(req, resp);
    }
}
