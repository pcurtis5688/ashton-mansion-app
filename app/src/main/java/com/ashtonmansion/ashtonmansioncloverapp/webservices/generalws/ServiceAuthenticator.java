package com.ashtonmansion.ashtonmansioncloverapp.webservices.generalws;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by paul on 8/1/2016.
 */
public class ServiceAuthenticator extends Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        return (new PasswordAuthentication("paul", new char[]{'W', 'm', 'o', '6', '7', '7', '6', '6', '7', '6', '7'}));
    }
}
