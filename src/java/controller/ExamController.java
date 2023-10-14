package controller;

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
import java.util.Map;
import model.Answer;
import model.Question;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import model.Course;
import model.Exam;

@WebServlet(name = "ExamController", urlPatterns = {"/exam"})
public class ExamController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        Exam exam = new ExamDAO().getExamById(examId);
        ArrayList<Question> listQuestions = new ExamDAO().getQuestionsListByExamId(examId);
        Map<Question, ArrayList<Answer>> questionAnswerMap = new HashMap<>();
        for (Question lq : listQuestions) {
            ArrayList<Answer> listAnswer = new ExamDAO().getAnswersListByQuestionId(lq.getQuestionID());
            questionAnswerMap.put(lq, listAnswer);
        }
        ArrayList<Question> modifiedListQuestions = new ArrayList<>(listQuestions.subList(0, listQuestions.size()));

        request.setAttribute("questionCounts", listQuestions.size());
        request.setAttribute("questionAnswerMap", questionAnswerMap);
        request.setAttribute("listQuestions", modifiedListQuestions);
        request.setAttribute("exam", exam);
        request.getRequestDispatcher("questions.jsp").forward(request, response);
    }

    private Timestamp getTimestampFromString(String date) {
        try {
            return new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
                    .parse(date).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int createExam(HttpServletRequest req) {
        String examName = req.getParameter("examName");
        int classId = Integer.parseInt(req.getParameter("classId"));

        Timestamp dateStart = getTimestampFromString(req.getParameter("dateStart"));
        Timestamp dateEnd = getTimestampFromString(req.getParameter("dateEnd"));

        int maxAttempts = Integer.parseInt(req.getParameter("maxAttempts"));

        int durationHours = Integer.parseInt(req.getParameter("durationHours"));
        int durationMinutes = Integer.parseInt(req.getParameter("durationMinutes"));
        int duration = durationHours * 3600 + durationMinutes * 60;

        byte isDisplayAnswers = Byte.parseByte(req.getParameter("isDisplayAnswers"));
        byte isVisible = Byte.parseByte(req.getParameter("isVisible"));

        return new ExamDAO().createExam(examName, classId, isVisible,
                Exam.STATUS_NOT_STARTED, dateStart, dateEnd, duration, isDisplayAnswers, maxAttempts);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int classId = Integer.parseInt(request.getParameter("classId"));
        
        switch (request.getParameter("action")) {
            case "createExam":
                this.createExam(request);
                response.sendRedirect("./class?classId=" + classId);
                break;
            case "exam":
                int examId = Integer.parseInt(request.getParameter("examId"));
                String examName = request.getParameter("examName");
        String isVisible = (request.getParameter("isVisible").equalsIgnoreCase("public"))? "True" : "False";
        String status = request.getParameter("status");
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        int duration = Integer.parseInt(request.getParameter("duration"));
        
        String isDisplay =  ((request.getParameter("isDisplay")).equalsIgnoreCase("public"))? "True" : "False";
        int maxAttempts = Integer.parseInt(request.getParameter("maxAttempts"));
        String dateCreated = request.getParameter("dateCreated");
        String deleted = (request.getParameter("deleted").equalsIgnoreCase("public"))? "True" : "False";
        System.out.println(classId+examName+isVisible+status+dateStart+dateEnd+duration+isDisplay+maxAttempts+dateCreated+deleted);
        boolean exam = new ExamDAO().updateExam(examId, examName, classId, isVisible, status, dateStart, dateEnd, duration, isDisplay, maxAttempts, dateCreated, deleted);
                response.sendRedirect("./class?classId=" +classId+"&examId="+examId);
        break;

            case "question":
                examId = Integer.parseInt(request.getParameter("examId"));
                        new ExamDAO().deleteExamQuestions(examId);
                int questionIndex = 0;
                boolean check = true;
                try {
                    while (true) {
                        questionIndex++;
                        String questionText = request.getParameter("questionName" + questionIndex);
                        String questionType = request.getParameter("flexRadioDefault" + questionIndex);
                        float score = Float.parseFloat(request.getParameter("weightName" + questionIndex));
                        int courseId = new CourseDAO().getCourseIdByClassId(classId);
                        if (questionType == null) {
                            break;
                        }
                        addQuestionAnswer(request, questionText, questionType, courseId, questionIndex, score, examId);
                        
                    }
                } catch (Exception e) {
                }
                response.sendRedirect("./class?classId=" + classId + "&examId=" + examId);
                break;

        }
    }

    private void addQuestionAnswer(HttpServletRequest request, String questionText, String questionType, int courseId, int questionIndex, float score, int examId) {
        float questionScore = Float.parseFloat(request.getParameter("weightName" + questionIndex));
        int questionId = new ExamDAO().addQuestion(examId, questionText,questionType, courseId, questionScore);
        int answerIndex = 0;
        ExamDAO examdao = new ExamDAO();
        examdao.insertExamQuestion(examId, questionId, questionScore);
            while (true) {
                answerIndex++;
                String answer = request.getParameter("answerName" + questionIndex + "-" + answerIndex);
                if (answer == null) {
                    break;
                }
                float weight = Float.parseFloat(request.getParameter("weightAName" + questionIndex + "-" + answerIndex));
                int answerId = new ExamDAO().addAnswer(answer, questionId, weight);
                examdao.insertQuestionAnswer(questionId, answerId, weight);
            }
    }
}

