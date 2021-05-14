package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku;

import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import it.unipi.dii.inginf.dsmt.fastdoku.persistence.LevelDBUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "updateServlet", value = "/update-servlet")
public class UpdatePointsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest (request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest (request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        LevelDBUser levelDBUser = LevelDBUser.getInstance();
        int points = Integer.parseInt(request.getParameter("points"));
        int myPoints = points + user.getPoints();
        if (myPoints < 0)
            myPoints = 0;
        levelDBUser.updateScore(user.getUsername(), points);
        user.setPoints(myPoints);
        session.setAttribute("loggedUser", user);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(Integer.toString(points));
    }
}
