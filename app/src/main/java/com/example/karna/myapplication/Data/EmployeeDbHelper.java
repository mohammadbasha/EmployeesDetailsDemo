package com.example.karna.myapplication.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Karna on 12/1/2017.
 */

public class EmployeeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CompanyEmployeesList.db";

    private static final int DATABASE_VERSION = 1;

    public EmployeeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CreateTable = "CREATE TABLE " + EmployeeContent.EmployeesEntery.TABLE_NAME + " ("
                + EmployeeContent.EmployeesEntery._ID + " INTEGER PRIMARY KEY, "
                + EmployeeContent.EmployeesEntery.COLUMN_FIRSTNAME + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_LASTNAME + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_TITLE + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_DEPARTMENT + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_CITY + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_PHONENO + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_EMAIL + " TEXT NOT NULL, "
                + EmployeeContent.EmployeesEntery.COLUMN_IMAGE + " BLOB NOT NULL " + " );";

        sqLiteDatabase.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
