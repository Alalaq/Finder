package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Like;
import ru.kpfu.itis.entities.Profile;
import ru.kpfu.itis.entities.User;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.repositories.impl.LikesRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.repositories.impl.MutualsRepositoryJdbcTemplateImpl;
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
import java.util.Objects;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@WebServlet(urlPatterns = "/profiles/", name = "CatalogueServlet")
public class CatalogueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        updateLastViewedUser(req);

        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/catalogue.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("Like") != null || req.getParameter("Skip") != null) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (req.getParameter("Like") != null) {
                LikesRepository likes = new LikesRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));

                Long userWhosBeenLiked = user.getLastViewedUser();
                Long userWhoLiked = user.getId();

                Like like = Like.builder().idOfUser(userWhosBeenLiked).idOfUserWhoLiked(userWhoLiked).build();

                if (!(Objects.equals(userWhoLiked, userWhosBeenLiked))) {
                    if (likes.checkIfTheresLike(userWhoLiked, userWhosBeenLiked)) {
                        MutualsRepository mutuals = new MutualsRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));
                        ProfilesRepository profiles = new ProfilesRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));

                        mutuals.add(profiles.getProfileById(userWhoLiked), profiles.getProfileById(userWhosBeenLiked));

                        likes.deleteLike(Like.builder().idOfUser(userWhoLiked).idOfUserWhoLiked(userWhosBeenLiked).build());

                        req.setAttribute("message", "You have mutual sympathy! Check it in your profile.");
                    } else if (!likes.checkIfTheresLike(userWhosBeenLiked, userWhoLiked)) {
                        likes.add(like);
                    }
                }
                updateLastViewedUser(req);
            } else if (req.getParameter("Skip") != null) {
                updateLastViewedUser(req);
            }

        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/pages/catalogue.jsp").forward(req, resp);
    }

    private void updateLastViewedUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UsersRepository users = new UsersRepositoryJdbcTemplateImpl((DataSource) getServletContext().getAttribute("dataSource"));

        User user = (User) session.getAttribute("user");
        user.setLastViewedUser(profileProcess(req));
        users.updateLastViewed(user.getId(), user.getLastViewedUser());
    }


    private static Long profileProcess(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Long lastViewedUserId = user.getLastViewedUser();

        ProfilesRepository profilesRepository = new ProfilesRepositoryJdbcTemplateImpl((DataSource) req.getServletContext().getAttribute("dataSource"));

        Profile usersProfile = profilesRepository.getProfileById(user.getId());
        Profile profile = null;

        if (lastViewedUserId != null) {
            if (profilesRepository.getMaxId() <= lastViewedUserId) {
                req.setAttribute("message", "You have reviewed all the questionnaires at the moment. Wait for a while or change the filters.");
                return lastViewedUserId;
            }
            if ((lastViewedUserId + 1) == user.getId()) {
                lastViewedUserId++;
                return lastViewedUserId;
            }
            if (!profilesRepository.getMaxId().equals(lastViewedUserId)) {
                lastViewedUserId++;
                profile = profilesRepository.getProfileById(lastViewedUserId);
            }
        } else if (!user.getId().equals(1L)) {
            profile = profilesRepository.getProfileById(1L);
            lastViewedUserId = 1L;
        } else {
            profile = profilesRepository.getProfileById(2L);
            lastViewedUserId = 2L;
        }
        Integer minAge = usersProfile.getMinAgePrefered();
        Integer maxAge = usersProfile.getMaxAgePrefered();
        String sexPreffered = usersProfile.getSexPrefered();

        assert profile != null;
        if (profile.getAge() > maxAge || profile.getAge() < minAge) {
            user.setLastViewedUser(lastViewedUserId++);
            session.setAttribute("user", user);
            profileProcess(req);
        } else if (!sexPreffered.equals("Any")) {
            if (!profile.getSex().equals(sexPreffered)) {
                lastViewedUserId++;
                profileProcess(req);
            } else {
                fullFillTheFields(req, profile);
                return lastViewedUserId;
            }
        }
        return lastViewedUserId;
    }

    protected static void fullFillTheFields(HttpServletRequest req, Profile profile) {
        req.setAttribute("name", profile.getName());
        req.setAttribute("city", profile.getCity());
        req.setAttribute("age", profile.getAge());
        req.setAttribute("description", profile.getDescription());
        req.setAttribute("hobbies", profile.getHobbies());
    }
}
