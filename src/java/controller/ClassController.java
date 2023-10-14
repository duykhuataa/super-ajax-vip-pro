package controller;

import dao.ClassDAO;
import dao.CourseDAO;
import dao.ExamDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import utils.Helper;
import model.Class;
import model.User;

@WebServlet(name = "ClassController", urlPatterns = {"/class"})
public class ClassController extends HttpServlet {

    public void getListStudentByClassId(HttpServletRequest req) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("examId") != null) {
            int examId = Integer.parseInt(req.getParameter("examId"));
            req.setAttribute("listQuestions", new ExamDAO().getQuestionsListByExamId(examId));
            int index = -1;
            float check;
            while (true) {
                index++;
                check = new ExamDAO().getQuestionWeight(examId);
                System.out.println(check);
                if (check == -1) {
                    break;
                }
                req.setAttribute("score" + index, check);
            }
            req.setAttribute("examId", examId);
            req.setAttribute("exam", new ExamDAO().getExamById(examId));
            req.getRequestDispatcher("viewExam.jsp").forward(req, resp);
        } else if (req.getParameter("classId") != null) {
            int classId = Integer.parseInt(req.getParameter("classId"));
            req.setAttribute("examList", new ExamDAO().getExamListByClassId(classId));
            ArrayList<User> studentList = new ClassDAO().getStudentListByClassId(classId);
            req.setAttribute("studentList", studentList);

            req.getRequestDispatcher("class.jsp").forward(req, resp);
        } else {
            User user = (User) req.getSession().getAttribute("user");
            ArrayList<Integer> listPage = Helper.getTotalElementOfPage("Class", req.getParameter("keyValue"), String.valueOf(user.getUserId()));
            HashMap<ArrayList<Object>, Integer> hashMap = Helper.getListAndIndex(req.getParameter("index"),
                    listPage.size(), "Class", req.getParameter("keyValue"), String.valueOf(user.getUserId()));
            ArrayList<Object> classList = new ArrayList<>(hashMap.keySet().iterator().next());
            req.setAttribute("index", hashMap.values().iterator().next());

            req.setAttribute("href", req.getContextPath() + "/class");
            req.setAttribute("listPageSize", listPage.size());
            req.setAttribute("listPage", listPage);
            req.setAttribute("classList", classList);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
