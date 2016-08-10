package com.ashtonmansion.ashtonmansioncloverapp.activity.appointment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws.AppointmentWebServices;

public class AddAppointmentActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText durationText;
    private EditText customerCodeText;
    private Spinner alertTypeSpinner;
    private EditText itemCodeText;
    private EditText apptNoteText;
    private EditText emp1Text;
    private EditText emp2Text;
    private Spinner apptConfirmStatusSpinner;
    private AppointmentDAO apptDAO;
    private Appointment appointment;
    // POST EXECUTION VARS
    private boolean webServiceSuccess;


    // working classes below
    private class callApptCreateJaxWSInBackground extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(AddAppointmentActivity.this);

        @Override
        protected void onPreExecute() {
            //Progress bar for insertion
            super.onPreExecute();
            progressDialog.setMessage("Creating ...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            webServiceSuccess = AppointmentWebServices.insertAppointment(appointment);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Close the progress bar
            progressDialog.dismiss();
        }
    }

    public void submitAddAppointment(View view) {
        apptDAO = new AppointmentDAO(this);
        appointment = new Appointment();
        appointment.set_date("fixthis");
        appointment.set_start_time("fixthis");
        appointment.set_duration(durationText.getText().toString());
        appointment.set_customer_code(customerCodeText.getText().toString());
        appointment.set_alert_type(alertTypeSpinner.getSelectedItem().toString());
        appointment.set_item_code(itemCodeText.getText().toString());
        appointment.set_note(apptNoteText.getText().toString());
        appointment.set_employee_code_1(emp1Text.getText().toString());
        appointment.set_employee_code_2(emp2Text.getText().toString());
        appointment.set_confirm_status(apptConfirmStatusSpinner.getSelectedItem().toString());
        apptDAO.addAppointment(appointment);

        //todo uncomment when ready to debug

        new callApptCreateJaxWSInBackground();

        if (webServiceSuccess == true) {
            Log.i("Web Service: ", "SUCCESS");
        } else {
            Log.i("Web Service: ", "FAILURE");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        datePicker = (DatePicker) findViewById(R.id.appt_date);
        timePicker = (TimePicker) findViewById(R.id.appt_start_time);
        durationText = (EditText) findViewById(R.id.appt_duration);
        customerCodeText = (EditText) findViewById(R.id.appt_customer_code);
        alertTypeSpinner = (Spinner) findViewById(R.id.appt_alert_type_spinner);
        itemCodeText = (EditText) findViewById(R.id.appt_item_code);
        apptNoteText = (EditText) findViewById(R.id.appt_note);
        emp1Text = (EditText) findViewById(R.id.appt_emp_1_assigned);
        emp2Text = (EditText) findViewById(R.id.appt_emp_2_assigned);
        apptConfirmStatusSpinner = (Spinner) findViewById(R.id.appt_confirm_status);
        emp2Text = (EditText) findViewById(R.id.appt_emp_2_assigned);
    }

    //Method to return when cancel button is pressed
    public void cancelAddAppointment(View view) {
        finish();
    }
}