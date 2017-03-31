package adammia.example.com.mdquiz;

/**
 * Created by adammia on 2017. 03. 21.
 */


public class Question {

    private String mText;
    private String mAnswer;

    public Question(String text, String answer) {
        mText = text;
        mAnswer = answer;
    }

    public String getText() {
        return mText;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public int checkAnswer(String answer) {
        answer = answer.toLowerCase();
        return mAnswer.compareTo(answer);
    }

}
