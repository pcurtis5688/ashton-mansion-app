package com.ashtonmansion.ashtonmansioncloverapp.activity.appointment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws.AppointmentWebServices;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {
    private Context appointmentsActivityContext;
    private AppointmentDAO appointmentDAO;
    private List<Appointment> appointmentList = new ArrayList<>();
    private TableLayout appointmentsTable;

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
        headerIDTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerDateTV.setText(R.string.header_appt_table_appt_date);
        headerDateTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerTimeTV.setText(R.string.header_appt_table_appt_time);
        headerTimeTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerDurationTV.setText(R.string.header_appt_table_appt_duration);
        headerDurationTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerCustCodeTV.setText(R.string.header_appt_table_appt_customer_code);
        headerCustCodeTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerAlertTypeTV.setText(R.string.header_appt_table_appt_alert_type);
        headerAlertTypeTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerItemCodeTV.setText(R.string.header_appt_table_appt_item_code);
        headerItemCodeTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerNoteTV.setText(R.string.header_appt_table_appt_note);
        headerNoteTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerEmp1TV.setText(R.string.header_appt_table_appt_emp_1);
        headerEmp1TV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerEmp2TV.setText(R.string.header_appt_table_appt_emp_2);
        headerEmp2TV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
        headerConfirmStatusTV.setText(R.string.header_appt_table_appt_status);
        headerConfirmStatusTV.setTypeface(Typeface.DEFAULT, R.style.TableHeaderStyle);
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

    private void refreshAppointmentTable() {
        //GET NEW APPOINTMENT LIST
        appointmentList = appointmentDAO.getAllAppointments();
        //Clear the appointment table
        appointmentsTable.removeAllViews();
        //Create the table header row and add to table
        createAppointmentTableHeaderRow();
        //Iterate and create rows
        for (final Appointment appt : appointmentList) {
            //todo newTableRow.setLayoutParams();
            //CREATE NEW ROW FOR TABLE
            TableRow newApptRow = new TableRow(this);
            //CREATE NEW COLUMNS FOR ROW
            TextView idTV = new TextView(this, null, R.style.CenteredWhiteText);

            TextView dateTV = new TextView(this);
            dateTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView timeTV = new TextView(this);
            timeTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView durationTV = new TextView(this);
            durationTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView customerCodeTV = new TextView(this);
            customerCodeTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView alertTypeTV = new TextView(this);
            alertTypeTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView itemCodeTV = new TextView(this);
            itemCodeTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView noteTV = new TextView(this);
            noteTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView emp1TV = new TextView(this);
            emp1TV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView emp2TV = new TextView(this);
            emp2TV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            TextView confirmStatusTV = new TextView(this);
            confirmStatusTV.setTypeface(Typeface.DEFAULT, R.style.CenteredWhiteText);
            //HANDLE THE DATES AND TIMES
            String appt_date = appt.get_date();
            String appt_time = appt.get_start_time();

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
            //DELETE BUTTON
            Button deleteApptButton = new Button(appointmentsActivityContext);
            deleteApptButton.setText(R.string.delete_string_for_buttons);
            deleteApptButton.setTypeface(Typeface.DEFAULT_BOLD, R.color.white);
            deleteApptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteThisAppointment(appt);
                }
            });
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
            newApptRow.addView(deleteApptButton);
            //ADD NEW ROW TO TABLE
            appointmentsTable.addView(newApptRow);
        }
    }

    public void displayAddAppointment(View view) {
        Intent addActivityIntent = new Intent(this, AddAppointmentActivity.class);
        startActivity(addActivityIntent);
    }

    private void deleteThisAppointment(final Appointment apptToDelete) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Appointment?")
                .setMessage("Delete Appointment " + apptToDelete.get_id() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String confirmationToastString = "Appointment " + apptToDelete.get_id() + " Deleted!";
                        deleteAppointment(apptToDelete.get_id());
                        Toast.makeText(AppointmentsActivity.this, confirmationToastString, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void deleteAppointment(final String appointmentID) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progressDialog = new ProgressDialog(appointmentsActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Deleting Appointment...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                //DELETE THE APPOINTMENT LOCALLY
                AppointmentDAO apptDAO = new AppointmentDAO(appointmentsActivityContext);
                apptDAO.deleteAppointmentByID(appointmentID);
                //MAKE THE WEB SERVICE CALL TO DELETE THE APPOINTMENT
                AppointmentWebServices appointmentWebServices = new AppointmentWebServices();
                appointmentWebServices.deleteAppointmentByIDWS(appointmentID);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                refreshAppointmentTable();
                progressDialog.dismiss();
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        appointmentsActivityContext = this;
        appointmentDAO = new AppointmentDAO(appointmentsActivityContext);
        appointmentsTable = (TableLayout) findViewById(R.id.appointments_table);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Retrieve Appointment List and Refresh Page
        refreshAppointmentTable();
    }
}
