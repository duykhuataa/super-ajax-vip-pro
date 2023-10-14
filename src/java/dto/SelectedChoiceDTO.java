package dto;

import java.util.List;

public class SelectedChoiceDTO {

    private int questionId;
    private List<String> selectedAnswerIds;

    public SelectedChoiceDTO(int questionId, List<String> selectedAnswers) {
        this.questionId = questionId;
        this.selectedAnswerIds = selectedAnswers;
    }

    public int getQuestionId() {
        return questionId;
    }
    
    public List<String> getSelectedAnswerIds() {
        return selectedAnswerIds;
    }

}
