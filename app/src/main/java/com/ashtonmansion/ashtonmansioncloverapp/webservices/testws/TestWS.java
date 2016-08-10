package com.ashtonmansion.ashtonmansioncloverapp.webservices.testws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.ServiceAuthenticator;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import jcifs.*;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.Authenticator;

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

    public boolean testAddApptService(Appointment appt) {
        final String wsdlUrl = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%252C%20Inc./Codeunit/AppointmentWS";
        final String namespace = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS";
        final String soap_action = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS:CreateAppointment";
        final String method_name = "CreateAppointment";
        boolean insertSuccess = false;
        String response = "";

        SoapObject request = new SoapObject(namespace, method_name);

        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

        String testString = "eeeek";
        ///PARAMS
        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("pID");
        pi1.setValue("10");
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("pDate");
        pi2.setValue(testString);
        pi2.setType(String.class);
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
        pi5.setValue(testString);
        pi5.setType(String.class);
        request.addProperty(pi5);

        PropertyInfo pi6 = new PropertyInfo();
        pi6.setName("palerttype");
        pi6.setValue(testString);
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
        pi11.setValue(testString);
        pi11.setType(String.class);
        request.addProperty(pi11);


        HttpTransportSE transportSE = WebServiceUtilities.getHttpTransportSE(wsdlUrl);
        try {
            transportSE.call(soap_action, envelope);
            insertSuccess = true;
        } catch (IOException e1) {
            Log.e("IO err:", e1.toString());
            response = transportSE.responseDump;
            Log.e("Response: ", "" + response);
        } catch (XmlPullParserException e2) {
            Log.e("XML err: ", e2.toString());
            response = transportSE.responseDump;
            Log.e("Response: ", "" + response);
        }

        return insertSuccess;
    }

    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    public boolean testAddApptService2(Appointment appt) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                Log.e("Req scheme: ", getRequestingScheme());
                // Remember to include the NT domain in the username
                String userdomain = WebServiceUtilities.getUserDomain();
                String username = WebServiceUtilities.getUserName();
                String userpass = WebServiceUtilities.getPass();

                return new PasswordAuthentication(userdomain + "\\" +
                        username, userpass.toCharArray());
            }
        });

        ///////////////////
        final String wsdlUrl = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%252C%20Inc./Codeunit/AppointmentWS";
        final String namespace = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS";
        final String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS:CreateAppointment";
        final String method_name = "CreateAppointment";
        boolean insertSuccess = false;
        URL urlRequest = null;
        String xmlResponse;

        ////////////////
        try {
            urlRequest = new URL(wsdlUrl);
            HttpURLConnection conn = (HttpURLConnection) urlRequest.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            ///////////////////
            SoapObject request = new SoapObject(namespace, method_name);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

            ///PARAMS
            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("pID");
            pi1.setValue(appt.get_id());
            pi1.setType(String.class);
            request.addProperty(pi1);

            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("pDate");
            pi2.setValue(appt.get_date());
            pi2.setType(String.class);
            request.addProperty(pi2);

            PropertyInfo pi3 = new PropertyInfo();
            pi3.setName("pTime");
            pi3.setValue(appt.get_start_time());
            pi3.setType(String.class);
            request.addProperty(pi3);

            PropertyInfo pi4 = new PropertyInfo();
            pi4.setName("pCustCode");
            pi4.setValue(appt.get_customer_code());
            pi4.setType(String.class);
            request.addProperty(pi4);

            PropertyInfo pi5 = new PropertyInfo();
            pi5.setName("pDuration");
            pi5.setValue(appt.get_duration());
            pi5.setType(String.class);
            request.addProperty(pi5);

            PropertyInfo pi6 = new PropertyInfo();
            pi6.setName("palerttype");
            pi6.setValue(appt.get_alert_type());
            pi6.setType(String.class);
            request.addProperty(pi6);

            PropertyInfo pi7 = new PropertyInfo();
            pi7.setName("pItemCode");
            pi7.setValue(appt.get_item_code());
            pi7.setType(String.class);
            request.addProperty(pi7);

            PropertyInfo pi8 = new PropertyInfo();
            pi8.setName("pNotes");
            pi8.setValue(appt.get_note());
            pi8.setType(String.class);
            request.addProperty(pi8);

            PropertyInfo pi9 = new PropertyInfo();
            pi9.setName("pEmpl1");
            pi9.setValue(appt.get_employee_code_1());
            pi9.setType(String.class);
            request.addProperty(pi9);

            PropertyInfo pi10 = new PropertyInfo();
            pi10.setName("pEmpl2");
            pi10.setValue(appt.get_employee_code_2());
            pi10.setType(String.class);
            request.addProperty(pi10);

            PropertyInfo pi11 = new PropertyInfo();
            pi11.setName("pConfStatus");
            pi11.setValue(appt.get_confirm_status());
            pi11.setType(String.class);
            request.addProperty(pi11);

            HttpTransportSE transportSE = WebServiceUtilities.getHttpTransportSE(wsdlUrl);

            transportSE.call(SOAP_ACTION, envelope);
            xmlResponse = transportSE.responseDump.toString();

            Log.i("XML Response: ", xmlResponse);

            insertSuccess = true;
        } catch (MalformedURLException e1) {
            Log.e("Malformed: ", urlRequest.toString());
            Log.e("Exception: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IO Err: ", "" + e2.getMessage());
        } catch (XmlPullParserException e3){
            Log.e("XMLPull Excpt: ", e3.getMessage());
        }

        return insertSuccess;
    }

    public String ntlmTest() {
//        String userAuthenticated = "false";
//        String domainController = "subdomain.domain.com";
//        UniAddress dc = UniAddress.getByName(domainController, true);
//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("Domain", "user", "password");
//        SmbSession.logon(dc, auth);
//        userAuthenticated = "true";
        return "boo";
    }

}