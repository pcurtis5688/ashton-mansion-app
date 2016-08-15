package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
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

    //SUBMIT EMPLOYEE TO BOTH CLOVER AND DYNAMICS FOR ADDITION
    public void finalizeEmployeeCreation(View view) {
        long newlyAssignedEmployeeID = -2;
        Employee cloverEmployeeReturned;
        boolean successfulDynamicsEmployeeInsertion = false;
        Employee cloverReturnedEmployoee = new Employee();

        //CREATE V3 INSTANCE OF A CLOVER EMPLOYEE
        newEmployee = new Employee();
        newEmployee.setName(employeeName.getText().toString());
        newEmployee.setNickname(employeeNickname.getText().toString());
        String test = employeeRoleSpinner.getSelectedItem().toString();
//        newEmployee.setRole();
        newEmployee.setPin(employeePIN.getText().toString());
        newEmployee.setEmail(employeeEmail.getText().toString());

        EmployeeDAO employeeDAO = new EmployeeDAO(this);
        String returnedCloverEmployeeID = (employeeDAO.createEmployeeInClover(newEmployee, this)).getId();
        newEmployee.setId(returnedCloverEmployeeID);
        successfulDynamicsEmployeeInsertion = employeeDAO.insertEmployeeDynamics(newEmployee);
        // todo insertion flow work after testing complete }
        newlyAssignedEmployeeID = employeeDAO.addLocalEmployeeRecord(newEmployee);
        if (newlyAssignedEmployeeID == -1) {
            Log.e("Local Return Code: ", Long.toString(newlyAssignedEmployeeID));
            //todo handle error case?
        } else if (newlyAssignedEmployeeID == -2) {
            Log.e("Local Return Code: ", Long.toString(newlyAssignedEmployeeID));
            //todo handle error case?
        } else {
            Log.i("New Employee ID: ", "" + newlyAssignedEmployeeID);
        }
        Log.e("Dynamics Record fail: ", "" + successfulDynamicsEmployeeInsertion);
        Log.e("CloverEmpCreatefail ", "" + (cloverEmployeeReturned = null));
    }

    //RETURN WHEN CANCEL BUTTON PRESSED
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
