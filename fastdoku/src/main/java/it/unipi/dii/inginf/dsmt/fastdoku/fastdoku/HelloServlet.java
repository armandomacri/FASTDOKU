package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku;

import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import it.unipi.dii.inginf.dsmt.fastdoku.persistence.LevelDBUser;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "accessServlet", value = "/access-servlet")
public class HelloServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatpassword = request.getParameter("repeatpassword");
        LevelDBUser levelDBUser = LevelDBUser.getInstance();
        HttpSession session = request.getSession();

        //login
        if (request.getParameter("loginButton") != null) {

            User user = levelDBUser.login(username, password);
            if (user != null) {
                session.setAttribute("loggedUser", user);
                goToPage("main.jsp", request, response);
            }
            else {
                out.print("Username or password wrong");
                goToPage("index.jsp", request, response);
            }
        }
        else { // If the user has required a register operation
            if (password.equals(repeatpassword)){
                if (levelDBUser.isRegistered(username)){ //The username is already in use
                    out.print("Sorry, the username is already in use!");
                    Metod.goToPage("index.jsp", request, response);
                } else if (Pattern.matches("^[a-zA-Z0-9_.]*$", username)) { //If the username is correctly formatted
                    levelDBUser.signin(username, password);
                    session.setAttribute("loggedUser", new User(username, password));
                    goToPage("main.jsp", request, response);
              } else {
                    out.print("Username not valid! Please use alphanumeric chars, underscore and dot");
                    goToPage("main.jsp", request, response);
                }
            }
        }
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