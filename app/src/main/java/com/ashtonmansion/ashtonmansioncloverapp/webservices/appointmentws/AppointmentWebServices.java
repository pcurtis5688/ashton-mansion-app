package com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by paul on 8/1/2016.
 */
public class AppointmentWebServices {
    // STATIC SERVICE VARS
    private static final String APPT_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWebService";
    private static final String APPT_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/AppointmentWebService";

    public boolean addAppointmentViaWS(Appointment appt) {
        final String CREATE_APPT_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWebService:CreateAppointment";
        final String CREATE_APPT_METHOD_NAME = "CreateAppointment";
        boolean apptSuccessfullyAddedInDynamics = false;
        try {
            SoapObject request = new SoapObject(APPT_WS_NAMESPACE, CREATE_APPT_METHOD_NAME);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
            /////////////
            ///PARAMS////
            //TODO THINK IN MORE DETAIL IF WE NEED TO GENERATE ID OR IF SQL DOES
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
            transport.call(CREATE_APPT_SOAP_ACTION, envelope);
            apptSuccessfullyAddedInDynamics = true;
        } catch (IOException e1) {
            Log.e("IO Err: ", "" + "" + e1.getMessage());
        } catch (XmlPullParserException e2) {
            Log.e("XMLPull Excpt: ", "" + e2.getMessage());
        } catch (Exception e) {
            Log.e("Exception in: ", "" + e.getMessage());
        }
        return apptSuccessfullyAddedInDynamics;
    }

    public boolean deleteAppointmentByIDWS(String appointmentID) {
        final String DELETE_APPT_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/AppointmentWebService:DeleteAppointment";
        final String DELETE_APPT_METHOD_NAME = "DeleteAppointment";
        boolean apptSuccessfullyDeleted = false;
        try {
            SoapObject request = new SoapObject(APPT_WS_NAMESPACE, DELETE_APPT_METHOD_NAME);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
            /////////////
            ///PARAMS////
            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("appointment_ID");
            pi1.setValue(appointmentID);
            pi1.setType(String.class);
            request.addProperty(pi1);
            ////////////MAKE THE CALL
            NTLMTransport transport = new NTLMTransport();
            transport.setCredentials(APPT_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
            transport.call(DELETE_APPT_SOAP_ACTION, envelope);
            apptSuccessfullyDeleted = true;
        } catch (XmlPullParserException e1) {
            Log.e("XML Excpt: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IO Excpt: ", e2.getMessage());
        } catch (Exception e3) {
            Log.e("Generic Excpt: ", e3.getMessage());
        }
        return apptSuccessfullyDeleted;
    }
}

