package is.hi.hbv601g.myquizapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE = "is.hi.hbv601g.myquizapp.isAnswerTrue";
    private static final String EXTRA_ANSWER_IS_SHOWN = "is.hi.hbv601g.myquizapp.answerIsShown";
    private boolean mAnswerIsTrue;
    private Button mButtonShowAnswer;
    private TextView mTextViewAnswer;

    public static Intent newIntent(Context packageContext, boolean isAnswerTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent data){
        return data.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        Log.d(TAG, "onCreate: " + mAnswerIsTrue);

        mButtonShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mTextViewAnswer = (TextView) findViewById(R.id.answer_text);
        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue)
                    mTextViewAnswer.setText(R.string.true_button);
                else
                    mTextViewAnswer.setText(R.string.false_button);
                setAnswerShowResult(true);
            }
        });
    }

    private void setAnswerShowResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
