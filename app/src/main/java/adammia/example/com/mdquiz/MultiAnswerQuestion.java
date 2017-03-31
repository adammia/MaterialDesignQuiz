package adammia.example.com.mdquiz;

import java.util.ArrayList;

/**
 * Created by adammia on 2017. 03. 21.
 */

public class MultiAnswerQuestion extends Question {
    ArrayList<String> choices;

    /**
     * @param questionText is text that explains question
     * @param answer       must be numbers in form of string like if number 2 and number 5 are correct answers string must be: "25";
     * @param userChoices  is text that is displayed inside checkboxes
     */
    public MultiAnswerQuestion(String questionText, String answer, String... userChoices) {
        super(questionText, answer);
        choices = new ArrayList<String>();
        for (int i = 0; i < userChoices.length; i++) {
            choices.add(userChoices[i]);
        }
    }

    public ArrayList getChoices() {
        return choices;
    }
}
