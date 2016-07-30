package com.ashtonmansion.ashtonmansionschedulingapp.activity;

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

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

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

    private static final String SOAP_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/";
    private static final String SOAP_METHOD = "CreateAppt";
    private static final String SOAP_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%252C%20Inc./Codeunit/wsApptSched";
    private static final String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched:CreateAppt";

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
            SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);
            String testString = "test";

            PropertyInfo pi1 = new PropertyInfo();
            //  pi1.name = "pID";
            pi1.setName("pID");
            pi1.setValue(testString);
            pi1.type = String.class;
            request.addProperty(pi1);

            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("pDate");
            pi2.setValue(testString);
            pi2.type = String.class;
            request.addProperty(pi2);

            PropertyInfo pi3 = new PropertyInfo();
            pi3.setName("pTime");
            pi3.setValue(testString);
            pi3.setType(String.class);
            request.addProperty(pi3);

            PropertyInfo pi4 = new PropertyInfo();
            pi4.setName("pCustCode");
            pi4.setValue(testString);
            pi4.setType(String.class);

            request.addProperty(pi4);

            PropertyInfo pi5 = new PropertyInfo();
            pi5.setName("pDuration");
            pi5.setValue("1.5");
            pi5.setType(String.class);

            request.addProperty(pi5);

            PropertyInfo pi6 = new PropertyInfo();
            pi6.setName("pAlerttype");
            pi6.setValue("email");
            pi6.setType(String.class);
            request.addProperty(pi6);

            PropertyInfo pi7 = new PropertyInfo();
            pi7.setName("pItemCode");
            pi7.setValue(testString);
            pi7.setType(String.class);
            request.addProperty(pi7);

            PropertyInfo pi8 = new PropertyInfo();
            pi8.setName("pNotes");
            pi8.setValue(testString);
            pi8.setType(String.class);
            request.addProperty(pi8);

            PropertyInfo pi9 = new PropertyInfo();
            pi9.setName("pEmpl1");
            pi9.setValue(testString);
            pi9.setType(String.class);
            request.addProperty(pi9);

            PropertyInfo pi10 = new PropertyInfo();
            pi10.setName("pEmpl2");
            pi10.setValue(testString);
            pi10.setType(String.class);
            request.addProperty(pi10);

            PropertyInfo pi11 = new PropertyInfo();
            pi11.setName("pConfStatus");
            pi11.setValue("Confirmed");
            pi11.setType(String.class);
            request.addProperty(pi11);

            SoapSerializationEnvelope envelope = WebServices.getSoapEnvelope(request);
            HttpTransportSE androidHttpTransport = WebServices.getHttpTransportSE(SOAP_URL);

            try {
                String responsedump = androidHttpTransport.responseDump;
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                Log.i("Response: ", responsedump);
            } catch (XmlPullParserException | IOException e) {
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