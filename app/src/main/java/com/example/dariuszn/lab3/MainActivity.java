package com.example.dariuszn.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addTelephone:
                //Toast.makeText(MainActivity.this, "Kliknięto plus", Toast.LENGTH_SHORT).show();
                goToAddPhoneActivity();
                break;

            default:
                break;
        }

        return true;
    }

    private void goToAddPhoneActivity() {
        Intent intent = new Intent(MainActivity.this, AddPhoneActivity.class);
        startActivity(intent);
    }
}
