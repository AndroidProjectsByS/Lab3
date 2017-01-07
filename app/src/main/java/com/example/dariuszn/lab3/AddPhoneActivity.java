package com.example.dariuszn.lab3;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dariuszn.lab3.model.Phone;

public class AddPhoneActivity extends AppCompatActivity {

    private EditText producentEditText;
    private EditText modelEditText;
    private EditText wwwEditText;
    private EditText androidVersionEditText;

    private Button wwwButton;
    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_phone);

        initComponents();
        addListenersToButtons();
    }

    private void initComponents() {
        producentEditText = (EditText) findViewById(R.id.producent);
        modelEditText = (EditText) findViewById(R.id.model);
        wwwEditText = (EditText) findViewById(R.id.www);
        androidVersionEditText = (EditText) findViewById(R.id.androidVersion);

        wwwButton = (Button) findViewById(R.id.wwwButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        saveButton = (Button) findViewById(R.id.saveButton);
    }

    private void addListenersToButtons() {
        addListenerToWWWButton();
        addListenerToCancelButton();
        addListenerToSaveButton();
    }

    private void addListenerToWWWButton() {
        wwwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void addListenerToCancelButton() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

            }
        });
    }

    private void addListenerToSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoneToDatabase();
            }
        });
    }

    public void addPhoneToDatabase() {
        ContentValues values = new ContentValues();
        values.put(Phone.ANDROID_VERSION_COLUMN, androidVersionEditText.getText().toString());
        values.put(Phone.MODEL_COLUMMN, modelEditText.getText().toString());
        values.put(Phone.PRODUCENT_COLUMN, producentEditText.getText().toString());
        values.put(Phone.WWW_COLUMN, wwwEditText.getText().toString());

        getContentResolver().insert(MyProvider.CONTENT_URI, values);
    }
}
