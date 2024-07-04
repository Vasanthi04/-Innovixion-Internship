package com.project;

public class Quiz {
    private Question[] questions;
    private int size;

    public Quiz(int capacity) {
        this.questions = new Question[capacity];
        this.size = 0;
    }

    public void addQuestion(Question question) {
        if (size < questions.length) {
            questions[size++] = question;
        } else {
            // Handle full capacity scenario if needed
            System.out.println("Quiz capacity reached. Cannot add more questions.");
        }
    }

    public Question getQuestion(int index) {
        if (index >= 0 && index < size) {
            return questions[index];
        }
        return null;
    }

    public int getTotalQuestions() {
        return size;
    }

    public boolean evaluateAnswer(int questionIndex, String answer) {
        Question question = getQuestion(questionIndex);
        if (question != null) {
            boolean isCorrect = question.checkAnswer(answer);
            // Adjust score or other actions based on correctness
            return isCorrect;
        }
        return false;
    }
}
