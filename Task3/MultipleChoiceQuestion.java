package com.project;

public class MultipleChoiceQuestion extends Question {
    private String[] options;
    private int correctOptionIndex;

    public MultipleChoiceQuestion(String questionText, String[] options, int correctOptionIndex) {
        super(questionText);
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }
    @Override
    public boolean checkAnswer(String answer) {
        try {
            int selectedOption = Integer.parseInt(answer.trim()); // Convert answer to integer
            if (selectedOption < 0 || selectedOption >= options.length) {
                return false; // Out of range option index
            }
            return selectedOption == correctOptionIndex;
        } catch (NumberFormatException e) {
            return false; // Handle cases where answer cannot be parsed to int
        }
    }


    public String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}
