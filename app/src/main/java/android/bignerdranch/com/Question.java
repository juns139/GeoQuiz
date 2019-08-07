package android.bignerdranch.com;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mHasCheated;

    public Question(int textResId, boolean answerTrue) {
        this.mTextResId = textResId; // question text
        this.mAnswerTrue = answerTrue; // question answer
        this.mHasCheated = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

}
