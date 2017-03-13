package com.gudperna.controller;

import com.gudperna.util.ConnectionUtil;
import com.gudperna.dao.UserDAO;
import com.gudperna.dao.impl.UserDAOImpl;

import com.gudperna.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class UserController extends HttpServlet {

    // private String message;

    public void init() throws ServletException
    {
        // message = "Hello Gugun Dwi Permana";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            UserDAO userService = new UserDAOImpl(ConnectionUtil.getConnection());
            // //request.getParameterMap().containKey("act")
            if(request.getParameter("act") != null) {
                String act = request.getParameter("act");
                if(act.equals("insert")) {
                    User user = userService.prepareFormInsert();
                    request.setAttribute("user", user);
                    RequestDispatcher rd = request.getRequestDispatcher("userForm.jsp");
                    rd.forward(request, response);
                }
                if(act.equals("edit")) {
                    String id = request.getParameter("id");
                    User user = userService.prepareFormEdit(Integer.parseInt(id));
                    request.setAttribute("user", user);
                    RequestDispatcher rd = request.getRequestDispatcher("userForm.jsp");
                    rd.forward(request, response);
                }
                if(act.equals("delete")) {
                    String id = request.getParameter("id");
                    userService.delete(Integer.parseInt(id));
                    
                    response.sendRedirect("users");
                }

            } else {

                request.setAttribute("listUser", userService.getAll());
                RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
                rd.forward(request, response);
            }

        } catch(ServletException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userService = new UserDAOImpl(ConnectionUtil.getConnection());
        
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);

        String action = request.getParameter("action");

        if(action.equals("insert")) {
            userService.insert(user);
        } else {
            userService.edit(user);
        }
        
        // redirect
        response.sendRedirect("users");

    }

    public void destroy()
    {
        //
    }
}