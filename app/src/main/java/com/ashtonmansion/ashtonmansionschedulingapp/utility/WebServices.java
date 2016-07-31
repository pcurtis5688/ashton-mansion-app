package com.ashtonmansion.ashtonmansionschedulingapp.utility;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by paul on 7/26/2016.
 */
public class WebServices {
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


}
