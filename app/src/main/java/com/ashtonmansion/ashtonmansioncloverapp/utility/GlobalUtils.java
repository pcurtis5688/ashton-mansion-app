package com.ashtonmansion.ashtonmansioncloverapp.utility;

import com.clover.sdk.v3.customers.Address;
import com.clover.sdk.v3.customers.Customer;
import com.clover.sdk.v3.customers.EmailAddress;
import com.clover.sdk.v3.customers.PhoneNumber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by paul on 8/20/2016.
 */
public class GlobalUtils {

    public static String formatDate(int month, int day, int year) {
        String formattedDate;
        DateFormat df;

        Date appt_date = new Date(year - 1900, month, day);
        df = new SimpleDateFormat("MM/dd/yyyy");
        formattedDate = df.format(appt_date);

        return formattedDate;
    }

    public static String formatTime(int hour, int minute) {
        String amOrPm;
        if (hour >= 12) {
            amOrPm = "PM";
        } else {
            amOrPm = "AM";
        }
        String formattedTime = "" + (hour - 12) + ":" + minute + " " + amOrPm;
        return formattedTime;
    }

    public static List<Customer> getV3CustomerListViaV1List(List<com.clover.sdk.v1.customer.Customer> v1customerList) {
        List<Customer> v3Customers = new ArrayList<>();
        for (com.clover.sdk.v1.customer.Customer v1Customer : v1customerList) {
            Customer v3Customer = new Customer();
            v3Customer.setId(v1Customer.getId());
            v3Customer.setFirstName(v1Customer.getFirstName());
            v3Customer.setLastName(v1Customer.getLastName());
            v3Customer.setMarketingAllowed(v1Customer.getMarketingAllowed());
            //PHONE NUMBERS
            List<com.clover.sdk.v1.customer.PhoneNumber> v1Numbers = v1Customer.getPhoneNumbers();
            List<PhoneNumber> v3Numbers = convertPhoneNumbers(v1Numbers);
            v3Customer.setPhoneNumbers(v3Numbers);
            //EMAIL ADDRESSES
            List<com.clover.sdk.v1.customer.EmailAddress> v1EmailList = v1Customer.getEmailAddresses();
            List<EmailAddress> v3Emails = convertEmailAddresses(v1EmailList);
            v3Customer.setEmailAddresses(v3Emails);
            //ADDRESSES
            List<com.clover.sdk.v1.customer.Address> v1AddressList = v1Customer.getAddresses();
            List<Address> v3Addresses = convertAddresses(v1Customer.getAddresses());
            v3Customer.setAddresses(v3Addresses);
            v3Customers.add(v3Customer);
        }
        return v3Customers;
    }

    public static List<PhoneNumber> convertPhoneNumbers(List<com.clover.sdk.v1.customer.PhoneNumber> v1PhoneNumbers) {
        List<PhoneNumber> v3numbers = new ArrayList<>();
        for (com.clover.sdk.v1.customer.PhoneNumber v1Number : v1PhoneNumbers) {
            PhoneNumber v3Number = new PhoneNumber();
            v3Number.setId(v1Number.getId());
            v3Number.setPhoneNumber(v1Number.getPhoneNumber());
            v3numbers.add(v3Number);
        }
        return v3numbers;
    }

    public static List<EmailAddress> convertEmailAddresses(List<com.clover.sdk.v1.customer.EmailAddress> v1EmailAddresses) {
        List<EmailAddress> v3EmailAddresses = new ArrayList<>();
        for (com.clover.sdk.v1.customer.EmailAddress v1EmailAddress : v1EmailAddresses) {
            EmailAddress v3EmailAddress = new EmailAddress();
            v3EmailAddress.setId(v1EmailAddress.getId());
            v3EmailAddress.setEmailAddress(v1EmailAddress.getEmailAddress());
            v3EmailAddresses.add(v3EmailAddress);
        }
        return v3EmailAddresses;
    }

    public static List<Address> convertAddresses(List<com.clover.sdk.v1.customer.Address> v1AddressList) {
        List<Address> v3Addresses = new ArrayList<>();
        for (com.clover.sdk.v1.customer.Address v1Address : v1AddressList) {
            Address v3Address = new Address();
            v3Address.setId(v1Address.getId());
            //todo interesting.... v3Address.setCountry();
            v3Address.setAddress1(v1Address.getAddress1());
            v3Address.setAddress2(v1Address.getAddress2());
            v3Address.setAddress3(v1Address.getAddress3());
            v3Address.setCity(v1Address.getCity());
            v3Address.setState(v1Address.getState());
            v3Address.setZip(v1Address.getZip());
            v3Addresses.add(v3Address);
        }
        return v3Addresses;
    }
}
