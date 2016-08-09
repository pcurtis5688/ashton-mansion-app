package com.ashtonmansion.ashtonmansioncloverapp.webservices.testws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.xml.namespace.QName;

/**
 * Created by paul on 7/31/2016.
 */
public class TestWS {
    private static final String TEST_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/TestService";
    private static final String BASE_URL = "http://10.0.3.2:7047/DynamicsNAV/WS/";

    public static String GetHello(String helloIn) {
        String SAY_HELLO_SOAP_METHOD = "ReturnHello";
        String test_webservice_namespace = "urn:microsoft-dynamics-schemas/codeunit/TestService";
        QName WEB_SERVICE_QNAME = new QName("urn:microsoft-dynamics-schemas/DynamicsNAV90/WS/", "Codeunit/TestWS");

        String COMPANY_WEB_SERVICE_URL = "http://10.0.3.2:x7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/TestWS";

        String CREATE_APPT_ACTION = WEB_SERVICE_QNAME + SAY_HELLO_SOAP_METHOD;


        SoapObject request = new SoapObject(test_webservice_namespace, SAY_HELLO_SOAP_METHOD);

        PropertyInfo custProp = new PropertyInfo();
        custProp.setName("helloIn");
        custProp.setValue(helloIn);
        custProp.setType(String.class);
        request.addProperty(custProp);

        HttpTransportSE transportSE = WebServiceUtilities.getHttpTransportSE(TEST_WEB_SERVICE_URL);

        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

        Log.e("REQUEST=", "" + request);
        Log.e("SOAP ENVOLP=", "envolpe=" + envelope);

        String response = "";
        try {
            transportSE.call(CREATE_APPT_ACTION, envelope);
            response = transportSE.responseDump;
        } catch (XmlPullParserException | IOException e) {
            Log.e("XMLPullParser Err: ", e.toString());
        }
        return response;
    }


    public static String GetISD(String countryname) {
        String WEB_SERVICE_URL = "http://www.webservicex.net/country.asmx";
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
            HttpTransportSE httpTransportSE = new HttpTransportSE(WEB_SERVICE_URL);
            httpTransportSE.debug = true;

            Log.e("REQUEST: ", request.toString());
            Log.e("ENVELOPE: ", envelope.toString());


            httpTransportSE.call(SOAP_ACTION, envelope);

            response = httpTransportSE.responseDump;

        } catch (Exception e) {
            Log.i("Call exception: ", e.toString());
        }
        return response;
    }
}