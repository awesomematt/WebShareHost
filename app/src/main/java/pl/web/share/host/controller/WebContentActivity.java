package pl.web.share.host.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.web.share.host.R;

/**
 * Class responsible for collecting user input data connected with web content (html body).
 * Activity enables user to define his own website body content.
 * Stores user input and passes it to SettingsActivity.
 */
public class WebContentActivity extends AppCompatActivity {

    //Declaration of important variables
    private TextView textContent1;
    private TextView textContent2;
    private EditText webBodyContent;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_content);
        initializeVariables();

        //sets on click listener for save button - saves collected data from user input to Intent object
        //data is being send back to SettingsActivity
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent websiteContentIntent = new Intent();
                websiteContentIntent.putExtra("webContent", webBodyContent.getText().toString());
                Toast.makeText(getApplicationContext(), "Website content saved", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, websiteContentIntent);
                finish();

            }
        });
    }

    /**
     * Method for initialization of the variables stored in WebContentActivity class.
     */
    private void initializeVariables() {
        textContent1 = (TextView) findViewById(R.id.contentText1);
        textContent2 = (TextView) findViewById(R.id.contentText2);
        webBodyContent = (EditText) findViewById(R.id.webBodyContent);
        saveBtn = (Button) findViewById(R.id.saveButton);
    }
}
