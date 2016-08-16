package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
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
    //FLAGS
    private boolean dynamicsAddSuccess;

    //SUBMIT EMPLOYEE TO BOTH CLOVER AND DYNAMICS FOR ADDITION
    public void finalizeEmployeeCreation(View view) {
        long newlyAssignedEmployeeID = -2;
        Employee cloverEmployeeReturned = null;

        //* CREATE V3 INSTANCE OF A CLOVER EMPLOYEE & SET VARS *//////
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

        ///////////* CLOVER INSERTION *//////////////////////////
        insertEmployeeInClover();

        ///////////* DYNAMICS INSERTION *////////////////////////
        EmployeeDAO employeeDAO = new EmployeeDAO(this);
        dynamicsAddSuccess = employeeDAO.insertEmployeeDynamics(newEmployee);

        ///////////* LOCAL INSERTION *////////////////////////
        newlyAssignedEmployeeID = employeeDAO.addLocalEmployeeRecord(newEmployee);

        ///////////* OUTCOME HANDLING *////////////////////////
        //TODO CLEAN THIS WHOLE SECTION UP
        if (newlyAssignedEmployeeID == -1) {
            Log.e("Local Return Code: ", Long.toString(newlyAssignedEmployeeID));
        } else if (newlyAssignedEmployeeID == -2) {
            Log.e("Local Return Code: ", Long.toString(newlyAssignedEmployeeID));
        } else {
            Log.i("New Employee ID: ", "" + newlyAssignedEmployeeID);
        }
        Log.e("Dynamics Record fail: ", "" + dynamicsAddSuccess);
        Log.e("CloverEmpCreatefail ", "" + (cloverEmployeeReturned == null));
    }

    private void insertEmployeeInClover() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Account merAcct = CloverAccount.getAccount(addEmployeeContext);
                EmployeeConnector empConn = new EmployeeConnector(addEmployeeContext, merAcct, null);

                try {
                    empConn.createEmployee(newEmployee);
                } catch (RemoteException e2) {
                    Log.e("Remote EXC: ", e2.getMessage());
                } catch (ServiceException e3) {
                    Log.e("ServiceException EXC: ", e3.getMessage());
                } catch (ClientException e4) {
                    Log.e("ClientException EXC: ", e4.getMessage());
                } catch (BindingException e5) {
                    Log.e("BindingException EXC: ", e5.getMessage());
                } catch (Exception e6) {
                    Log.e("Generic Exception: ", e6.getMessage());
                } finally {
                    empConn.disconnect();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }

            //TODO CHECK HERE IF SUCCESS INSERT ALSO INSERT TO CLOVER?
            //TODO also should I create common classes for these types of methods related to clover accounts
        }.execute();
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

        addEmployeeContext = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //////*BELOW METHODS BASICALLY COMPLETE *///////////////
    public void cancelAddEmployee(View view) {
        finish();
    }
}
