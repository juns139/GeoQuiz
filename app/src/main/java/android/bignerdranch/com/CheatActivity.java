package android.bignerdranch.com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";

    private static final String EXTRA_ANSWER_IS_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_is_shown";

    private static final String ANSWER_SHOWN = "shown";
    private static final String CHEAT_USED = "cheater";

    private boolean mAnswerIsTrue;
    private Button mShowAnswerButton;
    private TextView mAnswerText;
    private TextView mApiText;
    private boolean mAnswerWasShown;
    private boolean mCheatButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mApiText = findViewById(R.id.api_text);
        mAnswerText = findViewById(R.id.answer_text_view);
        mApiText.setText("API Level " + Build.VERSION.SDK_INT);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        if (savedInstanceState != null) {
            mAnswerWasShown = savedInstanceState.getBoolean(ANSWER_SHOWN);
            setAnswerShownResult(mAnswerWasShown);
            mCheatButtonPressed = savedInstanceState.getBoolean(CHEAT_USED);
            if (mCheatButtonPressed) {
                displayText();
            }
        }

        //this getIntent is from startActivity(Intent) call.
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayText();
                mAnswerWasShown = true;
                mCheatButtonPressed = true;
                setAnswerShownResult(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    ((Animator) anim).addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(ANSWER_SHOWN, mAnswerWasShown);
        bundle.putBoolean(CHEAT_USED, mCheatButtonPressed);
    }

    private void displayText() {
        if (mAnswerIsTrue) {
            mAnswerText.setText(R.string.true_button);
        } else {
            mAnswerText.setText(R.string.false_button);
        }
    }
}
