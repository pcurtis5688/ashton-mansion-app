package com.ashtonmansion.ashtonmansionschedulingapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.utility.CallSoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SandboxActivity extends AppCompatActivity {
    private TextView countryIsdTV;
    private String testSoapAgainstOnlineStr;
    private String testSoapAgainstMineStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        countryIsdTV = (TextView) findViewById(R.id.country_isd);

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
            CallSoap soapHandler = new CallSoap();
            testSoapAgainstOnlineStr = soapHandler.GetISD("India");


            testSoapAgainstMineStr = soapHandler.soapHello("companyName");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Close the progress bar
            progressDialog.dismiss();
            countryIsdTV.setText("Country ISD: " + testSoapAgainstOnlineStr);
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
            HttpTransportSE transport = new HttpTransportSE(url);

            transport.call(soap_action, envelope);
            Log.i("Response string", envelope.getResponse().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("E string", e.toString());
        }
    }
}
