package com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;

/**
 * Created by paul on 8/8/2016.
 */
public class WebServiceUtilities {
    private static final String USER_DOMAIN = "laptop-53b1c7v6";
    private static final String USER_NAME = "paul";
    private static final String USER_PW = "Wmo67766767";

    public static final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    public static final ServiceAuthenticator getSoapAuthenticator() {
        ServiceAuthenticator soapAuthenticator = new ServiceAuthenticator();
        return soapAuthenticator;
    }

    public static final HttpTransportSE getHttpTransportSE(String REQUEST_URL) {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, REQUEST_URL, 60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

    public static final String getUserDomain() {
        return USER_DOMAIN;
    }

    public static final String getUserName() {
        return USER_NAME;
    }

    public static final String getPass() {
        return USER_PW;
    }


}


