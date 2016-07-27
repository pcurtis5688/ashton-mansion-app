package com.ashtonmansion.ashtonmansionapp.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ashtonmansion.ashtonmansionapp.R;
import com.ashtonmansion.ashtonmansionapp.dao.DatabaseHandler;
import com.ashtonmansion.ashtonmansionapp.dbo.Appointment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Date;

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

    public void insertAppointmentWS(View view) {
        new AsyncTask<Void, Void, String>() {
            String SOAP_NAMESPACE = "urn:urn:microsoft-dynamics-schemas/codeunit/wsApptSched";

            String SOAP_URL = "http://10.50.50.50:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/wsApptSched";
            String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched:CreateAppt";
            String SOAP_METHOD = "CreateAppt";

            SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

            String testString = "test";

            @Override
            protected String doInBackground(Void... params) {
                try {
                    PropertyInfo pi1 = new PropertyInfo();
                    pi1.setName("pID");
                    pi1.setValue(testString);
                    request.addProperty(pi1);

                    PropertyInfo pi2 = new PropertyInfo();
                    pi2.setName("pDate");
                    pi2.setValue(new Date().toString());
                    request.addProperty(pi2);

                    PropertyInfo pi3 = new PropertyInfo();
                    pi3.setName("pTime");
                    pi3.setValue(testString);
                    request.addProperty(pi3);

                    PropertyInfo pi4 = new PropertyInfo();
                    pi4.setName("pCustCode");
                    pi4.setValue(testString);
                    request.addProperty(pi4);

                    PropertyInfo pi5 = new PropertyInfo();
                    pi5.setName("pDuration");
                    pi5.setValue("1.5");
                    request.addProperty(pi5);

                    PropertyInfo pi6 = new PropertyInfo();
                    pi6.setName("pAlerttype");
                    pi6.setValue("email");
                    request.addProperty(pi6);

                    PropertyInfo pi7 = new PropertyInfo();
                    pi7.setName("pItemCode");
                    pi7.setValue(testString);
                    request.addProperty(pi7);

                    PropertyInfo pi8 = new PropertyInfo();
                    pi8.setName("pNotes");
                    pi8.setValue(testString);
                    request.addProperty(pi8);

                    PropertyInfo pi9 = new PropertyInfo();
                    pi9.setName("pEmpl1");
                    pi9.setValue(testString);
                    request.addProperty(pi9);

                    PropertyInfo pi10 = new PropertyInfo();
                    pi10.setName("pEmpl2");
                    pi10.setValue(testString);
                    request.addProperty(pi10);

                    PropertyInfo pi11 = new PropertyInfo();
                    pi11.setName("pConfStatus");
                    pi11.setValue("Confirmed");
                    request.addProperty(pi11);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);

                    androidHttpTransport.call(SOAP_ACTION, envelope);

                    Log.i("worked", new String("worked"));
                    return "success";
                } catch (Exception e) {
                    Log.i("WS Error-->", e.toString());
                    Log.i("hi", new String("error"));
                }

                return "success";
            }

        }.execute();
    }
}
