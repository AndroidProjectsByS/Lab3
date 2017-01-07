package com.example.dariuszn.lab3;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dariuszn.lab3.model.Phone;

public class EditPhoneActivity extends AppCompatActivity {

    private EditText producentEditText;
    private EditText modelEditText;
    private EditText wwwEditText;
    private EditText androidVersionEditText;
    private long phoneId;

    private Button wwwButton;
    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_phone);

        phoneId = getIntent().getLongExtra(Phone.ID, -1);

        initComponents();
        addListenersToButtons();
        fillComponents();
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
                if (wwwEditText.getText().toString().startsWith("http://")) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(wwwEditText.getText().toString()));
                    startActivity(intent);
                }
            }
        });
    }

    private void addListenerToCancelButton() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });
    }

    private void addListenerToSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePhoneInDatabase();
            }
        });
    }

    public void updatePhoneInDatabase() {
        boolean isEditTextCorrect = checkCorrectionOfEditTexts();
        if (isEditTextCorrect) {
            ContentValues values = new ContentValues();
            values.put(Phone.ANDROID_VERSION_COLUMN, androidVersionEditText.getText().toString());
            values.put(Phone.MODEL_COLUMMN, modelEditText.getText().toString());
            values.put(Phone.PRODUCENT_COLUMN, producentEditText.getText().toString());
            values.put(Phone.WWW_COLUMN, wwwEditText.getText().toString());

            getContentResolver().update(ContentUris.withAppendedId(MyProvider.CONTENT_URI,phoneId), values, null, null);

            goToMainActivity();
        }
    }

    private boolean checkCorrectionOfEditTexts() {
        boolean isCorrect = true;

        if (producentEditText.getText().toString().trim().equals("")) {
            isCorrect = false;
            producentEditText.setError("Pole zawiera nie poprawną wartość.");
        }
        else if (modelEditText.getText().toString().trim().equals("")) {
            isCorrect = false;
            modelEditText.setError("Pole zawiera nie poprawną wartość.");
        }
        else if (!wwwEditText.getText().toString().startsWith("http://")) {
            isCorrect = false;
            wwwEditText.setError("Pole zawiera nie poprawną wartość.");
        }
        else if (androidVersionEditText.getText().toString().matches("^[a-zA-z]+$")) {
            isCorrect = false;
            androidVersionEditText.setError("Pole zawiera nie poprawną wartość.");
        }

        return isCorrect;
    }

    private void fillComponents() {
        Cursor cursor = getContentResolver().query(ContentUris.withAppendedId(MyProvider.CONTENT_URI, phoneId), null, null, null, null);

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();

            String model = cursor.getString(cursor.getColumnIndex(Phone.MODEL_COLUMMN));
            String android_version = cursor.getString(cursor.getColumnIndex(Phone.ANDROID_VERSION_COLUMN));
            String www = cursor.getString(cursor.getColumnIndex(Phone.WWW_COLUMN));
            String producent = cursor.getString(cursor.getColumnIndex(Phone.PRODUCENT_COLUMN));

            modelEditText.setText(model);
            producentEditText.setText(producent);
            androidVersionEditText.setText(android_version);
            wwwEditText.setText(www);
        }
        else {
            Toast.makeText(EditPhoneActivity.this, "Ilość rekordów: " + Integer.toString(cursor.getColumnCount()),
                Toast.LENGTH_SHORT).show();
        }

    }

    private void goToMainActivity() {
        Intent intent = new Intent(EditPhoneActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
