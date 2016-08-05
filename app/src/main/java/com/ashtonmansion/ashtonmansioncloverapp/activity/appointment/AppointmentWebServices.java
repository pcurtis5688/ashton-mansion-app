package com.ashtonmansion.ashtonmansioncloverapp.activity.appointment;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.utility.jax_web_services.AppointmentWS;
import com.ashtonmansion.ashtonmansioncloverapp.utility.jax_web_services.AppointmentWSPort;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

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
            final URL WEB_SERVICE_URL = new URL(baseURL + "Codeunit/AppointmentWS/");

            QName systemServiceQName = new QName("urn:microsoft-dynamics-schemas/DynamicsNAV90/WS/", "AppointmentWS");

            AppointmentWS appointmentWS = new AppointmentWS(WEB_SERVICE_URL, systemServiceQName);
            AppointmentWSPort appointmentWSPort = new AppointmentWSPort() {
                @Override
                public boolean createAppointment(int iD, String date, String time, String customerCode, String duration, String alertType, String itemCode, String notes, String employee1, String employee2, String confirmationStatus) {
                    return false;
                }
            };
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
        Authenticator.setDefault(new ServiceAuthenticator());
    }


}

class ServiceAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication("laptop-53b1c7v6\\paul", new char[]{'W', 'm', 'o', '6', '7', '7', '6', '6', '7', '6', '7'}));
    }
}

