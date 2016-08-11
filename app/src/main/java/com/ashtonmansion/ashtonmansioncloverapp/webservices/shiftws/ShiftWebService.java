package com.ashtonmansion.ashtonmansioncloverapp.webservices.shiftws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;
import com.clover.sdk.v3.employees.Shift;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by paul on 8/11/2016.
 */
public class ShiftWebService {
    // STATIC SERVICE VARS ///////////////////////
    private static final String SHIFT_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/ShiftWebService";
    private static final String CREATE_SHIFT_METHOD = "CreateShift";
    private static final String SHIFT_WS_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/ShiftWebService:CreateShift";
    private static final String SHIFT_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/ShiftWebService";

    public boolean createShift(Shift shift) {
        boolean resultBool = true;
        try {
            ///REQUEST OBJECTS///////
            SoapObject request = new SoapObject(SHIFT_WS_NAMESPACE, CREATE_SHIFT_METHOD);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

            ///PARAMS////////////////
            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("iD");
            pi1.setValue(shift.getId());
            pi1.setType(String.class);
            request.addProperty(pi1);

            ////////////MAKE THE CALL/////////////
            //TODO HERE THE WEB SERVICE SENDS BACK IF SUCCESSFUL OR NOT, AND I EVALUATE
            //TODO ALSO SEE IF THERE ARE ANY NON-DEPRACATED CLASSES I CAN UTILIZE
            NTLMTransport transport = new NTLMTransport();
            transport.setCredentials(SHIFT_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
            transport.call(SHIFT_WS_SOAP_ACTION, envelope);
            String requestDump = transport.requestDump;
            Log.i("Result: ", "" + requestDump);
        } catch (IOException e1) {
            Log.e("IO Err: ", "" + "" + e1.getMessage());
            resultBool = false;
        } catch (XmlPullParserException e2) {
            Log.e("XMLPull Excpt: ", "" + e2.getMessage());
            resultBool = false;
        } catch (Exception e) {
            Log.e("Exception in: ", "" + e.getMessage());
            resultBool = false;
        }

        return resultBool;
    }
    //TODO NEXT METHOD WOULD BE SHIFT EXCEPTION HANDLING
}