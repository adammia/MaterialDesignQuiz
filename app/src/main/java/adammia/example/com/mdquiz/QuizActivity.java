package adammia.example.com.mdquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by adammia on 2017. 03. 21.
 */

public class QuizActivity extends AppCompatActivity {
    //private static final String TAG ="QuizActivity";
    private final int NUMBER_OF_QUESTIONS = 10;
    private ArrayList<Question> questions;
    private TextView questionTextView;
    private TextView evaluateTextView;
    private TextView questionNumberTextView;
    private TextView scoreTextView;
    private EditText answerEditText;
    private Question currentQuestion;
    private RadioGroup choiceRadioGroup;
    private RatingBar scoreRatingBar;
    private Button nextButton;
    private Button submitButton;
    private Button tryAgain;
    private int questionId = 0;
    private int score = 0;
    private int questionNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        evaluateTextView = (TextView) findViewById(R.id.evaluate_text);
        questionTextView = (TextView) findViewById(R.id.question_text);
        answerEditText = (EditText) findViewById(R.id.question_answer);
        answerEditText.setText("");
        questionNumberTextView = (TextView) findViewById(R.id.question_number);
        scoreTextView = (TextView) findViewById(R.id.score_number);
        choiceRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        scoreRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        nextButton = (Button) findViewById(R.id.button_next_question);
        submitButton = (Button) findViewById(R.id.button_submit_question);
        tryAgain = (Button) findViewById(R.id.button_try_again);
        //** Creates and populates questions in ArrayList
        questions = new ArrayList<Question>();
        populateQuestionArrayList();
        //** Set first question
        displayQuestion();


        //** recovering the instance state
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(getString(R.string.score));
            questionNumber = savedInstanceState.getInt(getString(R.string.question_number));
        } else
            init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //**Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mMenuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Handle menu with message, for redirection to start page, with a little delay
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.material_io) {
            askToClose();
            Toast.makeText(QuizActivity.this,
                    R.string.redirect,
                    Toast.LENGTH_LONG)
                    .show();
        }
        if (item.getItemId() == R.id.md_assets) {
            askToClose();
            Toast.makeText(QuizActivity.this,
                    R.string.redirect,
                    Toast.LENGTH_LONG)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.score), score);
        outState.putInt(getString(R.string.question_number), questionNumber);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        init();
        scoreTextView.setText(String.valueOf(savedInstanceState.getInt(getString(R.string.score))));
        questionNumberTextView.setText(String.valueOf(savedInstanceState.getInt(getString(R.string.question_number))));
    }


    //** Initialize
    public void init() {
        questionNumberTextView = (TextView) findViewById(R.id.question_number);
    }

    /**
     * clears and populate questions arrayList
     * Add new questions here
     */
    private void populateQuestionArrayList() {
        questions.clear();
        questions.add(new Question(getString(R.string.Q10), getString(R.string.A10)));
        questions.add(new MultiAnswerQuestion(getString(R.string.Q9), getString(R.string.A9), getString(R.string.C91), getString(R.string.C92), getString(R.string.C93), getString(R.string.C94)));
        questions.add(new MultiChoiceQuestion(getString(R.string.Q8), getString(R.string.A8), getString(R.string.C81), getString(R.string.C82)));
        questions.add(new MultiChoiceQuestion(getString(R.string.Q7), getString(R.string.A7), getString(R.string.C71), getString(R.string.C72), getString(R.string.C73), getString(R.string.C74)));
        questions.add(new MultiAnswerQuestion(getString(R.string.Q6), getString(R.string.A6), getString(R.string.C61), getString(R.string.C62), getString(R.string.C63), getString(R.string.C64)));
        questions.add(new MultiChoiceQuestion(getString(R.string.Q5), getString(R.string.A5), getString(R.string.C51), getString(R.string.C52), getString(R.string.C53), getString(R.string.C54)));
        questions.add(new MultiAnswerQuestion(getString(R.string.Q4), getString(R.string.A4), getString(R.string.C41), getString(R.string.C42), getString(R.string.C43), getString(R.string.C44)));
        questions.add(new MultiChoiceQuestion(getString(R.string.Q3), getString(R.string.A3), getString(R.string.C31), getString(R.string.C32)));
        questions.add(new MultiAnswerQuestion(getString(R.string.Q2), getString(R.string.A2), getString(R.string.C21), getString(R.string.C22), getString(R.string.C23), getString(R.string.C24)));
        questions.add(new MultiAnswerQuestion(getString(R.string.Q1), getString(R.string.A1), getString(R.string.C11), getString(R.string.C12), getString(R.string.C13), getString(R.string.C14)));
    }


    /**
     * Display question in TextView with data from ArraList questions
     */
    private void displayQuestion() {
        if (questions.size() > 0) {
            currentQuestion = questions.get(getQuestionItem());
            questionTextView.setText(currentQuestion.getText());
            answerEditText.setText("");
            choiceRadioGroup.clearCheck();
            questionNumberTextView.setText(getString(R.string.question_no) + " " + questionNumber + getString(R.string.per) + NUMBER_OF_QUESTIONS);
            if (currentQuestion instanceof MultiChoiceQuestion) {
                answerEditText.setVisibility(View.INVISIBLE);
                choiceRadioGroup.setVisibility(View.VISIBLE);
                ArrayList choices = ((MultiChoiceQuestion) currentQuestion).getChoices();
                for (int i = 0; i < choices.size(); i++) {
                    RadioButton nextChoice = new RadioButton(this);
                    nextChoice.setText(choices.get(i).toString());
                    choiceRadioGroup.addView(nextChoice, i);
                }
            } else if (currentQuestion instanceof MultiAnswerQuestion) {
                answerEditText.setVisibility(View.INVISIBLE);
                choiceRadioGroup.setVisibility(View.VISIBLE);
                ArrayList choices = ((MultiAnswerQuestion) currentQuestion).getChoices();
                for (int i = 0; i < choices.size(); i++) {
                    CheckBox nextChoice = new CheckBox(this);
                    nextChoice.setText(choices.get(i).toString());
                    choiceRadioGroup.addView(nextChoice, i);
                }
            } else {

                answerEditText.setVisibility(View.VISIBLE);
                choiceRadioGroup.setVisibility(View.INVISIBLE);
            }
        }
    }

    private int getQuestionItem() {
        if (questions.size() > 0) {
            for (int i = 0; i < questions.size(); i++)
                questionId = i;
            return questionId;
        } else {
            return -1;
        }

    }

    /**
     * Gets user answer to question
     *
     * @return String answer that user has put in EditText R.id.question_answer
     */
    private String getUserAnswer() {
        String answer = answerEditText.getText().toString();
        return answer;
    }


    /**
     * Manage what happens when user clicks Next button
     *
     * @param v button Next
     */
    public void nextQuestion(View v) {
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
        choiceRadioGroup.removeAllViews();
        nextQuestion();

    }

    public void submitQuestion(View v) {
        nextButton.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.GONE);

        if (currentQuestion instanceof MultiChoiceQuestion) {
            checkMultiQuestion();
        } else if (currentQuestion instanceof MultiAnswerQuestion) {
            checkMultiAnswerQuestion();
        } else {
            checkSimpleQuestion();
        }
    }


    /**
     * Restart the quiz and try again
     *
     * @param v
     */
    public void tryAgain(View v) {
        score = 0;
        questionNumber = 1;
        populateQuestionArrayList();
        tryAgain.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        choiceRadioGroup.setVisibility(View.INVISIBLE);
        scoreRatingBar.setVisibility(View.GONE);
        questionNumberTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.GONE);
        evaluateTextView.setVisibility(View.GONE);
        answerEditText.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        displayQuestion();
    }

    /**
     * Simple question method
     */
    private void checkSimpleQuestion() {
        String answer = getUserAnswer();
        //* check answer in question class and if it is good then give points to user
        addScore(answer);
        answerEditText.setText(currentQuestion.getAnswer());
        if (answer.compareTo(currentQuestion.getAnswer()) == 0) {
            answerEditText.setTextColor(getResources().getColor(R.color.colorTrue));
        } else {
            answerEditText.setTextColor(getResources().getColor(R.color.colorWrong));


        }
    }

    /**
     * Multi question method
     */
    private void checkMultiQuestion() {
        String answer = "";
        RadioButton nextChoice;
        for (int i = 0; i < choiceRadioGroup.getChildCount(); i++) {
            nextChoice = (RadioButton) choiceRadioGroup.getChildAt(i);
            nextChoice.setEnabled(false);
            if (nextChoice.isChecked()) {
                answer = "" + (i + 1);
                if (answer.compareTo(currentQuestion.getAnswer()) == 0) {
                    nextChoice.setTextColor(getResources().getColor(R.color.colorTrue));
                } else {
                    nextChoice.setTextColor(getResources().getColor(R.color.colorWrong));
                }
            }
        }
        int i = Integer.parseInt(currentQuestion.getAnswer());
        nextChoice = (RadioButton) choiceRadioGroup.getChildAt(i - 1);
        nextChoice.setTextColor(getResources().getColor(R.color.colorTrue));
        addScore(answer);
    }

    /**
     * Multi answer question method
     */
    private void checkMultiAnswerQuestion() {
        String answer = "";
        CheckBox nextChoice;
        for (int i = 0; i < choiceRadioGroup.getChildCount(); i++) {
            nextChoice = (CheckBox) choiceRadioGroup.getChildAt(i);
            nextChoice.setEnabled(false);
            if (((CheckBox) choiceRadioGroup.getChildAt(i)).isChecked()) {
                answer += (i + 1);//make answer string like "23" if second and third answer are correct
                if (currentQuestion.getAnswer().contains(answer)) {
                    nextChoice.setTextColor(getResources().getColor(R.color.colorTrue));
                } else {
                    nextChoice.setTextColor(getResources().getColor(R.color.colorWrong));
                }
            }
            if (currentQuestion.getAnswer().contains("" + (i + 1))) {
                nextChoice.setTextColor(getResources().getColor(R.color.colorTrue));
            }
        }
        addScore(answer);
    }

    /**
     * Show next question on screen
     */
    private void nextQuestion() {

        if (questionNumber >= NUMBER_OF_QUESTIONS) { //** check if this is last question and than show score
            endOfQuiz();
        } else {
            removeQuestionFromList(); //*first remove current question from list
            questionNumber++; //* increase number of question counter
            displayQuestion(); //* now get and show next question
        }
    }

    /**
     * Increase score if answer is correct
     *
     * @param answer is String answer from user
     */
    private void addScore(String answer) {
        int i = currentQuestion.checkAnswer(answer);
        if (i == 0) {
            score++;

            Toast.makeText(this, getString(R.string.toast_correct) + " " + score + getString(R.string.per_no), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, R.string.toast_wrong, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Removes current chosen question from ArrayList questions
     */
    private void removeQuestionFromList() {
        if (questionId < questions.size()) {
            questions.remove(questionId);
        } else {
            scoreTextView.setText(R.string.error);
        }
    }

    /**
     * Manage what happens when user answers on all questions
     */

    private void endOfQuiz() {
        tryAgain.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        choiceRadioGroup.setVisibility(View.INVISIBLE);
        scoreRatingBar.setVisibility(View.VISIBLE);
        questionNumberTextView.setVisibility(View.GONE);
        scoreTextView.setVisibility(View.VISIBLE);
        evaluateTextView.setVisibility(View.VISIBLE);
        answerEditText.setVisibility(View.GONE);
        scoreTextView.setText(getString(R.string.result) + " " + score + "/" + NUMBER_OF_QUESTIONS);
        scoreRatingBar.setRating(score / (NUMBER_OF_QUESTIONS / 5));
        switch (score) {
            case 10:
                questionTextView.setText(R.string.best_100);
                evaluateTextView.setText(R.string.evaluationExcellent);
                break;
            case 9:
                questionTextView.setText(R.string.perfect_90);
                evaluateTextView.setText(R.string.evaluationExcellent);
                break;
            case 8:
                questionTextView.setText(R.string.excellent_80);
                evaluateTextView.setText(R.string.evaluationVeryGood);
                break;
            case 7:
                questionTextView.setText(R.string.excellent_70);
                evaluateTextView.setText(R.string.evaluationVeryGood);
                break;
            case 6:
                questionTextView.setText(R.string.welldone_60);
                evaluateTextView.setText(R.string.evaluateOk);
                break;
            case 5:
                questionTextView.setText(R.string.god_50);
                evaluateTextView.setText(R.string.evaluateOk);
                break;
            case 4:
            case 3:
            case 2:
            case 1:
                questionTextView.setText(R.string.less_30);
                evaluateTextView.setText(R.string.evaluateBad);
                break;
            case 0:
                questionTextView.setText(R.string.answer_forquestion);
                break;
            default:
                questionTextView.setText(R.string.you_did);
                break;
        }
    }

    //** Manage when touch outside of EditText, this method hides the keyboard
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * If back button is pressed, it redirects the user to start page, and game can start again
     */
    @Override
    public void onBackPressed() {
        askToClose();
    }

    private void askToClose() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setMessage(R.string.want_toquit);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}



