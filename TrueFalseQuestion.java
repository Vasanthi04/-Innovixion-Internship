package com.project;

public class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(String questionText, boolean correctAnswer) {
        super(questionText);
        this.correctAnswer = correctAnswer;
    }
    @Override
    public boolean checkAnswer(String answer) {
        String lowerCaseAnswer = answer.trim().toLowerCase();
        if (!lowerCaseAnswer.equals("true") && !lowerCaseAnswer.equals("false")) {
            return false; // Invalid true/false answer format
        }
        boolean userAnswer = Boolean.parseBoolean(lowerCaseAnswer);
        return userAnswer == correctAnswer;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }
}
