package com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by paul on 8/8/2016.
 */
public class WebServiceUtilities {

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
}


class ServiceAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication("laptop-53b1c7v6\\paul", new char[]{'W', 'm', 'o', '6', '7', '7', '6', '6', '7', '6', '7'}));
    }
}
