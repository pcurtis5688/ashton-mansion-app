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
    //ACTIVITY FIELD VARS
    private EditText employeeName;
    private EditText employeeNickname;
    private Spinner employeeRoleSpinner;
    private EditText employeePIN;
    private EditText employeeEmail;
    private Context addEmployeeContext;

    //SUBMIT EMPLOYEE TO CLOVER, DYNAMICS, AND LOCAL
    public void finalizeEmployeeCreation(View view) {
        ///* CREATE V3 INSTANCE OF A CLOVER EMPLOYEE & SET VARS *///
        Employee newEmployee = createNewEmployeeAndSetData();
        ///////////* CLOVER INSERTION *//////////////////////////
        doBackgroundCloverDynamicsAndLocalInsertionTask(newEmployee);
    }

    private Employee createNewEmployeeAndSetData() {
        Employee newEmployee = new Employee();
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
        return newEmployee;
    }

    //TODO BELOW, IN CLOVER INSERT, FIGURE OUT THEIR VALIDATION
    private void doBackgroundCloverDynamicsAndLocalInsertionTask(final Employee newEmployee) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(addEmployeeContext);
            //RESULT FLAGS
            private boolean cloverInsertionSuccess;
            private boolean createEmployeeWSSuccess;
            private boolean createEmployeeLocalSuccess;

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

                ///////////* DYNAMICS INSERTION *////////////////////////
                //////ONLY CONTINUE IF CLOVER INSERT SUCCESSFUL//////
                if (cloverInsertionSuccess == true) {
                    EmployeeWebService employeeWebService = new EmployeeWebService();
                    createEmployeeWSSuccess = employeeWebService.createEmployeeServiceCall(newEmployee);

                    ///////////* LOCAL INSERTION *////////////////////////
                    ////ONLY CONTINUE IF DYNAMICS INSERT SUCCESSFUL??////
                    if (createEmployeeWSSuccess == true || createEmployeeWSSuccess == false) {
                        try {
                            EmployeeDAO employeeDAO = new EmployeeDAO(addEmployeeContext);
                            createEmployeeLocalSuccess = employeeDAO.addLocalEmployeeRecord(newEmployee);
                        } catch (Exception e) {
                            Log.e("Local Insert Err: ", e.getMessage());
                        }
                    }
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

    public void cancelAddEmployee(View view) {
        finish();
    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        addEmployeeContext = this;
        employeeName = (EditText) findViewById(R.id.add_employee_name);
        employeeNickname = (EditText) findViewById(R.id.add_employee_nickname);
        employeeRoleSpinner = (Spinner) findViewById(R.id.add_employee_role_spinner);
        employeePIN = (EditText) findViewById(R.id.add_employee_pin);
        employeeEmail = (EditText) findViewById(R.id.add_employee_email);

        employeeRoleSpinner.setSelection(1);
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
