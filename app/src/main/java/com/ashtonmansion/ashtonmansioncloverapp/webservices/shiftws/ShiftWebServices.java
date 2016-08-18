package com.ashtonmansion.ashtonmansioncloverapp.webservices.shiftws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftTemplate;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by paul on 8/11/2016.
 */
public class ShiftWebServices {
    // STATIC SERVICE VARS ///////////////////////
    private static final String SHIFT_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/ShiftWebService";
    private static final String CREATE_SHIFT_METHOD = "CreateShiftTemplate";
    private static final String SHIFT_WS_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/ShiftWebService:CreateShiftTemplate";
    private static final String SHIFT_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/ShiftWebService";

    public boolean createShiftTemplateViaWS(ShiftTemplate shiftTemplate) {
        boolean shiftTemplateWSCreationSuccess = false;
        SoapObject request = new SoapObject(SHIFT_WS_NAMESPACE, CREATE_SHIFT_METHOD);
        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
        /////////////
        ///PARAMS////

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("Shift_Template_ID");
        pi1.setValue(shiftTemplate.getShiftID());
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("Shift_Template_Code");
        pi2.setValue(shiftTemplate.getShiftCode());
        pi2.setType(String.class);
        request.addProperty(pi2);

        PropertyInfo pi3 = new PropertyInfo();
        pi3.setName("Shift_Template_Name");
        pi3.setValue(shiftTemplate.getShiftName());
        pi3.setType(String.class);
        request.addProperty(pi3);

        ////////////MAKE THE CALL
        NTLMTransport transport = new NTLMTransport();
        transport.setCredentials(SHIFT_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
        try {
            transport.call(SHIFT_WS_SOAP_ACTION, envelope);
            shiftTemplateWSCreationSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        } catch (Exception e3) {
            Log.e("Generic Excp: ", e3.getMessage());
        }
        //TODO HERE THE WEB SERVICE SENDS BACK IF SUCCESSFUL OR NOT, AND I EVALUATE
        String requestDump = "" + transport.requestDump;
        Log.i("Req: ", "" + requestDump);
        return shiftTemplateWSCreationSuccess;
    }
}