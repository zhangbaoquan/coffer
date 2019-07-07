package coffer.auto_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2019-06-29
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class TestMainActivity1 extends AppCompatActivity implements View.OnClickListener {

    /**
     * The TextView used to display the message inside the Activity.
     */
    private TextView mTextView;

    /**
     * The EditText where the user types the message.
     */
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);

        // Set the listeners for the buttons.
        findViewById(R.id.changeTextBt).setOnClickListener(this);
        findViewById(R.id.activityChangeTextBtn).setOnClickListener(this);

        mTextView = findViewById(R.id.textToBeChanged);
        mEditText = findViewById(R.id.editTextUserInput);
    }

    @Override
    public void onClick(View view) {
        // Get the text from the EditText view.
        final String text = mEditText.getText().toString();

        switch (view.getId()) {
            case R.id.changeTextBt:
                // First button's interaction: set a text in a text view.
                mTextView.setText(text);
                break;
            case R.id.activityChangeTextBtn:
                // Second button's interaction: start an activity and send a message to it.
                Intent intent = ShowTextActivity.newStartIntent(this, text);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
