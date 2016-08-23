package com.ashtonmansion.ashtonmansioncloverapp.activity.appointment;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansioncloverapp.utility.GlobalUtils;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.appointmentws.AppointmentWebServices;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddAppointmentActivity extends AppCompatActivity {
    //ACTIVITY VARS
    private Context addApptContext;
    ///////////SERVICE VARS
    private AppointmentDAO apptDAO;
    private Appointment appointment;
    private boolean addApptWebServiceSuccess;
    /////////// FIELD VARS
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText durationText;
    private EditText customerCodeText;
    private Spinner alertTypeSpinner;
    private EditText itemCodeText;
    private EditText apptNoteText;
    private Spinner emp1Spinner;
    private EditText emp2Text;
    private Spinner apptConfirmStatusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        addApptContext = this;

        datePicker = (DatePicker) findViewById(R.id.appt_date);
        timePicker = (TimePicker) findViewById(R.id.appt_start_time);
        durationText = (EditText) findViewById(R.id.appt_duration);
        customerCodeText = (EditText) findViewById(R.id.appt_customer_code);
        alertTypeSpinner = (Spinner) findViewById(R.id.appt_alert_type_spinner);
        itemCodeText = (EditText) findViewById(R.id.appt_item_code);
        apptNoteText = (EditText) findViewById(R.id.appt_note);
        emp1Spinner = (Spinner) findViewById(R.id.appt_emp_1_assigned_spinner);
        emp2Text = (EditText) findViewById(R.id.appt_emp_2_assigned);
        apptConfirmStatusSpinner = (Spinner) findViewById(R.id.appt_confirm_status);
        emp2Text = (EditText) findViewById(R.id.appt_emp_2_assigned);

        populateEmployeeSpinnerDropdowns();
    }

    private void populateEmployeeSpinnerDropdowns() {
        List<Employee> employeeList = getCloverEmployeeList();
        List<String> employeeNames = new ArrayList<>();
    //        for (Employee employee : employeeList) {
    //            employeeNames.add(employee.getName());
    //        }
    //        ArrayAdapter<String> employeeNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeNames);
         //     employeeNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //     emp1Spinner.setAdapter(employeeNameAdapter);
        //        emp1Spinner.setSelection(0);
    }

    public void submitAddAppointment(View view) {
        apptDAO = new AppointmentDAO(this);
        appointment = new Appointment();

        //HANDLE DATE FROM DATEPICKER
        int dateMonth = datePicker.getMonth();
        int dateDay = datePicker.getDayOfMonth();
        int dateYear = datePicker.getYear();
        String formattedDate = GlobalUtils.formatDate(dateMonth, dateDay, dateYear);
        //todo handle this date...
        //HANDLE TIME FROM TIMEPICKER
        String formattedTime = GlobalUtils.formatTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());

        //CONSTRUCT THE APPOINTMENT OBJECT
        appointment.set_date(formattedDate);
        appointment.set_start_time(formattedTime);
        appointment.set_duration(durationText.getText().toString());
        appointment.set_customer_code(customerCodeText.getText().toString());
        appointment.set_alert_type(alertTypeSpinner.getSelectedItem().toString());
        appointment.set_item_code(itemCodeText.getText().toString());
        appointment.set_note(apptNoteText.getText().toString());
        appointment.set_employee_code_1(emp1Spinner.getSelectedItem().toString());
        appointment.set_employee_code_2(emp1Spinner.getSelectedItem().toString());
        appointment.set_confirm_status(apptConfirmStatusSpinner.getSelectedItem().toString());
        long returnedID = apptDAO.addAppointment(appointment);
        appointment.set_id(Long.toString(returnedID));
        ////////////////////LOCAL DB UPDATED, NOW CALL DYNAMICS WS
        callApptWSInBackground(appointment);
    }

    private void callApptWSInBackground(final Appointment finalAppointment) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(addApptContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setMessage("Adding Appointment...");
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                AppointmentWebServices apptWebService = new AppointmentWebServices();
                addApptWebServiceSuccess = apptWebService.addAppointmentViaWS(finalAppointment);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                closeOutActivity();
                progress.dismiss();
            }
        }.execute();
    }

    private void closeOutActivity() {
        if (addApptWebServiceSuccess) {
            Log.i("Web Service: ", "SUCCESS");
        } else {
            Log.e("Web Service: ", "FAILURE");
        }
        apptDAO.close();
        finish();
    }

    public void cancelAddAppointment(View view) {
        finish();
    }

    public List<Employee> getCloverEmployeeList() {
        List<Employee> employeeList = null;
        try {
            employeeList = new AsyncTask<Void, Void, List<Employee>>() {
                @Override
                protected List<Employee> doInBackground(Void... params) {
                    List<Employee> employees = new ArrayList<>();
                    try {
                        EmployeeConnector employeeConnector = getAnEmployeeConnection();
                        employees = employeeConnector.getEmployees();
                    } catch (RemoteException | ClientException | ServiceException | BindingException e1) {
                        Log.e("Clover excptn: ", e1.getMessage());
                    }
                    return employees;
                }

                @Override
                protected void onPostExecute(List<Employee> result) {
                    super.onPostExecute(result);
                }
            }.get();
        } catch (InterruptedException | ExecutionException e1) {
            Log.e("Excptn: ", "" + e1.getClass().getName() + ":" + e1.getMessage());
        }
        return employeeList;
    }


    private EmployeeConnector getAnEmployeeConnection() {
        //Retrieve the Clover merchant account
        Account mAcct = CloverAccount.getAccount(addApptContext);
        EmployeeConnector employeeConnector = null;
        if (mAcct != null) {
            employeeConnector = new EmployeeConnector(addApptContext, mAcct, null);
        }
        return employeeConnector;
    }
}
