package com.example.karna.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.karna.myapplication.Data.EmployeeContent;

/**
 * Created by Karna on 12/2/2017.
 */

public class EmployeeAdapter extends CursorAdapter {
    /**
     * @param context
     * @param c
     * @deprecated
     */
    public EmployeeAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.listitems, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView mtxtFirstname = view.findViewById(R.id.txtFirstname);
        TextView mtxtLastname = view.findViewById(R.id.txtLastname);
        TextView mtxtTitle = view.findViewById(R.id.txtTitle);


        int Fistnameindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_FIRSTNAME);
        int Lastnameindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_LASTNAME);
        int Titleindex = cursor.getColumnIndex(EmployeeContent.EmployeesEntery.COLUMN_TITLE);

        String Firstname = cursor.getString(Fistnameindex);
        String Lastname = cursor.getString(Lastnameindex);
        String Title = cursor.getString(Titleindex);

        mtxtFirstname.setText(Firstname);
        mtxtLastname.setText(Lastname);
        mtxtTitle.setText(Title);

    }
}
