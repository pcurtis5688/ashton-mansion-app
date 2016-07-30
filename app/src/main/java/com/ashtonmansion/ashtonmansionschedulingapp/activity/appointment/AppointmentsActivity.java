package com.ashtonmansion.ashtonmansionschedulingapp.activity.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.activity.appointment.AddAppointmentActivity;
import com.ashtonmansion.ashtonmansionschedulingapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansionschedulingapp.dbo.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {
    private AppointmentDAO apptDAO;
    private List<Appointment> appointmentList = new ArrayList<Appointment>();
    private TableLayout appointmentsTable;
    private TableRow headerRow;
    private TableRow newApptRow;
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
        apptDAO = new AppointmentDAO(this);
        apptDAO.createAppointmentTable();
        appointmentsTable = (TableLayout) findViewById(R.id.appointments_table);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve Appointment List
        appointmentList = apptDAO.getAllAppointments();

        refreshAppointmentTable();
    }


    //OPEN THE ADD APPOINTMENT ACTIVITY
    public void displayAddAppointment(View view) {
        Intent addActivityIntent = new Intent(this, AddAppointmentActivity.class);
        startActivity(addActivityIntent);
    }


    private void refreshAppointmentTable() {
        //Clear the appointment table
        appointmentsTable.removeAllViews();
        //Create the table header row and add to table
        createAppointmentTableHeaderRow();
        //Iterate and create rows
        for (Appointment appt : appointmentList) {
            //todo newTableRow.setLayoutParams();
            //Create new appointment row to be added to table
            newApptRow = new TableRow(this);
            //Create new data fields to be added to row
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
            //Set data for each new row
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
            newApptRow.addView(idTv);
            newApptRow.addView(dateTv);
            newApptRow.addView(timeTv);
            newApptRow.addView(durationTv);
            newApptRow.addView(customerCodeTv);
            newApptRow.addView(alertTypeTv);
            newApptRow.addView(itemCodeTv);
            newApptRow.addView(noteTv);
            newApptRow.addView(emp1Tv);
            newApptRow.addView(emp2Tv);
            newApptRow.addView(confirmStatusTv);
            //Add the new row to the table
            appointmentsTable.addView(newApptRow);
        }
    }


    //CREATE APPOINTMENT TABLE HEADER ROW METHOD
    public void createAppointmentTableHeaderRow() {
        headerRow = new TableRow(this);

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
