package com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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

    public static boolean insertAppointment(Appointment appt){
        final String wsdlUrl = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS Canada, Inc./Codeunit/AppointmentWS";
        final String namespace = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS";
        final String soap_action = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWS:CreateAppointment";
        final String method_name = "CreateAppointment";
        boolean insertSuccess = false;

        SoapObject request = new SoapObject(namespace, method_name);

        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

        ///PARAMS
        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("pID");
        pi1.setValue("10");
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
        try {
            transportSE.call(soap_action, envelope);
            insertSuccess = true;
        } catch (IOException | XmlPullParserException e){
            Log.e("IO or XML err: ", e.toString());
        }


        return insertSuccess;
    }
}

