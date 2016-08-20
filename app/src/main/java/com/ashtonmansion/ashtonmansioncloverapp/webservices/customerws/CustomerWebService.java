package com.ashtonmansion.ashtonmansioncloverapp.webservices.customerws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;
import com.clover.sdk.v3.customers.Customer;
import com.clover.sdk.v3.customers.PhoneNumber;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by paul on 8/11/2016.
 */
public class CustomerWebService {
    // STATIC SERVICE VARS
    private static final String CUST_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/CustomerWebService";
    private static final String CUST_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/CustomerWebService";

    public boolean createCustomerServiceCall(Customer customer) {
        final String CREATE_CUST_METHOD = "CreateCustomer";
        final String CREATE_CUST_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/CustomerWebService:CreateCustomer";

        boolean createCustomerInDynamicsSuccess = false;
        SoapObject request = new SoapObject(CUST_WS_NAMESPACE, CREATE_CUST_METHOD);
        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
        ////////HANDLE LISTS//////////////////
        insertPhoneNumbers(customer);

        ////////////////////////
        //TODO HANDLE THESE TYPES OF FIELDS AND DECIDE HOW TO STORE
        String test3 = customer.getEmailAddresses().toString();
        String test4 = customer.getAddresses().toString();
        Log.i("Email String: ", test3);
        Log.i("Address String: ", test4);

        /////////////
        ///PARAMS////
        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("cust_ID");
        pi1.setValue(customer.getId());
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("cust_first_name");
        pi2.setValue(customer.getFirstName());
        pi2.setType(String.class);
        request.addProperty(pi2);

        PropertyInfo pi3 = new PropertyInfo();
        pi3.setName("cust_last_name");
        pi3.setValue(customer.getLastName());
        pi3.setType(String.class);
        request.addProperty(pi3);

        PropertyInfo pi4 = new PropertyInfo();
        pi4.setName("cust_marketing_allowed");
        pi4.setValue(customer.getMarketingAllowed().toString());
        pi4.setType(String.class);
        request.addProperty(pi4);

        PropertyInfo pi5 = new PropertyInfo();
        pi5.setName("cust_phone_numbers");
        pi5.setValue(customer.getPhoneNumbers().toString());
        pi5.setType(String.class);
        request.addProperty(pi5);

        PropertyInfo pi6 = new PropertyInfo();
        pi6.setName("cust_email_addresses");
        pi6.setValue(customer.getEmailAddresses().toString());
        pi6.setType(String.class);
        request.addProperty(pi6);

        PropertyInfo pi7 = new PropertyInfo();
        pi7.setName("cust_addresses");
        pi7.setValue(customer.getAddresses().toString());
        pi7.setType(String.class);
        request.addProperty(pi7);

        ////////////MAKE THE CALL
        NTLMTransport transport = new NTLMTransport();
        transport.setCredentials(CUST_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
        try {
            transport.call(CREATE_CUST_SOAP_ACTION, envelope);
            createCustomerInDynamicsSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        }
        return createCustomerInDynamicsSuccess;
    }

    public boolean insertPhoneNumbers(Customer customer) {
        //SERVICE INFO //todo maybe one call in future.
        final String INSERT_PHONE_NUMBER_METHOD = "InsertNumber";
        final String INSERT_PHONE_NUMBER_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/CustomerWebService:InsertNumber";
        boolean phoneNumberListSuccess = true;
        //CUSTOMER DATA
        String customerID = customer.getId();
        List<PhoneNumber> phoneNumberList = customer.getPhoneNumbers();

        for (PhoneNumber number : phoneNumberList) {
            //CREATE REQ AND ENVELOPE
            SoapObject request = new SoapObject(CUST_WS_NAMESPACE, INSERT_PHONE_NUMBER_METHOD);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
            //PARAMS
            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("customer_ID");
            pi1.setValue(customerID);
            pi1.setType(String.class);
            request.addProperty(pi1);

            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("phone_number");
            pi2.setValue(number.toString());
            pi2.setType(String.class);
            request.addProperty(pi2);

            ////////////MAKE THE CALL
            NTLMTransport transport = new NTLMTransport();
            transport.setCredentials(CUST_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
            try {
                transport.call(INSERT_PHONE_NUMBER_SOAP_ACTION, envelope);
            } catch (XmlPullParserException e1) {
                Log.e("XMLPPException: ", e1.getMessage());
                phoneNumberListSuccess = false;

            } catch (IOException e2) {
                Log.e("IOException: ", e2.getMessage());
                phoneNumberListSuccess = false;

            } catch (Exception e3) {
                Log.e("IOException: ", e3.getMessage());
                phoneNumberListSuccess = false;
            }
        }
        return phoneNumberListSuccess;
    }

    public boolean deleteCustomerServiceCall(String customerID) {
        final String DELETE_CUST_METHOD = "DeleteCustomer";
        final String DELETE_CUST_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/CustomerWebService:DeleteCustomer";

        boolean deleteCustomerInDynamicsSuccess = false;
        SoapObject request = new SoapObject(CUST_WS_NAMESPACE, DELETE_CUST_METHOD);
        SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);

        ///PARAMS////
        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("customer_ID");
        pi1.setValue(customerID);
        pi1.setType(String.class);
        request.addProperty(pi1);

        ////////////MAKE THE CALL
        NTLMTransport transport = new NTLMTransport();
        transport.setCredentials(CUST_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
        try {
            transport.call(DELETE_CUST_SOAP_ACTION, envelope);
            deleteCustomerInDynamicsSuccess = true;
        } catch (XmlPullParserException e1) {
            Log.e("XMLPPException: ", e1.getMessage());
        } catch (IOException e2) {
            Log.e("IOException: ", e2.getMessage());
        } catch (Exception e3) {
            Log.e("GenericExcpt: ", e3.getMessage());
        }
        return deleteCustomerInDynamicsSuccess;
    }
}

