package controller;

import dao.ExamDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Answer;

@WebServlet(name = "QuestionController", urlPatterns = {"/question"})
public class QuestionController extends HttpServlet {

    private void addQuestionAnswer(HttpServletRequest request, String questionText, String questionType, int courseId, int questionIndex) {
            int examId = Integer.parseInt(request.getParameter("examId"));
        float questionScore = Float.parseFloat(request.getParameter("weightName" + questionIndex));
        int questionId = new ExamDAO().addQuestion(examId,questionText, questionType, courseId,questionScore);
        int answerIndex = 0;
        try {
            while (true) {
                answerIndex++;
                String answer = request.getParameter("answerName" + questionIndex + "-" + answerIndex);
                float weight = Float.parseFloat(request.getParameter("weightAName" + questionIndex + "-" + answerIndex));
                new ExamDAO().addAnswer(answer, questionId, weight);
                if (answer == null) {
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("questions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int questionIndex = 0;
        switch (request.getParameter("action")) {
            case "question":
                try {
                while (true) {
                    questionIndex++;
                    String questionText = request.getParameter("questionName" + questionIndex);
                    String questionType = request.getParameter("flexRadioDefault" + questionIndex);
                    int courseId = 1;
                    addQuestionAnswer(request, questionText, questionType, courseId, questionIndex);
                    if (questionText == null) {
                        break;
                    }
                }
            } catch (Exception e) {
            }
            request.getRequestDispatcher("viewQuestion.jsp").forward(request, response);
            case "viewQuestion":
                int questionId = Integer.parseInt(request.getParameter("questionId"));
                String questionName = new ExamDAO().getQuestionName(questionId);
                ArrayList<Answer> listAnswers = new ExamDAO().getAnswersListByQuestionId(questionId);
                request.setAttribute("listAnswers", listAnswers);
                request.setAttribute("questionName", questionName);
                request.getRequestDispatcher("viewQuestion.jsp").forward(request, response);
        }

    }
}
