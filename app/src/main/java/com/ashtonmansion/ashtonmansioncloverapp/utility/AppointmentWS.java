package com.ashtonmansion.ashtonmansioncloverapp.utility;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.xml.namespace.QName;

import okhttp3.Response;


/**
 * Created by paul on 8/1/2016.
 */
public class AppointmentWS {
    // Appointment vars

    // NETWORK VARS
    private static final String baseURL = "http://10.0.3.2:7047/DynamicsNAV/WS/";
    private static QName SYSTEM_SERVICE_QNAME = new QName("urn:microsoft-dynamics-schemas/DynamicsNAV90/WS/", "Codeunit/TestService");
    private static String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/ApptCU:CreateAppointment";
    private static String APPT_WS_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/AppointmentWS";
    private static String SOAP_METHOD = "CreateAppointment";
    private static String SOAP_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS";


    public AppointmentWS() {
        Authenticator.setDefault(new ServiceAuthenticator());
    }

    public String createAppointment(Appointment appointment) {
        SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);
        String response = null;

        PropertyInfo piAppt = new PropertyInfo();
        piAppt.setName("Appointment");
        piAppt.setValue(appointment);
        piAppt.setType(Appointment.class);
        request.addProperty(piAppt);

        ///Done setting props.
        //Make request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(APPT_WS_URL);
        httpTransportSE.debug = true;

        try {
            Log.i("Request: ", httpTransportSE.requestDump);

            httpTransportSE.call(SOAP_ACTION, envelope);

            Log.i("Response: ", httpTransportSE.responseDump);

            response = httpTransportSE.responseDump;
        } catch (Exception e) {
            Log.i("Call exception: ", e.toString());
            Log.e("REQUEST=", "" + request);
            Log.e("SOAP ENVOLP=", "envolpe=" + envelope);
        }
        return response;
    }
}

class ServiceAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication("laptop-53b1c7v6\\paul", new char[]{'W', 'm', 'o', '6', '7', '7', '6', '6', '7', '6', '7'}));
    }
}