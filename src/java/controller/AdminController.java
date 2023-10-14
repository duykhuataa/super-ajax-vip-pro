package controller;

import com.google.gson.Gson;
import dao.ClassDAO;
import dao.CourseDAO;
import dao.TeacherDAO;
import dao.UserDAO;
import dto.UserServiceDTO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import model.Course;
import model.Role;
import model.User;
import utils.Helper;
import utils.Mail;
import model.Class;

@WebServlet(name = "AdminManagementController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String forwardTo = "admin/index.jsp";
        if (request.getParameter("section") != null) {
            ArrayList<Integer> listPage = new ArrayList<>();
            HashMap<ArrayList<Object>, Integer> hashMap = new HashMap<>();
            ArrayList<Object> objectList = new ArrayList<>();
            int index = 1;
            String message = (String)request.getSession().getAttribute("messProfile");
            request.getSession().removeAttribute("messProfile");
            switch (request.getParameter("section")) {
                case "users":
                    forwardTo = "admin/users.jsp";
                    ArrayList<User> userList = new UserDAO().getUserList();
                    ArrayList<UserServiceDTO> userService = new ArrayList<>();
                    int indexJson = 0;
                    for (User user : userList) {
                        indexJson +=1;
                        userService.add(new UserServiceDTO(indexJson,
                                user.getUsername(), user.getEmail(), 
                                user.getRole().getRoleName(), user.getFullName(),
                                user.getImage(), String.valueOf(user.getDateCreated()),
                                (user.getIsDeleted() == 1) ? "TRUE" : "FALSE"));
                    }
                    String userListJson = new Gson().toJson(userService);
                    listPage = Helper.getTotalElementOfPage("User", request.getParameter("keyValue"),null);

                    hashMap = Helper.getListAndIndex(request.getParameter("index"),
                            listPage.size(), "User", request.getParameter("keyValue"),null);
                    objectList = new ArrayList<>(hashMap.keySet().iterator().next());
                    index = hashMap.values().iterator().next();
                    request.setAttribute("userListJson", userListJson);
                    break;
                case "courses":
                    forwardTo = "admin/courses.jsp";
                    ArrayList<Course> courseList = new CourseDAO().getCourseList();
                    String courseListJson = new Gson().toJson(courseList);
                    listPage = Helper.getTotalElementOfPage("Course", request.getParameter("keyValue"),null);
                    hashMap = Helper.getListAndIndex(request.getParameter("index"),
                            listPage.size(), "Course", request.getParameter("keyValue"),null);
                    objectList = new ArrayList<>(hashMap.keySet().iterator().next());
                    index = hashMap.values().iterator().next();
                    request.setAttribute("courseListJson", courseListJson);
                    break;
                case "classes":
                    forwardTo = "admin/classes.jsp";
                    ArrayList<Class> classList = new ClassDAO().getClassList();
                    String classListJson = new Gson().toJson(classList);
                    listPage = Helper.getTotalElementOfPage("Class", request.getParameter("keyValue"),null);
                    hashMap = Helper.getListAndIndex(request.getParameter("index"),
                            listPage.size(), "Class", request.getParameter("keyValue"),null);
                    objectList = new ArrayList<>(hashMap.keySet().iterator().next());
                    index = hashMap.values().iterator().next();
                    request.setAttribute("courseList", new CourseDAO().getCourseList());
                    request.setAttribute("classListJson", classListJson);
                    request.setAttribute("teacherList", new TeacherDAO().getTeacherList());
                    break;
                case "exams":
                    forwardTo = "admin/exams.jsp";
                    break;
                default:
                    throw new AssertionError();
            }
            request.setAttribute("messProfile", message);
            request.setAttribute("index", index);
            request.setAttribute("key", request.getParameter("keyValue"));
            request.setAttribute("href", request.getContextPath() + "/admin?section=" + request.getParameter("section"));
            request.setAttribute("listPageSize", listPage.size());
            request.setAttribute("listPage", listPage);
            request.setAttribute("objectList", objectList);
        }

        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        int roleId = Integer.parseInt(request.getParameter("role"));
        new UserDAO().createUser(username, email, roleId);
        String password = Helper.generatePassword();
        new UserDAO().changePassword(request.getParameter("email"), password);
        Mail.send(email, "EMS", "UserName: " + username + "\nPassword: " + password);
    }

    private void createClass(HttpServletRequest request, HttpServletResponse response) {
        String className = request.getParameter("className");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        new ClassDAO().createClass(className, courseId, teacherId);
    }
    
    private void updateClass(HttpServletRequest request, HttpServletResponse response){
        String className = request.getParameter("className");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        new ClassDAO().updateClass(courseId, className, teacherId);
    }

    private void createCourse(HttpServletRequest request, HttpServletResponse response) {
        String courseCode = request.getParameter("courseCode");
        String courseName = request.getParameter("courseName");
        new CourseDAO().createCourse(courseCode, courseName);
    }
    
    private void updateCourse(HttpServletRequest request, HttpServletResponse response){
        String courseName = request.getParameter("courseName");
        new CourseDAO().updateCourse(courseName);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchCode = "";
        switch (request.getParameter("action")) {
            case "createUser":
                this.createUser(request, response);
                break;
            case "deleteUser":
                new UserDAO().deleteUser(request.getParameter("userId"));
                break;
            case "createClass":
                this.createClass(request, response);
                break;
            case "deleteClass":
                new ClassDAO().deleteClass(request.getParameter("classId"));
                break;
            case "updateClass":
                this.updateClass(request, response);
                break;
            case "createCourse":
                this.createCourse(request, response);
                break;
            case "deleteCourse":
                new CourseDAO().deleteCourse(request.getParameter("courseId"));
                break;
            case "updateCourse":
                this.updateCourse(request, response);
                break;    
            default:
                throw new AssertionError();
        }

        response.sendRedirect("./admin" + (request.getParameter("queryString") != null
                ? "?" + request.getParameter("queryString")
                : ""));
//                response.sendRedirect("./admin?" + request.getParameter("queryString") + "&searchCode=" + searchCode);

    }
}
