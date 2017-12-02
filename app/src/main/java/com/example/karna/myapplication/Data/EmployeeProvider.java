package com.example.karna.myapplication.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Karna on 12/1/2017.
 */

public class EmployeeProvider extends ContentProvider {

    private static final int EMPLOYEES = 100;
    private static final int EMPLOYEE_ID = 101;
    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUrimatcher.addURI(EmployeeContent.CONTENT_AUTHOR, EmployeeContent.PATH_EMPLOYEES, EMPLOYEES);
        sUrimatcher.addURI(EmployeeContent.CONTENT_AUTHOR, EmployeeContent.PATH_EMPLOYEES + "/#", EMPLOYEE_ID);
    }

    private EmployeeDbHelper mDbHelper;

    public boolean onCreate() {
        mDbHelper = new EmployeeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(EmployeeContent.EmployeesEntery.TABLE_NAME);
        switch ((sUrimatcher.match(uri))) {
            case EMPLOYEES:
                cursor = database.query(EmployeeContent.EmployeesEntery.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
            case EMPLOYEE_ID:
                s = EmployeeContent.EmployeesEntery._ID + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(EmployeeContent.EmployeesEntery.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUrimatcher.match(uri)) {
            case EMPLOYEES:
                return EmployeeContent.EmployeesEntery.EMPLOYEE_LIST;
            case EMPLOYEE_ID:
                return EmployeeContent.EmployeesEntery.EMPLOYEE_ITEM;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(EmployeeContent.EmployeesEntery.TABLE_NAME, null, contentValues);

        if (id == -1) {

            Toast.makeText(getContext(), "Data is not inserrted", Toast.LENGTH_SHORT).show();
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int id = 0;
        switch (sUrimatcher.match(uri)) {
            case EMPLOYEES:
                id = database.delete(EmployeeContent.EmployeesEntery.TABLE_NAME, s, strings);
                break;
            case EMPLOYEE_ID:
                s = EmployeeContent.EmployeesEntery._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = database.delete(EmployeeContent.EmployeesEntery.TABLE_NAME, s, strings);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int id = 0;
        switch (sUrimatcher.match(uri)) {
            case EMPLOYEES:
                id = database.delete(EmployeeContent.EmployeesEntery.TABLE_NAME, s, strings);
                break;
            case EMPLOYEE_ID:
                s = EmployeeContent.EmployeesEntery._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = database.delete(EmployeeContent.EmployeesEntery.TABLE_NAME, s, strings);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }
}