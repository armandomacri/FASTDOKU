package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku;

import it.unipi.dii.inginf.dsmt.fastdoku.fastdoku.persistence.LevelDBManager;


import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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

        // Hello
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatpassword = request.getParameter("repeatpassword");
        LevelDBManager levelDBmanager = LevelDBManager.getInstance();
        HttpSession session = request.getSession();


        //login
        if (request.getParameter("loginButton") != null)
        {

            User user = levelDBmanager.login(username, password);
            if (user != null)
            {
                session.setAttribute("loggedUser", user);
                  //      Metod.goToPage("Game.jsp", request, response); //aggiungere Game.jsp per giocare
                out.println("<html><body>");
                out.println("<h1>" + username + "</h1>");
                out.println("<h1>" + password + "</h1>");
                out.println("</body></html>");
            }
            else{
                out.print("Username or password wrong");
                Metod.goToPage("index.jsp", request, response);
            }
        }
        else // If the user has required a register operation
        { //if (password.equals(repeatpassword)){
           /* if (levelDBmanager.isRegistered(username)) //The username is already in use
            {
                out.print("Sorry, the username is already in use!");
                Metod.goToPage("index.jsp", request, response);

            } else {*/
                // If the username is correctly formatted
           //     if (Pattern.matches("^[a-zA-Z0-9_.]*$", username)) {

             levelDBmanager.signin(username, password);
                    out.println("<html><body>");
                    out.println("<h1>" + username + "</h1>");
                    out.println("<h1>" + password + "</h1>");
                    out.println("</body></html>");
                    System.out.println("ok 10");
                    session.setAttribute("loggedUser", new User(username, password));

                  //  HelloServlet.goToPage("Game.jsp", request, response);
              //  } else {
               //     out.print("Username not valid! Please use alphanumeric chars, underscore and dot");
                    //Metod.goToPage("index.jsp", request, response);
            //    }
       // }
            }

       // LevelDBManager a = new LevelDBManager();
        // a.signin(username, password);
        out.println("<html><body>");
        out.println("<h1>" + username + "</h1>");
        out.println("<h1>" + password + "</h1>");
        out.println("</body></html>");
        out.close();
    }


}