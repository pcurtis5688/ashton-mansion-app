package com.ashtonmansion.ashtonmansioncloverapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.utility.TestWS;


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
            TestWS testService = new TestWS();

            testSoapAgainstOnlineStr = testService.GetISD("India");
            Log.i("ISD is: ", testSoapAgainstMineStr);

            testSoapAgainstMineStr = testService.sayHiToSoap("hello");
            Log.i("Soap says: ", testSoapAgainstMineStr);

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


}
