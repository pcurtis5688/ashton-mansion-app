package com.ashtonmansion.ashtonmansioncloverapp.activity.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {
    private AppointmentDAO apptDAO;
    private List<Appointment> appointmentList = new ArrayList<>();
    private TableLayout appointmentsTable;

    private void refreshAppointmentTable() {
        //Clear the appointment table
        appointmentsTable.removeAllViews();
        //Create the table header row and add to table
        createAppointmentTableHeaderRow();
        //Iterate and create rows
        for (Appointment appt : appointmentList) {
            //todo newTableRow.setLayoutParams();
            //CREATE NEW ROW FOR TABLE
            TableRow newApptRow = new TableRow(this);
            //CREATE NEW COLUMNS FOR ROW
            TextView idTV = new TextView(this);
            TextView dateTV = new TextView(this);
            TextView timeTV = new TextView(this);
            TextView durationTV = new TextView(this);
            TextView customerCodeTV = new TextView(this);
            TextView alertTypeTV = new TextView(this);
            TextView itemCodeTV = new TextView(this);
            TextView noteTV = new TextView(this);
            TextView emp1TV = new TextView(this);
            TextView emp2TV = new TextView(this);
            TextView confirmStatusTV = new TextView(this);
            //SET DATA FOR EACH COLUMN
            idTV.setText(String.valueOf(appt.get_id()));
            dateTV.setText(appt.get_date());
            timeTV.setText(appt.get_start_time());
            durationTV.setText(String.valueOf(appt.get_duration()));
            customerCodeTV.setText(appt.get_customer_code());
            alertTypeTV.setText(appt.get_alert_type());
            itemCodeTV.setText(appt.get_item_code());
            noteTV.setText(appt.get_note());
            emp1TV.setText(String.valueOf(appt.get_employee_code_1()));
            emp2TV.setText(String.valueOf(appt.get_employee_code_2()));
            confirmStatusTV.setText(appt.get_confirm_status());
            //ADD ALL TV'S TO NEW ROW
            newApptRow.addView(idTV);
            newApptRow.addView(dateTV);
            newApptRow.addView(timeTV);
            newApptRow.addView(durationTV);
            newApptRow.addView(customerCodeTV);
            newApptRow.addView(alertTypeTV);
            newApptRow.addView(itemCodeTV);
            newApptRow.addView(noteTV);
            newApptRow.addView(emp1TV);
            newApptRow.addView(emp2TV);
            newApptRow.addView(confirmStatusTV);
            //ADD NEW ROW TO TABLE
            appointmentsTable.addView(newApptRow);
        }
    }

    public void createAppointmentTableHeaderRow() {
        //MAKE THE HEADER ROW
        TableRow headerRow = new TableRow(this);
        //SET THE COLUMN HEADERS
        TextView headerIDTV = new TextView(this);
        TextView headerDateTV = new TextView(this);
        TextView headerTimeTV = new TextView(this);
        TextView headerDurationTV = new TextView(this);
        TextView headerCustCodeTV = new TextView(this);
        TextView headerAlertTypeTV = new TextView(this);
        TextView headerItemCodeTV = new TextView(this);
        TextView headerNoteTV = new TextView(this);
        TextView headerEmp1TV = new TextView(this);
        TextView headerEmp2TV = new TextView(this);
        TextView headerConfirmStatusTV = new TextView(this);
        headerIDTV.setText(R.string.header_appt_table_appt_ID);
        headerDateTV.setText(R.string.header_appt_table_appt_date);
        headerTimeTV.setText(R.string.header_appt_table_appt_time);
        headerDurationTV.setText(R.string.header_appt_table_appt_duration);
        headerCustCodeTV.setText(R.string.header_appt_table_appt_customer_code);
        headerAlertTypeTV.setText(R.string.header_appt_table_appt_alert_type);
        headerItemCodeTV.setText(R.string.header_appt_table_appt_item_code);
        headerNoteTV.setText(R.string.header_appt_table_appt_note);
        headerEmp1TV.setText(R.string.header_appt_table_appt_emp_1);
        headerEmp2TV.setText(R.string.header_appt_table_appt_emp_2);
        headerConfirmStatusTV.setText(R.string.header_appt_table_appt_status);
        //ADD THE COLUMN HEADERS TO THE ROW
        headerRow.addView(headerIDTV);
        headerRow.addView(headerDateTV);
        headerRow.addView(headerTimeTV);
        headerRow.addView(headerDurationTV);
        headerRow.addView(headerCustCodeTV);
        headerRow.addView(headerAlertTypeTV);
        headerRow.addView(headerItemCodeTV);
        headerRow.addView(headerNoteTV);
        headerRow.addView(headerEmp1TV);
        headerRow.addView(headerEmp2TV);
        headerRow.addView(headerConfirmStatusTV);
        //ADD THE ROW TO THE TABLE
        appointmentsTable.addView(headerRow);
    }

    public void displayAddAppointment(View view) {
        Intent addActivityIntent = new Intent(this, AddAppointmentActivity.class);
        startActivity(addActivityIntent);
    }

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
}
