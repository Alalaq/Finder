package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.repositories.MutualsRepository;
import ru.kpfu.itis.repositories.ProfilesRepository;
import ru.kpfu.itis.repositories.impl.MutualsRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.repositories.impl.ProfilesRepositoryJdbcTemplateImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@WebServlet(urlPatterns = "/personal_account/", name = "PersonalAccountServlet")
public class PersonalAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));
        Profile profile = profiles.getProfileById(user.getId());

        MutualsRepository mutualsRepository = new MutualsRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));
        List<Long> mutuals = mutualsRepository.getAllMutualsOfUser(user);
        List<String> realMutuals = new ArrayList<>();

        String currString;
        if (mutuals != null) {

            for (Long key : mutuals) {
                Profile mutualLikedUser = profiles.getProfileById(key);
                currString = mutualLikedUser.getName() + ", " + mutualLikedUser.getAge() + ": " + mutualLikedUser.getConnect();
                realMutuals.add(currString);
            }

            if (realMutuals.size() != 0) {
                req.setAttribute("mutuals", realMutuals);
            }
        }

        fullFillTheFields(req, profile);

        if (req.getParameter("logout") != null) {
            session.removeAttribute("user");
            session.invalidate();
            resp.sendRedirect("/Finder/main_page/");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/personal_acc.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/personal_acc.jsp").forward(req, resp);
    }


    protected static void fullFillTheFields(HttpServletRequest req, Profile profile) {
        req.setAttribute("name", profile.getName());
        req.setAttribute("city", profile.getCity());
        req.setAttribute("age", profile.getAge());
        req.setAttribute("description", profile.getDescription());
        req.setAttribute("hobbies", profile.getHobbies());
    }
}
