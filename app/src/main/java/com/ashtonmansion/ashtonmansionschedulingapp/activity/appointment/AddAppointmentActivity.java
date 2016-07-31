package com.ashtonmansion.ashtonmansionschedulingapp.activity.appointment;

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

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansionschedulingapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansionschedulingapp.utility.WebServices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

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


    // working classes below
    private class insertAppointmentWSCall extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(AddAppointmentActivity.this);

        @Override
        protected void onPreExecute() {
            //Progress bar for insertion
            super.onPreExecute();

            progressDialog.setMessage("Inserting...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            final String SOAP_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched";
            final String SOAP_METHOD = "CreateAppt";
            final String SOAP_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS Canada%2C Inc./Codeunit/wsApptSched";
            final String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched:CreateAppt";

            try {
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.implicitTypes = true;
                envelope.setAddAdornments(false);
                envelope.dotNet = true;
                envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

                String testString = "test";

                request.addProperty("pID", testString);
                request.addProperty("pDate", testString);
                request.addProperty("pTime", testString);
                request.addProperty("pCustCode", testString);
                request.addProperty("pDuration", testString);
                request.addProperty("pAlerttype", testString);
                request.addProperty("pItemCode", testString);
                request.addProperty("pNotes", testString);
                request.addProperty("pEmpl1", testString);
                request.addProperty("pEmpl2", testString);
                request.addProperty("pConfStatus", testString);

                envelope.setOutputSoapObject(request);

                HttpTransportSE ht = new HttpTransportSE(SOAP_URL);
                ht.debug = true;

                ht.call(SOAP_ACTION, envelope);
                Log.i("Request dump: ", ht.requestDump);
                Log.i("HTTP RESPONSE: ", ht.responseDump);
            } catch (Exception e) {
                Log.i("Call error: ", e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Close the progress bar
            progressDialog.dismiss();
        }
    }

    public void callInsertAppointmentWSCall(View view) {
        new insertAppointmentWSCall().execute();
    }

    public void submitAddAppointment(View view) {
        apptDAO = new AppointmentDAO(this);
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
        apptDAO.addAppointment(appointment);

        //todo uncomment when ready to debug
        //new insertAppointmentWSCall().execute();
    }

    //Method to return when cancel button is pressed
    public void cancelAddAppointment(View view) {
        finish();
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
}