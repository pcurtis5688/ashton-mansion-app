package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v3.employees.AccountRole;
import com.clover.sdk.v3.employees.Employee;

/**
 * Created by paul on 8/17/2016.
 */
public class EditEmployeeActivity extends AppCompatActivity {
    //ACTIVITY INSTANCE VARS
    private Context editEmployeeActivityContext;
    private Employee editEmployee;
    //FIELD DATA VARS
    private EditText employeeNameEditField;
    private EditText employeeNicknameEditField;
    private Spinner employeeRoleEditSpinner;
    private ArrayAdapter<String> employeeRoleAdapter;
    private EditText employeePINEditField;
    private EditText employeeEmailEditField;
    private ProgressDialog progressDialog;

    public void finalizeEmployeeModification(View view) {
        progressDialog.setMessage("Modifying Employee...");
        progressDialog.show();
        //SET THE EMPLOYEE OBJECT WITH THE NEW DATA FROM FIELDS
        editEmployee.setName(employeeNameEditField.getText().toString());
        editEmployee.setNickname(employeeNicknameEditField.getText().toString());
        String selectedRole = employeeRoleEditSpinner.getSelectedItem().toString();
        if (selectedRole.equalsIgnoreCase("employee")) {
            editEmployee.setRole(AccountRole.EMPLOYEE);
        } else if (selectedRole.equalsIgnoreCase("manager")) {
            editEmployee.setRole(AccountRole.MANAGER);
        } else if (selectedRole.equalsIgnoreCase("admin")) {
            editEmployee.setRole(AccountRole.ADMIN);
        } else {
            editEmployee.setRole(AccountRole.EMPLOYEE);
        }
        editEmployee.setPin(employeePINEditField.getText().toString());
        editEmployee.setEmail(employeeEmailEditField.getText().toString());
        modifyEmployeeInCloverDynamicsAndLocal(editEmployee);
    }

    private boolean modifyEmployeeInCloverDynamicsAndLocal(Employee modifiedEmployee) {
        boolean employeeSuccessfullyModified = false;
        //// TODO: 8/22/2016
        return employeeSuccessfullyModified;
    }

    public void cancelEditEmployee(View view) {
        finish();
    }

    /////////ACTIVITY FLOW METHODS ////////////////////////
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
        editEmployee = (Employee) extras.get("employee");
        employeeNameEditField.setText(editEmployee.getName());
        employeeNicknameEditField.setText(editEmployee.getNickname());
        //ROLE HANDLING
        String[] roleList = getResources().getStringArray(R.array.employee_roles_array);
        employeeRoleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleList);
        employeeRoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeRoleEditSpinner.setAdapter(employeeRoleAdapter);
        int correctSpinnerPosition = employeeRoleAdapter.getPosition(editEmployee.getRole().name());
        employeeRoleEditSpinner.setSelection(correctSpinnerPosition);
        //
        employeePINEditField.setText(editEmployee.getPin());
        employeeEmailEditField.setText(editEmployee.getEmail());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
