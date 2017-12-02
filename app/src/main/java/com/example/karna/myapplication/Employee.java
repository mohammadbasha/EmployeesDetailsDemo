package com.example.karna.myapplication;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.karna.myapplication.Data.EmployeeContent;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Karna on 12/2/2017.
 */

public class Employee extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int SELECT_IMAGE = 1;
    public static final int CAPTURE_IMAGE = 2;
    ImageView mProfileImage;
    Uri newUri;
    EditText mFirstname, mLastname, mTitle, mDepartment, mCity, mPhoneno, mEmail;
    Button mPickImage, mSaveData;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editorslayout);
        mFirstname = findViewById(R.id.FirstName);
        mLastname = findViewById(R.id.LastName);
        mTitle = findViewById(R.id.Title);
        mDepartment = findViewById(R.id.Department);
        mCity = findViewById(R.id.City);
        mPhoneno = findViewById(R.id.Phoneno);
        mEmail = findViewById(R.id.Email);

        mProfileImage = findViewById(R.id.profileimage);
        mPickImage = findViewById(R.id.Pickimage);
        mSaveData = findViewById(R.id.saveContent);
        Intent i = getIntent();
        newUri = i.getData();
        if (newUri != null) {
            getLoaderManager().initLoader(0, null, this);
        }
        mPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(Employee.this)
                        .title("Selete Your Image")
                        .items(R.array.options)
                        .itemsIds(R.array.optionID)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        Intent PhotoPickIntent = new Intent(Intent.ACTION_PICK);
                                        PhotoPickIntent.setType("image/*");
                                        startActivityForResult(PhotoPickIntent, SELECT_IMAGE);
                                        break;
                                    case 1:
                                        Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(CameraIntent, CAPTURE_IMAGE);
                                        break;
                                    case 2:

                                }

                            }
                        })
                        .show();
            }
        });

        if (ContextCompat.checkSelfPermission(Employee.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Employee.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        mSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResults();
            }
        });
    }

    private void saveResults() {
        String FirstName = mFirstname.getText().toString();
        String LastName = mLastname.getText().toString();
        String Title = mTitle.getText().toString();
        String Department = mDepartment.getText().toString();
        String City = mCity.getText().toString();
        String Phoneno = mPhoneno.getText().toString();
        String Email = mEmail.getText().toString();

        mProfileImage.setDrawingCacheEnabled(true);
        mProfileImage.buildDrawingCache();
        Bitmap bitmap = mProfileImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        ContentValues values = new ContentValues();

        values.put(EmployeeContent.EmployeesEntery.COLUMN_FIRSTNAME, FirstName);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_LASTNAME, LastName);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_TITLE, Title);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_DEPARTMENT, Department);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_CITY, City);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_PHONENO, Phoneno);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_EMAIL, Email);
        values.put(EmployeeContent.EmployeesEntery.COLUMN_IMAGE, data);

        Uri newuri = getContentResolver().insert(EmployeeContent.EmployeesEntery.CONTENT_URI, values);

        Toast.makeText(getApplicationContext(), "data is inserted", Toast.LENGTH_SHORT).show();

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mProfileImage.setEnabled(true);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editorsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if ((resultCode == RESULT_OK)) {
                try {
                    final Uri imageuri = data.getData();
                    final InputStream inputStream = getContentResolver().openInputStream(imageuri);
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    mProfileImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }


        if (requestCode == CAPTURE_IMAGE) {
            if (resultCode == RESULT_OK) {
                Bitmap thumbian = (Bitmap) data.getExtras().get("data");
                mProfileImage.setMaxWidth(200);
                mProfileImage.setImageBitmap(thumbian);
            }
        }


    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String[] Projection = {EmployeeContent.EmployeesEntery._ID,
                EmployeeContent.EmployeesEntery.COLUMN_FIRSTNAME,
                EmployeeContent.EmployeesEntery.COLUMN_LASTNAME,
                EmployeeContent.EmployeesEntery.COLUMN_TITLE,
                EmployeeContent.EmployeesEntery.COLUMN_DEPARTMENT,
                EmployeeContent.EmployeesEntery.COLUMN_CITY,
                EmployeeContent.EmployeesEntery.COLUMN_PHONENO,
                EmployeeContent.EmployeesEntery.COLUMN_EMAIL,
                EmployeeContent.EmployeesEntery.COLUMN_IMAGE};
        return new CursorLoader(this, newUri, Projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor.moveToFirst()) {

            int FirstNameColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_FIRSTNAME);
            int LstNameColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_LASTNAME);
            int TitleColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_TITLE);
            int DepartmentColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_DEPARTMENT);
            int CityColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_CITY);
            int PhonenoColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_PHONENO);
            int EmailColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_EMAIL);
            int ImageColumnindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_IMAGE);


            String firstname = cursor.getString(FirstNameColumnindex);
            String lastname = cursor.getString(LstNameColumnindex);
            String title = cursor.getString(TitleColumnindex);
            String department = cursor.getString(DepartmentColumnindex);
            String city = cursor.getString(CityColumnindex);
            String phoneno = cursor.getString(PhonenoColumnindex);
            String email = cursor.getString(EmailColumnindex);
            byte[] image = cursor.getBlob(ImageColumnindex);

            mFirstname.setText(firstname);
            mLastname.setText(lastname);
            mTitle.setText(title);
            mDepartment.setText(department);
            mCity.setText(city);
            mPhoneno.setText(phoneno);
            mEmail.setText(email);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            mProfileImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 200, 200, false));

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
