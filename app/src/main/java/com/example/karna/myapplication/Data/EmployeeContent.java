package com.example.karna.myapplication.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Karna on 12/1/2017.
 */

public class EmployeeContent {

    public static final String CONTENT_AUTHOR = "com.example.karna.myapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHOR);
    public static final String PATH_EMPLOYEES = "employees";


    public static final class EmployeesEntery implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EMPLOYEES);

        public static final String EMPLOYEE_LIST = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHOR + "/" + PATH_EMPLOYEES;

        public static final String EMPLOYEE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHOR + "/" + PATH_EMPLOYEES;

        public static final String TABLE_NAME = "EmployessList";

        public static final String COLUMN_FIRSTNAME = "Firstname";

        public static final String COLUMN_LASTNAME = "Lastname";

        public static final String COLUMN_TITLE = "Title";

        public static final String COLUMN_DEPARTMENT = "Department";

        public static final String COLUMN_CITY = "City";

        public static final String COLUMN_PHONENO = "Phoneno";

        public static final String COLUMN_EMAIL = "Email";

        public static final String COLUMN_IMAGE = "ProfileImage";


    }


}
