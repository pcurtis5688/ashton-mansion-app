package com.ashtonmansion.ashtonmansionschedulingapp.utility;

import android.util.Log;

import com.ashtonmansion.ashtonmansionschedulingapp.dbo.Appointment;
import com.clover.sdk.v3.apps.App;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnectionSE;

import java.net.Authenticator;
import java.util.List;

/**
 * Created by paul on 7/31/2016.
 */
public class CallSoap {

    public String soapHello(String helloIn) {

        WebServices services = new WebServices();
        return services.connectToNtlm("why hello");
    }

    public static String GetISD(String countryname) {
        String SOAP_URL = "http://www.webservicex.net/country.asmx";
        String SOAP_ACTION = "http://www.webserviceX.NET/GetISD";
        String SOAP_METHOD = "GetISD";
        String SOAP_NAMESPACE = "http://www.webserviceX.NET";

        SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

        PropertyInfo custProp = new PropertyInfo();
        custProp.setName("CountryName");
        custProp.setValue(countryname);
        custProp.setType(String.class);
        request.addProperty(custProp);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        String response = null;

        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_URL);
            httpTransportSE.debug = true;

            Log.e("REQUEST=", "" + request);
            Log.e("SOAP ENVOLP=", "envolpe=" + envelope);


            httpTransportSE.call(SOAP_ACTION, envelope);

            response = httpTransportSE.responseDump;

        } catch (Exception e) {
            Log.i("Call exception: ", e.toString());
        }
        return response;
    }

    public static String InsertAppt(String companyName) {
        String testStringForMine = "testString";

        String SOAP_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./services/Codeunit/";
        String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";

        String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsAppt:CreateAppt";

        String SOAP_METHOD = "CreateAppt";
        SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("pID");
        pi1.setValue(testStringForMine);
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("pDate");
        pi2.setValue(testStringForMine);
        pi2.setType(String.class);
        request.addProperty(pi2);

        PropertyInfo pi3 = new PropertyInfo();
        pi3.setName("pTime");
        pi3.setValue(testStringForMine);
        pi3.setType(String.class);
        request.addProperty(pi3);

        PropertyInfo pi4 = new PropertyInfo();
        pi4.setName("pCustCode");
        pi4.setValue(testStringForMine);
        pi4.setType(String.class);
        request.addProperty(pi4);

        PropertyInfo pi5 = new PropertyInfo();
        pi5.setName("pDuration");
        pi5.setValue(testStringForMine);
        pi5.setType(String.class);
        request.addProperty(pi5);

        PropertyInfo pi6 = new PropertyInfo();
        pi6.setName("palerttype");
        pi6.setValue(testStringForMine);
        pi6.setType(String.class);
        request.addProperty(pi6);

        PropertyInfo pi7 = new PropertyInfo();
        pi7.setName("pItemCode");
        pi7.setValue(testStringForMine);
        pi7.setType(String.class);
        request.addProperty(pi7);

        PropertyInfo pi8 = new PropertyInfo();
        pi8.setName("pNotes");
        pi8.setValue(testStringForMine);
        pi8.setType(String.class);
        request.addProperty(pi8);

        PropertyInfo pi9 = new PropertyInfo();
        pi9.setName("pEmpl1");
        pi9.setValue(testStringForMine);
        pi9.setType(String.class);
        request.addProperty(pi9);

        PropertyInfo pi10 = new PropertyInfo();
        pi10.setName("pEmpl2");
        pi10.setValue(testStringForMine);
        pi10.setType(String.class);
        request.addProperty(pi10);

        PropertyInfo pi11 = new PropertyInfo();
        pi11.setName("pConfStatus");
        pi11.setValue(testStringForMine);
        pi11.setType(String.class);
        request.addProperty(pi11);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        String response = null;

        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_URL);
            httpTransportSE.debug = true;

            Log.e("REQUEST=", "" + request);
            Log.e("SOAP ENVOLP=", "envolpe=" + envelope);

            httpTransportSE.call(SOAP_ACTION, envelope);

            response = httpTransportSE.requestDump;
        } catch (Exception e) {
            Log.i("Errorcalling hello-->", e.toString());
        }
        return response;
    }

}