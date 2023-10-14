package controller;

import com.google.gson.Gson;
import dao.ExamDAO;
import dto.AnswerDTO;
import dto.QuestionDTO;
import dto.SelectedChoiceDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Answer;
import model.Exam;

import model.Question;

@WebServlet(name = "ExamAttempt", urlPatterns = {"/attempt"})

public class ExamAttempt extends HttpServlet {

    private final ExamDAO examDAO = new ExamDAO();

    private int examId = 11; // will be passed from another function
    
    private int timeRemaining = -1;
    private Exam exam = new ExamDAO().getExamByExamId(examId);

    private ArrayList<QuestionDTO> questionDtoList = new ArrayList<>();
    private HashMap<Integer, List<String>> submittedChoices = new HashMap<>();

    private void initNeededVariables(HttpServletRequest req, HttpServletResponse resp) {
        timeRemaining = exam.getDuration();
        
        ArrayList<Question> questionList = examDAO.getQuestionsListByExamId(examId);

        for (int i = 0; i < questionList.size(); i++) {
            Question _q = questionList.get(i);
            questionDtoList.add(new QuestionDTO(i + 1, 
                    _q.getQuestionID(), _q.getQuestionText(), 
                    _q.getQuestionType(), examDAO.getAnswersListByQuestionId(_q.getQuestionID())
            ));
        }
    }

    private void getQuestionAtIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int questionIndex = Integer.parseInt(req.getParameter("questionIndex")) - 1;

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        resp.getWriter().write(
                new Gson().toJson(questionDtoList.get(questionIndex))
        );
    }

    private void getSubmittedChoices(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int currentQuestionId = Integer.parseInt(req.getParameter("questionId"));
        
        List<String> _submittedAnswerIds = submittedChoices.get(currentQuestionId);

        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(
                new Gson().toJson(_submittedAnswerIds)
        );
    }
    
    private void submitChoices(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        timeRemaining = Integer.parseInt(req.getParameter("timeRemaining"));
        String selectedChoicesJSON = req.getParameter("selectedChoicesJSON");
        
        SelectedChoiceDTO choiceDto = new Gson().fromJson(selectedChoicesJSON,
                SelectedChoiceDTO.class);

        submittedChoices.put(choiceDto.getQuestionId(),
                choiceDto.getSelectedAnswerIds()
        );
    }

//    final step
    private void submitExam(HttpServletRequest req, HttpServletResponse resp) {
        timeRemaining = Integer.parseInt(req.getParameter("timeRemaining"));
        
        System.out.flush(); // remove trailing '\n' when print. not nescessary, only for better sout to debug
        System.out.println("Exam ID: " + exam.getExamId() + " - time remaining: " + timeRemaining + "s");
        for (Map.Entry<Integer, List<String>> entry : submittedChoices.entrySet()) {
            System.out.print("questionId: " + entry.getKey() + " - selected answerIds: ");
            for (String ansId : entry.getValue()) {
                System.out.print(ansId + " ");
            }
            System.out.println("");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("action") == null) {
            if (questionDtoList.isEmpty() && submittedChoices.isEmpty()) {
                this.initNeededVariables(req, resp);
            }

            req.setAttribute("duration", timeRemaining);
            req.setAttribute("questionsSize", questionDtoList.size());
            req.getRequestDispatcher("doQuiz.jsp").forward(req, resp);
        }

        switch (req.getParameter("action")) {
            case "getQuestionAtIndex":
                this.getQuestionAtIndex(req, resp);
                break;
            case "getSubmittedChoices":
                this.getSubmittedChoices(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("action") != null) {
            switch (req.getParameter("action")) {
                case "submitChoices":
                    this.submitChoices(req, resp);
                    break;
                case "submitExam": // final step
                    this.submitExam(req, resp);
//                    redirect to another url after this
//                    resp.sendRedirect()...
                    break;
            }
        }
    }

}
