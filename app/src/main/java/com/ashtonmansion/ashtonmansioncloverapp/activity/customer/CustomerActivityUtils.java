package com.ashtonmansion.ashtonmansioncloverapp.activity.customer;

import com.clover.sdk.v3.customers.Customer;

/**
 * Created by paul on 8/23/2016.
 */
public class CustomerActivityUtils {

    public static String getCustomerFullName(Customer customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }
}
