package com.ashtonmansion.ashtonmansionapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ashtonmansion.ashtonmansionapp.R;
import com.ashtonmansion.ashtonmansionapp.dao.DatabaseHandler;
import com.ashtonmansion.ashtonmansionapp.dbo.Appointment;

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
    private DatabaseHandler dbHandler;
    private Appointment appointment;


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


    }

    public void finalizeAddAppointment(View view) {
        //TODO test
        dbHandler = new DatabaseHandler(this);
        appointment = new Appointment();
        appointment.set_date("fixthis");
        appointment.set_start_time("fixthis");
        appointment.set_duration(Integer.parseInt(durationText.getText().toString()));
        appointment.set_customer_code(customerCodeText.getText().toString());
        appointment.set_alert_type(alertTypeSpinner.getSelectedItem().toString());
        appointment.set_item_code(itemCodeText.getText().toString());
        appointment.set_note(apptNoteText.getText().toString());
        appointment.set_employee_code_1(Integer.parseInt(emp1Text.getText().toString()));
        appointment.set_employee_code_2(Integer.parseInt(emp2Text.getText().toString()));
        appointment.set_confirm_status(apptConfirmStatusSpinner.getSelectedItem().toString());
        dbHandler.addAppointment(appointment);

    }

    //Method to return when cancel button is pressed
    public void cancelAddAppointment(View view) {
        finish();
    }
}
