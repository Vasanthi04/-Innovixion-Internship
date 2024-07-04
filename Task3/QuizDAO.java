package com.project;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class QuizDAO {
    private static final Logger LOGGER = Logger.getLogger(QuizDAO.class.getName());

    private Connection conn;

    public QuizDAO() {
        try {
            // Initialize database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "root", "VasaNthi24&");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while initializing QuizDAO", e);
        }
    }

    public void addQuestion(Question question) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO questions (question_text, type, option1, option2, option3, option4, correct_option, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
                pstmt.setString(2, "MCQ");
                pstmt.setString(3, mcq.getOptions()[0]);
                pstmt.setString(4, mcq.getOptions()[1]);
                pstmt.setString(5, mcq.getOptions()[2]);
                pstmt.setString(6, mcq.getOptions()[3]);
                pstmt.setInt(7, mcq.getCorrectOptionIndex());
                pstmt.setNull(8, Types.BOOLEAN);
            } else if (question instanceof TrueFalseQuestion) {
                TrueFalseQuestion tfq = (TrueFalseQuestion) question;
                pstmt.setString(2, "TF");
                pstmt.setNull(3, Types.VARCHAR);
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setNull(5, Types.VARCHAR);
                pstmt.setNull(6, Types.VARCHAR);
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setBoolean(8, tfq.getCorrectAnswer());
            }

            pstmt.setString(1, question.getQuestionText());
            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while adding question", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while adding question", e);
        }
    }

    public void updateQuestion(Question question, int questionId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE questions SET question_text = ?, type = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, correct_option = ?, correct_answer = ? WHERE id = ?");

            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
                pstmt.setString(2, "MCQ");
                pstmt.setString(3, mcq.getOptions()[0]);
                pstmt.setString(4, mcq.getOptions()[1]);
                pstmt.setString(5, mcq.getOptions()[2]);
                pstmt.setString(6, mcq.getOptions()[3]);
                pstmt.setInt(7, mcq.getCorrectOptionIndex());
                pstmt.setNull(8, Types.BOOLEAN);
            } else if (question instanceof TrueFalseQuestion) {
                TrueFalseQuestion tfq = (TrueFalseQuestion) question;
                pstmt.setString(2, "TF");
                pstmt.setNull(3, Types.VARCHAR);
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setNull(5, Types.VARCHAR);
                pstmt.setNull(6, Types.VARCHAR);
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setBoolean(8, tfq.getCorrectAnswer());
            }

            pstmt.setString(1, question.getQuestionText());
            pstmt.setInt(9, questionId);
            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while updating question", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while updating question", e);
        }
    }

    public void deleteQuestion(int questionId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM questions WHERE id = ?");
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while deleting question", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while deleting question", e);
        }
    }

    public Quiz getAllQuestions() {
        Quiz quiz = new Quiz(100);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                String questionText = rs.getString("question_text");
                String type = rs.getString("type");
                if (type.equals("MCQ")) {
                    String[] options = new String[4]; // Assuming fixed options for simplicity
                    options[0] = rs.getString("option1");
                    options[1] = rs.getString("option2");
                    options[2] = rs.getString("option3");
                    options[3] = rs.getString("option4");
                    int correctOption = rs.getInt("correct_option");
                    quiz.addQuestion(new MultipleChoiceQuestion(questionText, options, correctOption));
                } else if (type.equals("TF")) {
                    boolean correctAnswer = rs.getBoolean("correct_answer");
                    quiz.addQuestion(new TrueFalseQuestion(questionText, correctAnswer));
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while retrieving questions", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while retrieving questions", e);
        }
        return quiz;
    }

    // Add methods for user management, scoring, etc.

    // Example: Add methods for user management
    public void addUser(User user) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, score) VALUES (?, ?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, user.getScore());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while adding user", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while adding user", e);
        }
    }

    public void updateUserScore(String username, int newScore) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET score = ? WHERE username = ?");
            pstmt.setInt(1, newScore);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while updating user score", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while updating user score", e);
        }
    }

    public void deleteUser(String username) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred while deleting user", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while deleting user", e);
        }
    }
}
