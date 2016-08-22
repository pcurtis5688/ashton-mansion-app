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
    private static final String EMPL_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/EmployeeWebService";

    public boolean createEmployeeServiceCall(Employee employee) {
        final String CREATE_EMPL_METHOD = "CreateEmployee";
        final String CREATE_EMPL_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/EmployeeWebService:CreateEmployee";

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
            transport.call(CREATE_EMPL_SOAP_ACTION, envelope);
            createEmployeeInDynamicsSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        }
        return createEmployeeInDynamicsSuccess;
    }

    public boolean deleteEmployeeServiceCall(String employeeID) {
        final String DELETE_EMPLOYEE_METHOD = "DeleteEmployee";
        final String DELETE_EMPL_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/EmployeeWebService:DeleteEmployee";

        boolean deleteEmployeeInDynamicsSuccess = false;
        SoapObject request = new SoapObject(EMPL_WS_NAMESPACE, DELETE_EMPLOYEE_METHOD);
        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
        /////////////
        ///PARAMS////

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("employee_ID");
        pi1.setValue(employeeID);
        pi1.setType(String.class);
        request.addProperty(pi1);

        ////////////MAKE THE CALL
        NTLMTransport transport = new NTLMTransport();
        transport.setCredentials(EMPL_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
        try {
            transport.call(DELETE_EMPL_SOAP_ACTION, envelope);
            deleteEmployeeInDynamicsSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        } catch (Exception e3) {
            Log.e("GenericExcpt: ", e3.getMessage());
        }
        return deleteEmployeeInDynamicsSuccess;
    }

    public boolean modifyEmployeeServiceCall(Employee modifiedEmployee) {
        final String MODIFY_EMPL_METHOD = "ModifyEmployee";
        final String MODIFY_EMPL_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/EmployeeWebService:ModifyEmployee";
        boolean modifyEmployeeInDynamicsSuccess = false;

        SoapObject request = new SoapObject(EMPL_WS_NAMESPACE, MODIFY_EMPL_METHOD);
        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

        ///PARAMS////
        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("employee_ID_m");
        pi1.setValue(modifiedEmployee.getId());
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("employee_name_m");
        pi2.setValue(modifiedEmployee.getName());
        pi2.setType(String.class);
        request.addProperty(pi2);

        PropertyInfo pi3 = new PropertyInfo();
        pi3.setName("employee_nickname_m");
        pi3.setValue(modifiedEmployee.getNickname());
        pi3.setType(String.class);
        request.addProperty(pi3);

        PropertyInfo pi4 = new PropertyInfo();
        pi4.setName("employee_role_m");
        pi4.setValue(modifiedEmployee.getRole().toString());
        pi4.setType(String.class);
        request.addProperty(pi4);

        PropertyInfo pi5 = new PropertyInfo();
        pi5.setName("employee_pin_m");
        pi5.setValue(modifiedEmployee.getPin());
        pi5.setType(String.class);
        request.addProperty(pi5);

        PropertyInfo pi6 = new PropertyInfo();
        pi6.setName("employee_email_m");
        pi6.setValue(modifiedEmployee.getEmail());
        pi6.setType(String.class);
        request.addProperty(pi6);

        ////////////MAKE THE CALL
        NTLMTransport transport = new NTLMTransport();
        transport.setCredentials(EMPL_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
        try {
            transport.call(MODIFY_EMPL_SOAP_ACTION, envelope);
            modifyEmployeeInDynamicsSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        }
        return modifyEmployeeInDynamicsSuccess;
    }
}