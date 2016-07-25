package com.ashtonmansion.ashtonmansionapp.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansionapp.R;
import com.ashtonmansion.ashtonmansionapp.dao.DatabaseHandler;
import com.ashtonmansion.ashtonmansionapp.dbo.Appointment;
import com.clover.sdk.v1.printer.ReceiptContract;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {
    private DatabaseHandler dbHandler = new DatabaseHandler(this);
    private List<Appointment> appointmentList = new ArrayList<Appointment>();
    private TableLayout appointmentsTable;
    private TableRow newTableRow;
    private TextView idTv;
    private TextView dateTv;
    private TextView timeTv;
    private TextView durationTv;
    private TextView customerCodeTv;
    private TextView alertTypeTv;
    private TextView itemCodeTv;
    private TextView noteTv;
    private TextView emp1Tv;
    private TextView emp2Tv;
    private TextView confirmStatusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        appointmentsTable = (TableLayout) findViewById(R.id.appointments_table);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve Appointment List
        appointmentList = dbHandler.getAllAppointments();
        //Attach Appointments Table and formatting
        appointmentsTable.removeAllViews();

        //createAppointmentTableHeaderRow();
        createAppointmentTableHeaderRow();

        //Iterate and create rows
        for (Appointment appt : appointmentList) {
            //todo newTableRow.setLayoutParams();
            //Clear all TextViews and TableRow for new appointment
            newTableRow = new TableRow(this);

            idTv = new TextView(this);
            dateTv = new TextView(this);
            timeTv = new TextView(this);
            durationTv = new TextView(this);
            customerCodeTv = new TextView(this);
            alertTypeTv = new TextView(this);
            itemCodeTv = new TextView(this);
            noteTv = new TextView(this);
            emp1Tv = new TextView(this);
            emp2Tv = new TextView(this);
            confirmStatusTv = new TextView(this);

            //For each appointment, set the proper text view text and add to table
            idTv.setText(String.valueOf(appt.get_id()));
            dateTv.setText(appt.get_date());
            timeTv.setText(appt.get_start_time());
            durationTv.setText(String.valueOf(appt.get_duration()));
            customerCodeTv.setText(appt.get_customer_code());
            alertTypeTv.setText(appt.get_alert_type());
            itemCodeTv.setText(appt.get_item_code());
            noteTv.setText(appt.get_note());
            emp1Tv.setText(String.valueOf(appt.get_employee_code_1()));
            emp2Tv.setText(String.valueOf(appt.get_employee_code_2()));
            confirmStatusTv.setText(appt.get_confirm_status());
            //Add all text views to the new row
            //TODO DEBUG THIS
            newTableRow.addView(idTv);
            newTableRow.addView(dateTv);
            newTableRow.addView(timeTv);
            newTableRow.addView(durationTv);
            newTableRow.addView(customerCodeTv);
            newTableRow.addView(alertTypeTv);
            newTableRow.addView(itemCodeTv);
            newTableRow.addView(noteTv);
            newTableRow.addView(emp1Tv);
            newTableRow.addView(emp2Tv);
            newTableRow.addView(confirmStatusTv);

            appointmentsTable.addView(newTableRow);
        }
    }


    //OPEN THE ADD APPOINTMENT ACTIVITY
    public void displayAddAppointment(View view) {
        Intent addActivityIntent = new Intent(this, AddAppointmentActivity.class);
        startActivity(addActivityIntent);
    }

    //CREATE APPOINTMENT TABLE HEADER ROW METHOD
    public void createAppointmentTableHeaderRow() {
        TableRow headerRow = new TableRow(this);

        idTv = new TextView(this);
        idTv.setText("ID");
        dateTv = new TextView(this);
        dateTv.setText("Appointment Date");
        timeTv = new TextView(this);
        timeTv.setText("Time");
        durationTv = new TextView(this);
        durationTv.setText("Appt Duration");
        customerCodeTv = new TextView(this);
        customerCodeTv.setText("Customer Code");
        alertTypeTv = new TextView(this);
        alertTypeTv.setText("Alert Type");
        itemCodeTv = new TextView(this);
        itemCodeTv.setText("Item Code");
        noteTv = new TextView(this);
        noteTv.setText("Note");
        emp1Tv = new TextView(this);
        emp1Tv.setText("Employee 1");
        emp2Tv = new TextView(this);
        emp2Tv.setText("Emp 2");
        confirmStatusTv = new TextView(this);
        confirmStatusTv.setText("Status");

        headerRow.addView(idTv);
        headerRow.addView(dateTv);
        headerRow.addView(timeTv);
        headerRow.addView(durationTv);
        headerRow.addView(customerCodeTv);
        headerRow.addView(alertTypeTv);
        headerRow.addView(itemCodeTv);
        headerRow.addView(noteTv);
        headerRow.addView(emp1Tv);
        headerRow.addView(emp2Tv);
        headerRow.addView(confirmStatusTv);

        appointmentsTable.addView(headerRow);

    }
}
