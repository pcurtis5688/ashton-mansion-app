package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.employeews.EmployeeWebService;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.AccountRole;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

/**
 * Created by paul on 8/17/2016.
 */
public class EditEmployeeActivity extends AppCompatActivity {
    //ACTIVITY INSTANCE VARS
    private Context editEmployeeActivityContext;
    private Employee passedEmployeeInstance;
    private Employee modifiedEmployeeInstance;
    private String employeeID;
    //CLOVER SERVICE VARS
    private Account mAcct;
    private EmployeeConnector employeeConnector;
    //FIELD DATA VARS
    private EditText employeeNameEditField;
    private EditText employeeNicknameEditField;
    private Spinner employeeRoleEditSpinner;
    private EditText employeePINEditField;
    private EditText employeeEmailEditField;

    public void beginEmployeeModification(View view) {
        //SET THE EMPLOYEE OBJECT WITH THE NEW DATA FROM FIELDS
        setModifiedEmployeeDataFromFields();
        //MODIFY THE 3 EMPLOYEE LOCATIONS
        modifyEmployeeInCloverDynamicsAndLocal(modifiedEmployeeInstance);
    }

    private void setModifiedEmployeeDataFromFields() {
        modifiedEmployeeInstance = new Employee();
        modifiedEmployeeInstance.setName(employeeNameEditField.getText().toString());
        modifiedEmployeeInstance.setNickname(employeeNicknameEditField.getText().toString());
        String selectedRole = employeeRoleEditSpinner.getSelectedItem().toString();
        if (selectedRole.equalsIgnoreCase("employee")) {
            modifiedEmployeeInstance.setRole(AccountRole.EMPLOYEE);
        } else if (selectedRole.equalsIgnoreCase("manager")) {
            modifiedEmployeeInstance.setRole(AccountRole.MANAGER);
        } else if (selectedRole.equalsIgnoreCase("admin")) {
            modifiedEmployeeInstance.setRole(AccountRole.ADMIN);
        }
        modifiedEmployeeInstance.setPin(employeePINEditField.getText().toString());
        modifiedEmployeeInstance.setEmail(employeeEmailEditField.getText().toString());

    }


    private void modifyEmployeeInCloverDynamicsAndLocal(final Employee modifiedEmployee) {
        new AsyncTask<Void, Void, Void>() {
            private ProgressDialog progressDialog = new ProgressDialog(editEmployeeActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Modifying Employee...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                getMerchantAccount();
                connectEmployeeConn();
                try {
                    Employee testEmp = employeeConnector.getEmployee(employeeID);
                    testEmp.setName(modifiedEmployee.getName());
                    testEmp.setNickname(modifiedEmployee.getNickname());
                    testEmp.setRole(modifiedEmployee.getRole());
                    testEmp.setPin(modifiedEmployee.getPin());
                    testEmp.setEmail(modifiedEmployee.getEmail());
                    //CLOVER EMPLOYEE CHANGES
                    employeeConnector.updateEmployee(testEmp);
                    //WEB SERVICE MODIFICATION CALL
                    EmployeeWebService employeeWebService = new EmployeeWebService();
                    employeeWebService.modifyEmployeeServiceCall(modifiedEmployee);
                    //todo local
                } catch (RemoteException | BindingException | ServiceException | ClientException e1) {
                    Log.e("Clover Excpt: ", e1.getMessage());
                } catch (Exception e2) {
                    Log.e("Generic Excpt: ", e2.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                disconnectEmployeeConn();
                progressDialog.dismiss();
                finish();
            }
        }.execute();
    }

    public void cancelEditEmployee(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        editEmployeeActivityContext = this;
        employeeNameEditField = (EditText) findViewById(R.id.edit_employee_name_field);
        employeeNicknameEditField = (EditText) findViewById(R.id.edit_employee_nickname_field);
        employeeRoleEditSpinner = (Spinner) findViewById(R.id.edit_employee_role_spinner);
        employeePINEditField = (EditText) findViewById(R.id.edit_employee_pin_field);
        employeeEmailEditField = (EditText) findViewById(R.id.edit_employee_email_field);
        //SET THE EMPLOYEE'S CURRENT DATA INTO THE FIELDS
        Bundle extras = getIntent().getExtras();
        passedEmployeeInstance = (Employee) extras.get("employee");
        employeeID = passedEmployeeInstance.getId();
        employeeNameEditField.setText(passedEmployeeInstance.getName());
        employeeNicknameEditField.setText(passedEmployeeInstance.getNickname());
        //ROLE HANDLING
        String[] roleList = getResources().getStringArray(R.array.employee_roles_array);
        ArrayAdapter<String> employeeRoleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleList);
        employeeRoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeRoleEditSpinner.setAdapter(employeeRoleAdapter);
        int correctSpinnerPosition = employeeRoleAdapter.getPosition(passedEmployeeInstance.getRole().name());
        employeeRoleEditSpinner.setSelection(correctSpinnerPosition);
        //
        employeePINEditField.setText(passedEmployeeInstance.getPin());
        employeeEmailEditField.setText(passedEmployeeInstance.getEmail());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getMerchantAccount() {
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);
            //Break if Clover Account unreachable
            if (mAcct == null) {
                finish();
            }
        }
    }

    private void connectEmployeeConn() {
        disconnectEmployeeConn();
        if (mAcct != null) {
            employeeConnector = new EmployeeConnector(this, mAcct, null);
            employeeConnector.connect();
        }
    }

    private void disconnectEmployeeConn() {
        if (employeeConnector != null) {
            employeeConnector.disconnect();
            employeeConnector = null;
        }
    }
}
