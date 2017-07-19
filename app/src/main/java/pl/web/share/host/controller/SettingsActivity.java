package pl.web.share.host.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import pl.web.share.host.R;

/**
 * Class responsible for handling user selection in settings area.
 * Activity enables user to choose his own site title, animated text, website body content etc.
 * Stores user input and passes it to MainActivity.
 */
public class SettingsActivity extends AppCompatActivity {

    //Declaration of important variables
    private EditText siteTitle, animText;
    private RadioGroup directionGroup;
    private RadioButton up, down, left, right;
    private TextView webTitle, msgAnim, direction, backgnd, preview;
    private SeekBar imageSelector;
    private ImageView backgndImagePreview;
    private Button editContentBtn, saveBtn, infoBtn;

    private Integer selectedBackgndImage, selectedAnimDirection;
    private String websiteBodyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initializes all variables for SettingsActivity class
        initializeVariables();

        //setting up on click listeners for radio buttons (UI)
        //(each radio button represents html <marquee> text animation direction)
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        //sets listener for seek bar which defines user selection for background image (UI)
        imageSelector.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //integer type value from 0 to 5 (max) defines correct background image
                selectedBackgndImage = i;
                //changes background image preview for ImageView (UI)
                changePreview(i);
                Toast.makeText(getApplicationContext(), "Changed background image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //sets on click listener for edit content button (UI) - switches to WebContentActivity
        editContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contentActivity = new Intent(getApplicationContext(), WebContentActivity.class);
                Toast.makeText(getApplicationContext(), "Editing website content", Toast.LENGTH_SHORT).show();
                startActivityForResult(contentActivity, 1);
            }
        });

        //sets on click listener for info button (UI) - switches to InfoActivity
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoActivity = new Intent(getApplicationContext(), InfoActivity.class);
                startActivityForResult(infoActivity, 1);
            }
        });

        //sets on click listener for save button - saves collected data from user input to Intent object
        //data is being send back to MainActivity
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent settingsIntentData = new Intent();
                settingsIntentData.putExtra("siteTitle", siteTitle.getText().toString());
                settingsIntentData.putExtra("animText", animText.getText().toString());
                settingsIntentData.putExtra("backgroundImage", selectedBackgndImage);
                settingsIntentData.putExtra("animDirection", selectedAnimDirection);
                settingsIntentData.putExtra("websiteContent", websiteBodyContent);

                Toast.makeText(getApplicationContext(), "Changes successfully saved!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, settingsIntentData);
                finish();
            }
        });

    }

    /**
     * Method changes background image preview for ImageView object (UI)
     *
     * @param selection integer value type defined by user via SeekBar (0-5)
     */
    private void changePreview(Integer selection) {
        //each selection sets image resource for drawable directory
        switch (selection) {
            case 0:
                backgndImagePreview.setImageResource(R.drawable.image1);
                break;
            case 1:
                backgndImagePreview.setImageResource(R.drawable.image2);
                break;
            case 2:
                backgndImagePreview.setImageResource(R.drawable.image3);
                break;
            case 3:
                backgndImagePreview.setImageResource(R.drawable.image4);
                break;
            case 4:
                backgndImagePreview.setImageResource(R.drawable.image5);
                break;
            case 5:
                backgndImagePreview.setImageResource(R.drawable.image6);
                break;
            default:
                backgndImagePreview.setImageResource(R.drawable.image1);
                break;
        }
    }

    /**
     * Method defines action when a radio button is clicked by an user
     *
     * @param view - represents activity view (UI)
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        //(each radio button represents html <marquee> text animation direction - from 1 to 4)
        switch (view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    selectedAnimDirection = 1;
                break;
            case R.id.radioButton2:
                if (checked)
                    selectedAnimDirection = 2;
                break;
            case R.id.radioButton3:
                if (checked)
                    selectedAnimDirection = 3;
                break;
            case R.id.radioButton4:
                if (checked)
                    selectedAnimDirection = 4;
                break;
        }
    }

    /**
     * Method collects data which was sent back from WebContentActivity.
     * Data is being retrieved from Intent object.
     *
     * @param requestCode contains request code as an int type value
     * @param resultCode  contains result code as an int type value
     * @param data        contains user input data from SettingsActivity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //website body content is being set to a String type variable
                websiteBodyContent = data.getStringExtra("webContent");
            }
        }
    }

    /**
     * Method for initialization of the variables stored in SettingsActivity class.
     */
    private void initializeVariables() {
        siteTitle = (EditText) findViewById(R.id.websiteTitleText);
        animText = (EditText) findViewById(R.id.websiteMOTD);

        directionGroup = (RadioGroup) findViewById(R.id.directionRadioGroup);
        up = (RadioButton) findViewById(R.id.radioButton1);
        down = (RadioButton) findViewById(R.id.radioButton2);
        left = (RadioButton) findViewById(R.id.radioButton3);
        right = (RadioButton) findViewById(R.id.radioButton4);

        webTitle = (TextView) findViewById(R.id.textViewWebsiteTitle);
        msgAnim = (TextView) findViewById(R.id.textViewMOTD);
        direction = (TextView) findViewById(R.id.textViewMOTDDir);
        backgnd = (TextView) findViewById(R.id.textViewBackground);
        preview = (TextView) findViewById(R.id.textViewBackgroundPreview);

        imageSelector = (SeekBar) findViewById(R.id.seekBarBackground);

        backgndImagePreview = (ImageView) findViewById((R.id.imageViewBackgroundPreview));

        editContentBtn = (Button) findViewById(R.id.editBodyButton);
        saveBtn = (Button) findViewById(R.id.saveButton);
        infoBtn = (Button) findViewById(R.id.infoButton);

        selectedBackgndImage = 0;
        selectedAnimDirection = 0;
        websiteBodyContent = "";
    }
}
