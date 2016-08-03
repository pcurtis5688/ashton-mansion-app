package com.ashtonmansion.ashtonmansioncloverapp.utility;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 8/1/2016.
 */
public class AppointmentWSPort {

    public List<Appointment> getAppointments(String helloIn) {
        List<Appointment> appointmentList = new ArrayList<Appointment>();


        String SOAP_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada%2C%20Inc./Codeunit/TestService";
        String SOAP_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/TestService";

        String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/wsAppt:ReturnHello";

        String SOAP_METHOD = "ReturnHello";

        SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD);

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("helloIn");
        pi1.setValue(helloIn);
        pi1.setType(String.class);
        request.addProperty(pi1);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        String response = null;

        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_URL);
            httpTransportSE.debug = true;


            Log.e("REQUEST=", "" + request);
            Log.e("SOAP ENVOLP=", "envolpe=" + envelope);

            response = httpTransportSE.requestDump;
        } catch (Exception e) {
            Log.i("Errorcalling hello-->", e.toString());
        }

//        Appointment appt = new Appointment();
//        appt.set_note(response);
//        appointmentList.add(appt);

        return appointmentList;
    }
}
