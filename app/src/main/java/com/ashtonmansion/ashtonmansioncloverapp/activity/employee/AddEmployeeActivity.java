package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.employeews.EmployeeWebService;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.AccountRole;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

public class AddEmployeeActivity extends AppCompatActivity {
    //SERVICE VARS
    private Account mAcct;
    private EmployeeConnector employeeConnector;
    private Employee newEmployee;
    //ACTIVITY FIELD VARS
    private EditText employeeName;
    private EditText employeeNickname;
    private Spinner employeeRoleSpinner;
    private EditText employeePIN;
    private EditText employeeEmail;
    private Context addEmployeeContext;
    //RESULT FLAGS
    long newlyAssignedEmployeeID = 0;
    private boolean cloverInsertionSuccess;
    private boolean createEmployeeWSSuccess;

    //SUBMIT EMPLOYEE TO CLOVER, DYNAMICS, AND LOCAL
    public void finalizeEmployeeCreation(View view) {
        addEmployeeContext = this;

        ///* CREATE V3 INSTANCE OF A CLOVER EMPLOYEE & SET VARS *///
        createNewEmployeeAndSetData();

        ///////////* CLOVER INSERTION *//////////////////////////
        doBackgroundCloverDynamicsAndLocalInsertionTask();

        ///////////* OUTCOME HANDLING *//////////////////////////
        /////*todo ANYTHING HERE?*///////
    }

    private void createNewEmployeeAndSetData() {
        newEmployee = new Employee();
        newEmployee.setId("FIX"); //todo that <--
        newEmployee.setName(employeeName.getText().toString());
        newEmployee.setNickname(employeeNickname.getText().toString());
        String selectedRole = employeeRoleSpinner.getSelectedItem().toString();
        if (selectedRole.equalsIgnoreCase("employee")) {
            newEmployee.setRole(AccountRole.EMPLOYEE);
        } else if (selectedRole.equalsIgnoreCase("manager")) {
            newEmployee.setRole(AccountRole.MANAGER);
        } else if (selectedRole.equalsIgnoreCase("admin")) {
            newEmployee.setRole(AccountRole.ADMIN);
        }
        newEmployee.setPin(employeePIN.getText().toString());
        newEmployee.setEmail(employeeEmail.getText().toString());
    }

    //TODO BELOW, IN CLOVER INSERT, FIGURE OUT THEIR VALIDATION
    private void doBackgroundCloverDynamicsAndLocalInsertionTask() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(addEmployeeContext);

            @Override
            protected void onPreExecute() {
                progress.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                ///////////* CLOVER INSERTION *////////////////////////
                Account merAcct = CloverAccount.getAccount(addEmployeeContext);
                EmployeeConnector empConn = new EmployeeConnector(addEmployeeContext, merAcct, null);
                Employee returnedEmployee = new Employee();
                String newEmployeeID = "";
                try {
                    returnedEmployee = empConn.createEmployee(newEmployee);
                    cloverInsertionSuccess = true;
                } catch (RemoteException | ServiceException | ClientException | BindingException e) {
                    Log.e("Clover Exception: ", e.getMessage());
                    cloverInsertionSuccess = false;
                } catch (Exception e2) {
                    Log.e("Generic Exception: ", e2.getMessage());
                    cloverInsertionSuccess = false;
                } finally {
                    newEmployeeID = returnedEmployee.getId();
                    newEmployee.setId(newEmployeeID);
                    empConn.disconnect();
                    Log.i("Clover InsertSuccess: ", "" + cloverInsertionSuccess);
                }

                //////ONLY CONTINUE IF CLOVER INSERT SUCCESSFUL//////
                if (cloverInsertionSuccess == true) {
                    ///////////* DYNAMICS INSERTION *////////////////////////
                    EmployeeWebService employeeWebService = new EmployeeWebService();
                    createEmployeeWSSuccess = employeeWebService.createEmployeeServiceCall(newEmployee);
                    Log.e("EmpSuccess added n BG", "" + createEmployeeWSSuccess);

                    ////ONLY CONTINUE IF DYNAMICS INSERT SUCCESSFUL??////
                    //     if (createEmployeeWSSuccess == true) {
                    ///////////* LOCAL INSERTION *////////////////////////
                    try {
                        EmployeeDAO employeeDAO = new EmployeeDAO(addEmployeeContext);
                        newlyAssignedEmployeeID = employeeDAO.addLocalEmployeeRecord(newEmployee);
                    } catch (Exception e) {
                        Log.e("Local Insert Err: ", e.getMessage());
                    }
                    if (newlyAssignedEmployeeID == -1) {
                        Log.e("Local Insert Err: ", Long.toString(newlyAssignedEmployeeID));
                    } else {
                        Log.i("New LOCAL Employee ID: ", "" + newlyAssignedEmployeeID);
                    }
                    //   } else {
                    //        Log.e("Break;Dynamics Fail: ", "See exception");
                    //   }
                } else {
                    Log.e("Break;CloverFail: ", "See exception");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                progress.dismiss();
                finish();
            }
        }.execute();
    }

    //////*BELOW METHODS BASICALLY COMPLETE *///////////////
    public void cancelAddEmployee(View view) {
        finish();
    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        employeeName = (EditText) findViewById(R.id.add_employee_name);
        employeeNickname = (EditText) findViewById(R.id.add_employee_nickname);
        employeeRoleSpinner = (Spinner) findViewById(R.id.add_employee_role_spinner);
        employeePIN = (EditText) findViewById(R.id.add_employee_pin);
        employeeEmail = (EditText) findViewById(R.id.add_employee_email);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
