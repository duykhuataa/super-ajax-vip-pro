package dao;

import connection._DBContext;
import dto.AnswerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Answer;
import model.Exam;
import model.Question;
import model.*;

public class ExamDAO extends _DBContext {

    public int createExam(String examName, int classId, int isVisible,
            int status, Timestamp dateStart, Timestamp dateEnd, int duration,
            int isDisplayDetails, int maxAttempts) {
        String sql = "insert into Exam (ExamName, ClassId, IsVisible, "
                + "Status, DateStart, DateEnd, Duration, IsDisplayDetails, MaxAttempts)\n"
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, examName);
            ps.setInt(2, classId);
            ps.setInt(3, isVisible);
            ps.setInt(4, status);
            ps.setTimestamp(5, dateStart);
            ps.setTimestamp(6, dateEnd);
            ps.setInt(7, duration);
            ps.setInt(8, isDisplayDetails);
            ps.setInt(9, maxAttempts);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int findExamId(String examName, int classId, String isVisible,
            String status, String dateStart, String dateEnd, int duration,
            String isDisplayDetails, int maxAttempts, String dateCreated, String isDeleted, int examId) {
        String sql = "SELECT [ExamId]\n"
                + "  FROM [dbo].[Exam]\n"
                + "  where [ExamName] = ?\n"
                + "       and [ClassId] = ?\n"
                + "      and [IsVisible] = ?\n"
                + "      and [Status] = ?\n"
                + "      and [DateStart] = ?\n"
                + "      and [DateEnd] = ?\n"
                + "      and [Duration] = ?\n"
                + "      and [IsDisplayDetails] = ?\n"
                + "      and [MaxAttempts] = ?\n"
                + "      and [DateCreated] = ?\n"
                + "      and [IsDeleted] = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, examName);
            ps.setInt(2, classId);
            ps.setString(3, isVisible);
            ps.setString(4, status);
            ps.setString(5, dateStart);
            ps.setString(6, dateEnd);

            ps.setInt(7, duration);
            ps.setString(8, isDisplayDetails);
            ps.setInt(9, maxAttempts);
            ps.setString(10, dateCreated);
            ps.setString(11, isDeleted);
            ps.setInt(12, examId);

            ps.executeQuery();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateExam(int examId, String examName, int classId, String isVisible,
            String status, String dateStart, String dateEnd,
            int duration, String isDisplayDetails, int maxAttempts,
            String dateCreated, String isDeleted) {
        String sql = "UPDATE [dbo].[Exam] "
                + "SET [ExamName] = ?, "
                + "    [ClassId] = ?, "
                + "    [IsVisible] = ?, "
                + "    [Status] = ?, "
                + "    [DateStart] = ?, "
                + "    [DateEnd] = ?, "
                + "    [Duration] = ?, "
                + "    [IsDisplayDetails] = ?, "
                + "    [MaxAttempts] = ?, "
                + "    [DateCreated] = ?, "
                + "    [IsDeleted] = ? "
                + "WHERE [ExamId] = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, examName);
            ps.setInt(2, classId);
            ps.setString(3, isVisible);
            ps.setString(4, status);
            ps.setString(5, dateStart);
            ps.setString(6, dateEnd);
            ps.setInt(7, duration);
            ps.setString(8, isDisplayDetails);
            ps.setInt(9, maxAttempts);
            ps.setString(10, dateCreated);
            ps.setString(11, isDeleted);
            ps.setInt(12, examId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addExamAttempt(int userId, int examId, int status, Timestamp dateStart) {
        String sql = "INSERT INTO [dbo].[ExamAttempt]\n"
                + "           ([UserId]\n"
                + "           ,[ExamId]\n"
                + "           ,[Status]\n"
                + "           ,[DateStart])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, userId);
            ps.setInt(2, examId);
            ps.setInt(3, status);
            ps.setTimestamp(4, dateStart);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAttemptChoice(int examAttemptId, int questionId, int answerId) {
        String sql = "insert into [AttemptChoice] (ExamAttemptId,QuestionId,AnswerId) values\n"
                + "(?,?,?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, examAttemptId);
            ps.setInt(2, questionId);
            ps.setInt(3, answerId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<ExamAttempt> getListExamAttemp() {
        ArrayList<ExamAttempt> examAttemptList = new ArrayList<>();
        String sql = "select * from [ExamAttempt]";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                examAttemptList.add(new ExamAttempt(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(5), rs.getFloat(6), rs.getTimestamp(7), rs.getInt(8), rs.getByte(9)));
            }
            return examAttemptList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateFinishExam(int examAttemptId) {
        String sql = "Update [ExamAttempt] set Status = " + ExamAttempt.STATUS_FINISHED + " where ExamAttemptId = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, examAttemptId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int addQuestion(int examId, String questionText, String questionType, int courseId, float questionScore) {
        String sql = "INSERT INTO [dbo].[Question] ([QuestionText], [QuestionType], [CourseId], [DateCreated], [IsDeleted])\n"
                + "OUTPUT INSERTED.QuestionId\n"
                + "VALUES (?, ?, ?, DATEADD(HOUR, +7, GETDATE()), 0)";
        String questionTypeNumber = (questionType.equalsIgnoreCase("onechoice")) ? "True" : "False";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, questionText);
            ps.setString(2, questionTypeNumber);
            ps.setInt(3, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int questionId = rs.getInt(1);
                return questionId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertQuestionAnswer(int questionId, int answerId, float weight) {
        String sql = "INSERT INTO [dbo].[QuestionAnswer] "
                + "([QuestionId], [AnswerId], [Weight]) "
                + "VALUES (?, ?, ?)";
        try ( Connection con = new _DBContext().getConnection();  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, questionId);
            ps.setInt(2, answerId);
            ps.setFloat(3, weight);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertExamQuestion(int examId, int questionId, float questionScore) {
        String sql = "INSERT INTO [dbo].[ExamQuestion] "
                + "([ExamId], [QuestionId], [QuestionScore]) "
                + "VALUES (?, ?, ?)";
        try ( Connection con = connection;  PreparedStatement pstate = con.prepareStatement(sql)) {
            pstate.setInt(1, examId);
            pstate.setInt(2, questionId);
            pstate.setFloat(3, questionScore);
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addAnswer(String answerText, int questionId, float weight) {
        String sql = "insert into Answer (AnswerText)\n"
                + "OUTPUT INSERTED.AnswerId\n"
                + "                values (?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, answerText);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int answerId = rs.getInt(1);
                return answerId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Exam getExamByExamId(int examId) {
        String queryStr = "select *\n"
                + "from Exam\n"
                + "where ExamId = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(queryStr);
            stm.setInt(1, examId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Exam(rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getByte(4),
                        rs.getInt(5), rs.getTimestamp(6),
                        rs.getTimestamp(7), rs.getInt(8),
                        rs.getByte(9), rs.getInt(10),
                        rs.getTimestamp(11), rs.getByte(12));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Exam> getExamListByClassId(int classId) {
        String queryStr = "select *\n"
                + "from Exam\n"
                + "where ClassId = ?";

        ArrayList<Exam> examList = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(queryStr);
            stm.setInt(1, classId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                examList.add(new Exam(rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getByte(4),
                        rs.getInt(5), rs.getTimestamp(6),
                        rs.getTimestamp(7), rs.getInt(8),
                        rs.getByte(9), rs.getInt(10),
                        rs.getTimestamp(11), rs.getByte(12)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examList;
    }

    public boolean deleteAnswer(int answerId) {
        String sql = "DELETE FROM [dbo].[Answer]\n"
                + "      WHERE AnswerId = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, answerId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteQuestion(int questionId) {
        String sql = "DELETE FROM [dbo].[Question]\n" + "WHERE QuestionId=?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, questionId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteQuestionAnswers(int questionId) {
        String sql = "DELETE FROM [dbo].[QuestionAnswer]\n" + "WHERE QuestionId = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, questionId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteExamQuestions(int examId) {
        String sql = "BEGIN TRANSACTION;\n"
                + "BEGIN TRY\n"
                + "    DELETE  FROM [dbo].[QuestionAnswer]\n"
                + "    WHERE QuestionId IN (SELECT QuestionId FROM [dbo].[ExamQuestion] WHERE [ExamId] = ?);\n"
                + "\n"
                + "	DELETE  FROM [dbo].[ExamQuestion]\n"
                + "    WHERE [ExamId] = ?;\n"
                + "\n"
                + "    COMMIT;\n"
                + "END TRY\n"
                + "BEGIN CATCH\n"
                + "    ROLLBACK;\n"
                + "END CATCH;";
        try ( Connection con = new _DBContext().getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setInt(2, examId);
            ps.executeUpdate();
                    System.out.println("đã delete rồiiiiiiiiiiii");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteExam(int examId) {
        String sql = "DELETE FROM [dbo].[Exam]\n"
                + "WHERE [ExamId] = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Answer> getAnswersListByQuestionId(int questionId) {
        ArrayList<Answer> answerList = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM [dbo].[QuestionAnswer] AS QA\n"
                + "INNER JOIN [dbo].[Answer] AS A\n"
                + "ON QA.[AnswerId] = A.[AnswerId]\n"
                + "Where QA.[QuestionId] = ?;";
        try ( Connection cnn = new _DBContext().getConnection();  PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                answerList.add(new Answer(rs.getInt(4), rs.getString(5), rs.getTimestamp(6), rs.getByte(7)));
            }
            return answerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<AnswerDTO> getAnswersDTOListByQuestionId(int questionId) {
        ArrayList<AnswerDTO> answerList = new ArrayList<>();
        String sql = "SELECT A.AnswerId, A.AnswerText\n"
                + "                FROM [dbo].[QuestionAnswer] AS QA\n"
                + "                INNER JOIN [dbo].[Answer] AS A\n"
                + "ON QA.[AnswerId] = A.[AnswerId]"
                + "Where QA.[QuestionId] = ?;";
        try ( Connection cnn = connection;  PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                answerList.add(new AnswerDTO(rs.getInt(1), rs.getString(2)));
            }
            return answerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Question> getQuestionsListByExamId(int examId) {
        ArrayList<Question> questionsList = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[ExamQuestion] AS EQ\n"
                + "INNER JOIN [dbo].[Question] AS Q\n"
                + "ON EQ.QuestionId = Q.QuestionId\n"
                + "Where EQ.[ExamId] = ?";
        try ( Connection cnn = new _DBContext().getConnection();  PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questionsList.add(new Question(rs.getString(5), rs.getInt(4), rs.getInt(7), rs.getTimestamp(8), rs.getByte(6), rs.getByte(9)));
            }
            return questionsList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQuestionName(int questionId) {
        String sql = "SELECT [QuestionText]\n"
                + "FROM [dbo].[Question]\n"
                + "WHERE [QuestionId] = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getAnswerWeight(int answerId) {
        String sql = "SELECT [Weight]\n"
                + "  FROM [dbo].[QuestionAnswer]\n"
                + "  WHERE [AnswerId] = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, answerId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getFloat(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getQuestionWeight(int questionId) {
        String sql = "SELECT [QuestionScore]\n"
                + "  FROM [dbo].[ExamQuestion]\n"
                + "  WHERE [QuestionId] = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getFloat(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Exam getExamById(int examId) {
        String sql = "select * from Exam where ExamId = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, examId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Exam(rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getByte(4),
                    rs.getInt(5), rs.getTimestamp(6),
                    rs.getTimestamp(7), rs.getInt(8),
                    rs.getByte(9), rs.getInt(10),
                    rs.getTimestamp(11), rs.getByte(12)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
