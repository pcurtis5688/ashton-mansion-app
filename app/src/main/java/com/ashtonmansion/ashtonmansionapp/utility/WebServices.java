package com.ashtonmansion.ashtonmansionapp.utility;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Date;

/**
 * Created by paul on 7/26/2016.
 */
public class WebServices extends AsyncTask<Void, Void, String> {
    private String SOAP_NAMESPACE = "urn:urn:microsoft-dynamics-schemas/codeunit/wsApptSched";

    private String SOAP_URL = "http://10.0.2.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/wsApptSched";
    private String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsApptSched:CreateAppt";
    private String SOAP_METHOD = "CreateAppt";

    private SoapObject request;
    private PropertyInfo pi1;
    private PropertyInfo pi2;
    private PropertyInfo pi3;
    private PropertyInfo pi4;
    private PropertyInfo pi5;
    private PropertyInfo pi6;
    private PropertyInfo pi7;
    private PropertyInfo pi8;
    private PropertyInfo pi9;
    private PropertyInfo pi10;
    private PropertyInfo pi11;


    @Override
    protected String doInBackground(Void... params) {
        String testString = "test";

        request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

        pi1 = new PropertyInfo();
        pi1.setName("pID");
        pi1.setValue(testString);
        request.addProperty(pi1);

        pi2 = new PropertyInfo();
        pi2.setName("pDate");
        pi2.setValue(new Date().toString());
        request.addProperty(pi2);

        pi3 = new PropertyInfo();
        pi3.setName("pTime");
        pi3.setValue(testString);
        request.addProperty(pi3);

        pi4 = new PropertyInfo();
        pi4.setName("pCustCode");
        pi4.setValue(testString);
        request.addProperty(pi4);

        pi5 = new PropertyInfo();
        pi5.setName("pDuration");
        pi5.setValue("1.5");
        request.addProperty(pi5);

        pi6 = new PropertyInfo();
        pi6.setName("pAlerttype");
        pi6.setValue("email");
        request.addProperty(pi6);

        pi7 = new PropertyInfo();
        pi7.setName("pItemCode");
        pi7.setValue(testString);
        request.addProperty(pi7);

        pi8 = new PropertyInfo();
        pi8.setName("pNotes");
        pi8.setValue(testString);
        request.addProperty(pi8);

        pi9 = new PropertyInfo();
        pi9.setName("pEmpl1");
        pi9.setValue(testString);
        request.addProperty(pi9);

        pi10 = new PropertyInfo();
        pi10.setName("pEmpl2");
        pi10.setValue(testString);
        request.addProperty(pi10);

        pi11 = new PropertyInfo();
        pi11.setName("pConfStatus");
        pi11.setValue("Confirmed");
        request.addProperty(pi11);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            Log.i("worked", new String("worked"));
        } catch (Exception e) {
            Log.i("WS Error-->", e.toString());
            Log.i("hi", new String("error"));
        }


        return "string";
    }
}
