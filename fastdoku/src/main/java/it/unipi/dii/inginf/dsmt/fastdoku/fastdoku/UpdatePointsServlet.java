package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku;

import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import it.unipi.dii.inginf.dsmt.fastdoku.persistence.LevelDBUser;

import javax.servlet.RequestDispatcher;
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
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        LevelDBUser levelDBUser = LevelDBUser.getInstance();
        int points = Integer.parseInt(request.getParameter("points"));
        int myPoints = points + user.getPoints();
        if (myPoints < 0)
            myPoints = 0;
        levelDBUser.updateScore(user.getUsername(), myPoints);
        user.setPoints(myPoints);
        session.setAttribute("loggedUser", user);
        session.setAttribute("earnedPoints", points);

        //for the ranking
        int dailyPoints = levelDBUser.getDaylyScore(user.getUsername()) + points;
        levelDBUser.setDaylyScore(user.getUsername(), dailyPoints);
        /*-------------------------------------------------------------------*/

        response.sendRedirect("main.jsp");
        goToPage("main.jsp", request, response);
    }

    /**
     * Function that with the dispatcher send the user to another page
     * @param page          Page to show
     * @param request       Request object
     * @param response      Response object
     * @throws ServletException
     * @throws IOException
     */
    private void goToPage (String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
        if (requestDispatcher != null)
            requestDispatcher.include(request, response);
    }
}
