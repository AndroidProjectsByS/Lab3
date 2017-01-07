package com.example.dariuszn.lab3;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.dariuszn.lab3.model.Phone;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private SimpleCursorAdapter cursorAdapter;
    private ListView phonesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonesView = (ListView) findViewById(R.id.phones_list);

//        MyDatabaseHellper helper = new MyDatabaseHellper(this);
//        SQLiteDatabase db = helper.getWritableDatabase();

//        String sqlQuery = "INSERT INTO phones ('model', 'android_version', 'www', 'producent'" +
//                ") VALUES ('Samasung galaxy s2', '4', 'www', 'Samsung')";
//
//        db.execSQL(sqlQuery);

        callLoader();

        initContextMenuMulitChoiceMode();
        initContextMenuClickMode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private void callLoader() {
        getLoaderManager().initLoader(0, null, this);
        String[] mapFrom = new String[] {"producent", "model"};
        int[] mapTo = new int[] {R.id.producent_name, R.id.model};
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.row_layout, null, mapFrom, mapTo, 0);
        phonesView.setAdapter(cursorAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projekcja = {Phone.ID, Phone.PRODUCENT_COLUMN, Phone.MODEL_COLUMMN};
        CursorLoader cursorLoader = new CursorLoader(this, MyProvider.CONTENT_URI, projekcja, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    private void initContextMenuMulitChoiceMode() {
        phonesView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        phonesView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_delete_phones:
                        //Toast.makeText(MainActivity.this, "Wybrano opcje", Toast.LENGTH_SHORT).show();
                        deletedChoosenPhones();
                        break;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }

    private void initContextMenuClickMode() {
        phonesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditPhoneActivity.class);
                intent.putExtra(Phone.ID, id);
                startActivity(intent);
            }
        });
    }



    private void deletedChoosenPhones() {
        long checked[] = phonesView.getCheckedItemIds();
        for (int i = 0; i < checked.length; i++) {
            getContentResolver().delete(ContentUris.withAppendedId(MyProvider.CONTENT_URI, checked[i]), null, null);
        }
    }
}
