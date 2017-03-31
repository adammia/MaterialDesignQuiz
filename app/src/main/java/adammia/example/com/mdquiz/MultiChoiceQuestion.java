package adammia.example.com.mdquiz;

import java.util.ArrayList;

/**
 * Created by adammia on 2017. 03. 21.
 */

public class MultiChoiceQuestion extends Question {
    ArrayList<String> choices;

    public MultiChoiceQuestion(String questionText, String answer, String... userChoices) {
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
