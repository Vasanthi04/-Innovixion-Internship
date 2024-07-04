package com.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizApp extends Application {

    private QuizDAO quizDAO;
    private Quiz quiz;
    private int currentQuestionIndex;
    private User currentUser;
    private Label questionLabel;
    private VBox vbox;
    private ToggleGroup answerGroup;

    public QuizApp() {
        this.quizDAO = new QuizDAO();
        this.quiz = quizDAO.getAllQuestions();
        this.currentUser = new User("testuser");
        this.currentQuestionIndex = 0;
        this.answerGroup = new ToggleGroup();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Online Quiz Application");

        vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        questionLabel = new Label();
        vbox.getChildren().add(questionLabel);

        updateQuestion();

        Button submitButton = new Button("Submit Answer");
        submitButton.setOnAction(e -> handleAnswerSubmission());
        vbox.getChildren().add(submitButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateQuestion() {
        Question currentQuestion = quiz.getQuestion(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getQuestionText());
        displayQuestionOptions(currentQuestion);
    }

    private void displayQuestionOptions(Question question) {
        // Clear previous options
        vbox.getChildren().removeIf(node -> node instanceof RadioButton);
        answerGroup.getToggles().clear();

        if (question instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
            for (String option : mcQuestion.getOptions()) {
                RadioButton optionButton = new RadioButton(option);
                optionButton.setToggleGroup(answerGroup);
                vbox.getChildren().add(optionButton);
            }
        } else if (question instanceof TrueFalseQuestion) {
            RadioButton trueOption = new RadioButton("True");
            RadioButton falseOption = new RadioButton("False");
            trueOption.setToggleGroup(answerGroup);
            falseOption.setToggleGroup(answerGroup);
            vbox.getChildren().addAll(trueOption, falseOption);
        }
    }

    private void handleAnswerSubmission() {
        RadioButton selectedOptionButton = (RadioButton) answerGroup.getSelectedToggle();
        if (selectedOptionButton != null) {
            String answer = selectedOptionButton.getText();
            boolean isCorrect = quiz.evaluateAnswer(currentQuestionIndex, answer);
            if (isCorrect) {
                currentUser.increaseScore();
            }
            moveToNextQuestion();
        } else {
            // Show a warning if no option is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an answer.");
            alert.showAndWait();
        }
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quiz.getTotalQuestions()) {
            updateQuestion();
        } else {
            showFinalResults();
        }
    }

    private void showFinalResults() {
        vbox.getChildren().clear();
        Label resultLabel = new Label("Quiz Completed!\nYour Score: " + currentUser.getScore());
        vbox.getChildren().add(resultLabel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
