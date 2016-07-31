package com.ashtonmansion.ashtonmansionschedulingapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ashtonmansion.ashtonmansionschedulingapp.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SandboxActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);
    }

    public void callTestSOAPWS(View view) {
        new testSoapWSCall().execute();
    }

    private class testSoapWSCall extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(SandboxActivity.this);

        @Override
        protected void onPreExecute() {
            //Progress bar for insertion
            super.onPreExecute();

            progressDialog.setMessage("Making call...");
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
}
