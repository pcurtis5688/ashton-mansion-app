package com.ashtonmansion.ashtonmansioncloverapp.webservices.testws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.Authenticator;

/**
 * Created by paul on 7/31/2016.
 */
public class TestWS {
    //    private static final String BASE_URL = "http://10.0.3.2:7047/DynamicsNAV/WS/";
    // private static final String APPOINTMENT_WS_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS Canada, Inc./Codeunit/AppointmentWS";
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    private static final String TEST_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/TestWebService";
    private static final String TEST_WS_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/TestWebService:returnHello";
    private static final String TEST_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/TestWebService";
    private static final String RETURN_HELLO_METHOD = "returnHello";

    public String ntlmTest() {
        String great;
        try {
            SoapObject request = new SoapObject(TEST_WS_NAMESPACE, RETURN_HELLO_METHOD);
            //todo use my generalized utillities
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

            NTLMTransport transport = new NTLMTransport();
            transport.setCredentials(TEST_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
            transport.call(TEST_WS_SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            great = result.getInnerText().toString();
            Log.i("result: ", result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            great = e.toString();
            System.out.println(great);
        }

        return great;
    }

    ///////////////////
    private static final String APPT_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWebService";
    private static final String APPT_WS_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWebService:returnHello";
    private static final String APPT_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/AppointmentWebService";
    private static final String CREATE_APPT_METHOD = "CreateAppointment";

    public String addAppointmentViaWS(Appointment appt) {
        String response = "";
        try {
            SoapObject request = new SoapObject(APPT_WS_NAMESPACE, CREATE_APPT_METHOD);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
            /////////////
            ///PARAMS////
            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("iD");
            pi1.setValue(appt.get_id());
            pi1.setType(String.class);
            request.addProperty(pi1);

            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("date");
            pi2.setValue(appt.get_date());
            pi2.setType(String.class);
            request.addProperty(pi2);

            PropertyInfo pi3 = new PropertyInfo();
            pi3.setName("time");
            pi3.setValue(appt.get_start_time());
            pi3.setType(String.class);
            request.addProperty(pi3);

            PropertyInfo pi4 = new PropertyInfo();
            pi4.setName("customer_Code");
            pi4.setValue(appt.get_customer_code());
            pi4.setType(String.class);
            request.addProperty(pi4);

            PropertyInfo pi5 = new PropertyInfo();
            pi5.setName("duration");
            pi5.setValue(appt.get_duration());
            pi5.setType(String.class);
            request.addProperty(pi5);

            PropertyInfo pi6 = new PropertyInfo();
            pi6.setName("alert_Type");
            pi6.setValue(appt.get_alert_type());
            pi6.setType(String.class);
            request.addProperty(pi6);

            PropertyInfo pi7 = new PropertyInfo();
            pi7.setName("item_Code");
            pi7.setValue(appt.get_item_code());
            pi7.setType(String.class);
            request.addProperty(pi7);

            PropertyInfo pi8 = new PropertyInfo();
            pi8.setName("notes");
            pi8.setValue(appt.get_note());
            pi8.setType(String.class);
            request.addProperty(pi8);

            PropertyInfo pi9 = new PropertyInfo();
            pi9.setName("employee_1");
            pi9.setValue(appt.get_employee_code_1());
            pi9.setType(String.class);
            request.addProperty(pi9);

            PropertyInfo pi10 = new PropertyInfo();
            pi10.setName("employee_2");
            pi10.setValue(appt.get_employee_code_2());
            pi10.setType(String.class);
            request.addProperty(pi10);

            PropertyInfo pi11 = new PropertyInfo();
            pi11.setName("confirmation_Status");
            pi11.setValue(appt.get_confirm_status());
            pi11.setType(String.class);
            request.addProperty(pi11);
            ////////////MAKE THE CALL
            NTLMTransport transport = new NTLMTransport();
            transport.setCredentials(APPT_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
            transport.call(APPT_WS_SOAP_ACTION, envelope);
            response = transport.requestDump;
            Log.i("Result: ", "" + response);
        } catch (IOException e1) {
            Log.e("IO Err: ", "" + "" + e1.getMessage());
        } catch (XmlPullParserException e2) {
            Log.e("XMLPull Excpt: ", "" + e2.getMessage());
        } catch (Exception e){
            Log.e("Exception in: ", "" + e.getMessage());
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
        String response = "";

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