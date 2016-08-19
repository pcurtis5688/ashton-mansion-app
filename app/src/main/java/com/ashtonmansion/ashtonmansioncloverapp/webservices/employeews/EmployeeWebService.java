package com.ashtonmansion.ashtonmansioncloverapp.webservices.employeews;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;
import com.clover.sdk.v3.employees.Employee;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by paul on 8/11/2016.
 */
public class EmployeeWebService {
    // STATIC SERVICE VARS ///////////////////////
    private static final String EMPL_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/EmployeeWebService";
    private static final String CREATE_EMPL_METHOD = "CreateEmployee";
    private static final String EMPL_WS_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/EmployeeWebService:CreateEmployee";
    private static final String EMPL_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/EmployeeWebService";

    public boolean createEmployeeServiceCall(Employee employee) {
        boolean createEmployeeInDynamicsSuccess = false;
        SoapObject request = new SoapObject(EMPL_WS_NAMESPACE, CREATE_EMPL_METHOD);
        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
        /////////////
        ///PARAMS////

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("empl_ID");
        pi1.setValue(employee.getId());
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("empl_name");
        pi2.setValue(employee.getName());
        pi2.setType(String.class);
        request.addProperty(pi2);

        PropertyInfo pi3 = new PropertyInfo();
        pi3.setName("empl_nickname");
        pi3.setValue(employee.getNickname());
        pi3.setType(String.class);
        request.addProperty(pi3);

        PropertyInfo pi4 = new PropertyInfo();
        pi4.setName("empl_role");
        pi4.setValue(employee.getRole().toString());
        pi4.setType(String.class);
        request.addProperty(pi4);

        PropertyInfo pi5 = new PropertyInfo();
        pi5.setName("empl_PIN");
        pi5.setValue(employee.getPin());
        pi5.setType(String.class);
        request.addProperty(pi5);

        PropertyInfo pi6 = new PropertyInfo();
        pi6.setName("empl_email");
        pi6.setValue(employee.getEmail());
        pi6.setType(String.class);
        request.addProperty(pi6);

        ////////////MAKE THE CALL
        NTLMTransport transport = new NTLMTransport();
        transport.setCredentials(EMPL_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
        try {
            transport.call(EMPL_WS_SOAP_ACTION, envelope);
            createEmployeeInDynamicsSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        }
        return createEmployeeInDynamicsSuccess;
    }
}