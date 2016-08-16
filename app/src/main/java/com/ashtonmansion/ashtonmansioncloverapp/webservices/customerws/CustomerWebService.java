package com.ashtonmansion.ashtonmansioncloverapp.webservices.customerws;

import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.WebServiceUtilities;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws.external.NTLMTransport;
import com.clover.sdk.v3.customers.Customer;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by paul on 8/11/2016.
 */
public class CustomerWebService {
    // STATIC SERVICE VARS
    ///////////////////////
    private static final String CUST_WS_NAMESPACE = "urn:microsoft-dynamics-schemas/codeunit/CustomerWebService";
    private static final String CREATE_CUST_METHOD = "InsertCustomer";
    private static final String CUST_WS_SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/CustomerWebService:InsertCustomer";
    private static final String CUST_WEB_SERVICE_URL = "http://10.0.3.2:7047/DynamicsNAV90/WS/CRONUS%20Canada,%20Inc./Codeunit/CustomerWebService";

    public boolean createCustomerServiceCall(Customer customer) {
        boolean resultBool = true;
        try {
            SoapObject request = new SoapObject(CUST_WS_NAMESPACE, CREATE_CUST_METHOD);
            SoapSerializationEnvelope envelope = WebServiceUtilities.getSoapSerializationEnvelope(request);
            /////////////
            ///PARAMS////
            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("iD");
            pi1.setValue(customer.getId());
            pi1.setType(String.class);
            request.addProperty(pi1);

            ////////////MAKE THE CALL
            NTLMTransport transport = new NTLMTransport();
            transport.setCredentials(CUST_WEB_SERVICE_URL, "paul", "Wmo67766767", "laptop-53b1c7v6", "");
            transport.call(CUST_WS_SOAP_ACTION, envelope);
            //TODO HERE THE WEB SERVICE SENDS BACK IF SUCCESSFUL OR NOT, AND I EVALUATE
            String requestDump = transport.requestDump;
            Log.i("Result: ", "" + requestDump);
        } catch (IOException e1) {
            Log.e("IO Err: ", "" + "" + e1.getMessage());
            resultBool = false;
        } catch (XmlPullParserException e2) {
            Log.e("XMLPull Excpt: ", "" + e2.getMessage());
            resultBool = false;
        } catch (Exception e){
            Log.e("Exception in: ", "" + e.getMessage());
            resultBool = false;
        }

        return resultBool;
    }
}
