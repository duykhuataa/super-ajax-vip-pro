package controller;

import dao.ClassDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Array;
import java.util.ArrayList;
import model.Class;
import model.User;

@WebServlet(name = "ProcessController", urlPatterns = {"/process"})
public class ProcessController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String enroll = request.getParameter("classEnrollKey");
        User user = (User) request.getSession().getAttribute("user");
        ClassDAO dao = new ClassDAO();
        Class clas = dao.getClassByEnroll(enroll);
        if (clas != null) {
            dao.addStudentToClass(clas.getClassId(), user.getUserId());
            String mess = "Enroll Class Successfully";
            request.setAttribute("mess", mess);
            request.getRequestDispatcher("class.jsp").forward(request, response);
        } else {
            String mess = "Enroll invalid !!!";
            request.setAttribute("mess", mess);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "searchClass":
                ArrayList<Class> listClass = new ArrayList<>();
                for (Class classes : new ClassDAO().getClassList()) {
                    if(classes.getClassCode().equalsIgnoreCase(request.getParameter("classcode").trim())) {
                        listClass.add(classes);
                    }
                }
                request.setAttribute("listClass",listClass);
                request.getRequestDispatcher("Class.jsp").forward(request, response);
                break;
            default:
                throw new AssertionError();
        }
        
    }
    
}
