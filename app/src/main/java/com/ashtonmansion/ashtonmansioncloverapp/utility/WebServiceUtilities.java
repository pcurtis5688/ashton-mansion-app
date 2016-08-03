package com.ashtonmansion.ashtonmansioncloverapp.utility;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by paul on 7/26/2016.
 */
public class WebServiceUtilities {

    public static SoapSerializationEnvelope getSoapEnvelope(SoapObject request) {
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.implicitTypes = true;
        soapSerializationEnvelope.setAddAdornments(false);
        soapSerializationEnvelope.setOutputSoapObject(request);

        return soapSerializationEnvelope;
    }

    public static HttpTransportSE getHttpTransportSE(String SOAP_URL) {
        HttpTransportSE ht = new HttpTransportSE(SOAP_URL);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

//    public String connectToNtlm(String helloIn) {
//        String helloBack = "";
//        try {
//            String baseURL = "http://10.0.3.2:7047/DynamicsNAV/WS/";
//            URL systemServiceURL = new URL(baseURL + "Codeunit/TestService");
//            QName systemServiceQName = new QName("urn:microsoft-dynamics-schemas/nav/system/", "Codeunit/TestService");
//
//
//            WSAuthenticator myAuthenticator = new WSAuthenticator();
//            myAuthenticator.getPasswordAuthentication();
//
//
//            AppointmentWS apptWs = new AppointmentWS(systemServiceURL, systemServiceQName);
//
//
//        } catch (Exception e) {
//            Log.i("Exception: ", e.toString());
//        }
//        return helloBack;
//    }
}
