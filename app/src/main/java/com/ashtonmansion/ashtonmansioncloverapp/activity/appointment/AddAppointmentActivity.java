package com.ashtonmansion.ashtonmansioncloverapp.activity.appointment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.utility.GlobalUtils;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws.AppointmentWebServices;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAppointmentActivity extends AppCompatActivity {
    //ACTIVITY VARS
    private Context addApptContext;
    ///////////SERVICE VARS
    private AppointmentDAO apptDAO;
    private Appointment appointment;
    private boolean addApptWebServiceSuccess;
    /////////// FIELD VARS
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        addApptContext = this;

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

    public void submitAddAppointment(View view) {
        apptDAO = new AppointmentDAO(this);
        appointment = new Appointment();

        //HANDLE DATE FROM DATEPICKER
        int dateMonth = datePicker.getMonth();
        int dateDay = datePicker.getDayOfMonth();
        int dateYear = datePicker.getYear();
        String formattedDate = GlobalUtils.formatDate(dateMonth, dateDay, dateYear);
        //todo handle this date...
        //HANDLE TIME FROM TIMEPICKER
        int apptHour = timePicker.getCurrentHour();
        int apptMinute = timePicker.getCurrentMinute();
        Time appt_time = new Time(apptHour, apptMinute, 0);
        //CONSTRUCT THE APPOINTMENT OBJECT
        appointment.set_date(formattedDate);
        appointment.set_start_time(appt_time.toString());
        appointment.set_duration(durationText.getText().toString());
        appointment.set_customer_code(customerCodeText.getText().toString());
        appointment.set_alert_type(alertTypeSpinner.getSelectedItem().toString());
        appointment.set_item_code(itemCodeText.getText().toString());
        appointment.set_note(apptNoteText.getText().toString());
        appointment.set_employee_code_1(emp1Text.getText().toString());
        appointment.set_employee_code_2(emp2Text.getText().toString());
        appointment.set_confirm_status(apptConfirmStatusSpinner.getSelectedItem().toString());
        apptDAO.addAppointment(appointment);
        ////////////////////LOCAL DB UPDATED, NOW CALL DYNAMICS WS
        callApptWSInBackground();
    }

    private void callApptWSInBackground() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(addApptContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setMessage("Adding Appointment...");
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                AppointmentWebServices apptWebService = new AppointmentWebServices();
                addApptWebServiceSuccess = apptWebService.addAppointmentViaWS(appointment);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                closeOutActivity();
                progress.dismiss();
            }
        }.execute();
    }

    private void closeOutActivity() {
        if (addApptWebServiceSuccess) {
            Log.i("Web Service: ", "SUCCESS");
        } else {
            Log.e("Web Service: ", "FAILURE");
        }
        apptDAO.close();
        finish();
    }

    public void cancelAddAppointment(View view) {
        finish();
    }
}
