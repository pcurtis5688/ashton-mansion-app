package com.ashtonmansion.ashtonmansionschedulingapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.utility.CallSoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

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
            String testSoapAgainstOnline = CallSoap.GetISD("India");
            String testStringForMine = "testString";

            String SOAP_URL = "http://laptop-53b1c7v6:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/wsApptSched";
            String SOAP_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched";
            String SOAP_METHOD = "CreateAppt";
            String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched:CreateAppt";

            SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("pID");
            pi1.setValue(testStringForMine);
            request.addProperty(pi1);

            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("pDate");
            pi2.setValue(testStringForMine);
            request.addProperty(pi2);

            PropertyInfo pi3 = new PropertyInfo();
            pi3.setName("pTime");
            pi3.setValue(testStringForMine);
            request.addProperty(pi3);

            PropertyInfo pi4 = new PropertyInfo();
            pi4.setName("pCustCode");
            pi4.setValue(testStringForMine);
            request.addProperty(pi4);

            PropertyInfo pi5 = new PropertyInfo();
            pi5.setName("pDuration");
            pi5.setValue(testStringForMine);
            request.addProperty(pi5);

            PropertyInfo pi6 = new PropertyInfo();
            pi6.setName("palerttype");
            pi6.setValue(testStringForMine);
            request.addProperty(pi6);

            PropertyInfo pi7 = new PropertyInfo();
            pi7.setName("pItemCode");
            pi7.setValue(testStringForMine);
            request.addProperty(pi7);

            PropertyInfo pi8 = new PropertyInfo();
            pi8.setName("pNotes");
            pi8.setValue(testStringForMine);
            request.addProperty(pi8);

            PropertyInfo pi9 = new PropertyInfo();
            pi9.setName("pEmpl1");
            pi9.setValue(testStringForMine);
            request.addProperty(pi9);

            PropertyInfo pi10 = new PropertyInfo();
            pi10.setName("pEmpl2");
            pi10.setValue(testStringForMine);
            request.addProperty(pi10);

            PropertyInfo pi11 = new PropertyInfo();
            pi11.setName("pConfStatus");
            pi11.setValue(testStringForMine);
            request.addProperty(pi11);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Close the progress bar
            progressDialog.dismiss();
        }
    }


    public void WithNTLM() {
        String namespace = "urn:microsoft-dynamics-schemas/page/salesorder";
        String url = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS Canada%2C Inc./Page/SalesOrder";
        String soap_action = "urn:microsoft-dynamics-schemas/page/salesorder:Read";
        String method_name = "Read";

        SoapObject request = new SoapObject(namespace, method_name);

        PropertyInfo custProp = new PropertyInfo();
        custProp.setName("CountryName");
        custProp.setValue("India");
        custProp.setType(String.class);
        request.addProperty(custProp);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        String response = null;

        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(url);


//            request.addProperty("pID", testString);
//            request.addProperty("pDate", testString);
//            request.addProperty("pTime", testString);
//            request.addProperty("pCustCode", testString);
//            request.addProperty("pDuration", testString);
//            request.addProperty("pAlerttype", testString);
//            request.addProperty("pItemCode", testString);
//            request.addProperty("pNotes", testString);
//            request.addProperty("pEmpl1", testString);
//            request.addProperty("pEmpl2", testString);
//            request.addProperty("pConfStatus", testString);
            //////////////////////
//            String custID = "323111";
//            PropertyInfo custProp = new PropertyInfo();
//            custProp.setName("No");
//            custProp.setValue(custID);
//            custProp.setType(String.class);
//            request.addProperty(custProp);


            HttpTransportSE transport = new HttpTransportSE(url);

            transport.call(soap_action, envelope);
//            NtlmTransport ntlm = new NtlmTransport();
//            ntlm.setCredentials(url, "USER", "PW", "DOMAIN","EMPTY??");
//            ntlm.call(soap_action, envelope); // Receive Error here!
//            SoapObject result = (SoapObject) envelope.getResponse();
            Log.i("Response string", envelope.getResponse().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("E string", e.toString());
        }
    }

}
