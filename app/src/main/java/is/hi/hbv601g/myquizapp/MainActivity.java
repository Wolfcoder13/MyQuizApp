package is.hi.hbv601g.myquizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import is.hi.hbv601g.myquizapp.networking.NetworkCallback;
import is.hi.hbv601g.myquizapp.networking.NetworkManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonNext;
    private Button mButtonCheat;
    private TextView mTextViewQuestion;

    private List<Question> mQuestionBank;

    private int mCurrentIndex = 0;
    private boolean mUserCheated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getQuestions(new NetworkCallback<List<Question>>() {
            @Override
            public void onSuccess(List<Question> result) {
                mQuestionBank = result;
                //Log.d(TAG, "First Question in bank: " + mQuestionBank.get(0).getQuestionText());
                updateQuestion();
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get question: " + errorString);
            }
        });

        networkManager.getQuestion(6, new NetworkCallback<Question>() {
            @Override
            public void onSuccess(Question result) {
                Log.d(TAG, "Question text for id " + String.valueOf(6) + " is: " + result.getQuestionText());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get question: " + errorString);
            }
        });

        //updateQuestion();

        mButtonTrue = (Button) findViewById(R.id.true_button);
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mButtonFalse = (Button) findViewById(R.id.false_button);
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mButtonNext = (Button) findViewById(R.id.next_button);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                updateQuestion();
            }
        });

        mButtonCheat = (Button) findViewById(R.id.cheat_button);
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                Intent intent = CheatActivity.newIntent(MainActivity.this, mQuestionBank.get(mCurrentIndex).isAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return ;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            mUserCheated = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    // Update question text to current question.
    private void updateQuestion(){
        mTextViewQuestion = (TextView) findViewById(R.id.question_text);
        mTextViewQuestion.setText(mQuestionBank.get(mCurrentIndex).getQuestionText());
        mUserCheated = false;
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();

        if(mUserCheated){
            Toast.makeText(this, R.string.judgement_toast, Toast.LENGTH_SHORT).show();
        }
        else {
            if (userPressedTrue == answerIsTrue) {
                Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
