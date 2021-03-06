package is.hi.hbv601g.myquizapp;

import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("id")
    private int mId;
    @SerializedName("questionText")
    private String mQuestionText;
    @SerializedName("answerTrue")
    private boolean mAnswerTrue;

    public Question(int id, String questionText, boolean answerTrue) {
        mId = id;
        mQuestionText = questionText;
        mAnswerTrue = answerTrue;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
