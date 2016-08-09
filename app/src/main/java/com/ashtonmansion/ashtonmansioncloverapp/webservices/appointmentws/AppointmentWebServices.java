package com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Authenticator;

import javax.xml.namespace.QName;


/**
 * Created by paul on 8/1/2016.
 */
public class AppointmentWebServices {
    // Appointment vars
    private static final String CREATE_APPT_SOAP_METHOD = "CreateAppointment";
    private static final QName WEB_SERVICE_QNAME = new QName("urn:microsoft-dynamics-schemas/DynamicsNAV90/WS/", "Codeunit/AppointmentWS");
    private static final String baseURL = "http://10.0.3.2:7047/DynamicsNAV/WS/";
    private static final String COMPANY_WEB_SERVICE_URL = "http://10.0.3.2:x7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/AppointmentWS";

    private static final String CREATE_APPT_ACTION = WEB_SERVICE_QNAME + CREATE_APPT_SOAP_METHOD;

    public boolean createAppointment(Appointment appointment) {
        boolean creationSuccess = false;

        try {
//            final URL WEB_SERVICE_URL = new URL(baseURL + "Codeunit/AppointmentWS/");
//
//            QName systemServiceQName = new QName("urn:microsoft-dynamics-schemas/DynamicsNAV90/WS/", "AppointmentWS");
//
//            AppointmentWS appointmentWS = new AppointmentWS(WEB_SERVICE_URL, systemServiceQName);
//            AppointmentWSPort appointmentWSPort = new AppointmentWSPort() {
//                @Override
//                public boolean createAppointment(int iD, String date, String time, String customerCode, String duration, String alertType, String itemCode, String notes, String employee1, String employee2, String confirmationStatus) {
//                    return false;
//                }
//            };
            // appointmentWSPort.createAppointment()

            SoapObject request = new SoapObject(COMPANY_WEB_SERVICE_URL, CREATE_APPT_SOAP_METHOD);
            String response = null;
////////////////////////////////
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

            HttpTransportSE httpTransportSE = new HttpTransportSE(COMPANY_WEB_SERVICE_URL);
            httpTransportSE.debug = true;

            Log.i("Request: ", httpTransportSE.requestDump);

            httpTransportSE.call(CREATE_APPT_ACTION, envelope);

            Log.i("Response: ", httpTransportSE.responseDump);

            response = httpTransportSE.responseDump;


            // Log.i("Call exception: ", e.toString());
            Log.e("REQUEST=", "" + request);
            Log.e("bodyout: ", envelope.bodyOut.toString());
        } catch (Exception e) {
            Log.e("Exception: ", e.toString());
        }
        return creationSuccess;
    }

    public AppointmentWebServices() {
        Authenticator.setDefault(WebServiceUtilities.getSoapAuthenticator());
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

