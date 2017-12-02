package com.example.karna.myapplication;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.karna.myapplication.Data.EmployeeContent;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton floatingActionButton;
    ListView listView;
    EmployeeAdapter employeeAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeeAdapter = new EmployeeAdapter(this, null);
        listView = findViewById(R.id.listview1);
        View emptyview = findViewById(R.id.emptyview);
        listView.setEmptyView(emptyview);
        listView.setAdapter(employeeAdapter);
        floatingActionButton = findViewById(R.id.fltbtn);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent editor = new Intent(MainActivity.this, Employee.class);
                Uri itemUri = ContentUris.withAppendedId(EmployeeContent.EmployeesEntery.CONTENT_URI, l);
                editor.setData(itemUri);
                startActivity(editor);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Employee.class);
                startActivity(i);
            }
        });
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {EmployeeContent.EmployeesEntery._ID,
                EmployeeContent.EmployeesEntery.COLUMN_FIRSTNAME,
                EmployeeContent.EmployeesEntery.COLUMN_LASTNAME,
                EmployeeContent.EmployeesEntery.COLUMN_TITLE,
                EmployeeContent.EmployeesEntery.COLUMN_DEPARTMENT,
                EmployeeContent.EmployeesEntery.COLUMN_CITY,
                EmployeeContent.EmployeesEntery.COLUMN_PHONENO,
                EmployeeContent.EmployeesEntery.COLUMN_EMAIL};
        return new CursorLoader(this, EmployeeContent.EmployeesEntery.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        employeeAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        employeeAdapter.swapCursor(null);

    }
}
