package com.ashtonmansion.ashtonmansioncloverapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws.AppointmentWebServices;

public class SandboxActivity extends AppCompatActivity {
    private TextView countryIsdTV;
    private TextView testSuccessTV;
    private boolean insertApptResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);
        countryIsdTV = (TextView) findViewById(R.id.country_isd);
        testSuccessTV = (TextView) findViewById(R.id.return_hello_tv);
    }


    public void callTestSOAPWS(View view) {
        new testSoapWSCall().execute();
    }

    private class testSoapWSCall extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(SandboxActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            AppointmentWebServices apptWebService = new AppointmentWebServices();

            Appointment bsappt = new Appointment();
            bsappt.set_id("15");
            bsappt.set_date("DATE");
            bsappt.set_start_time("TIME");
            bsappt.set_customer_code("custcode");
            bsappt.set_duration("12");
            bsappt.set_alert_type("email");
            bsappt.set_item_code("itemcode");
            bsappt.set_note("notesjava");
            bsappt.set_employee_code_1("emp1");
            bsappt.set_employee_code_2("emp2");
            bsappt.set_confirm_status("confirmed");

            //  String result = testService.ntlmTest();

            insertApptResult = apptWebService.addAppointmentViaWS(bsappt);
            Log.i("Result inner:", "" + insertApptResult);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Close the progress bar
            progressDialog.dismiss();
        }
    }
}
