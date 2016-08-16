package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.activity.customer.EditCustomerActivity;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.List;

public class EmployeesActivity extends AppCompatActivity {
    //STATIC LOCAL VARS
    private Account mAcct;
    private EmployeeConnector mEmpConn;
    private List<Employee> employeeList;
    private EmployeeDAO employeeDAO;
    private Context context = this;

    //Method to open the add employee activity/screen
    public void displayAddEmployee(View view) {
        Intent addEmployeeIntent = new Intent(this, AddEmployeeActivity.class);
        startActivity(addEmployeeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        employeeDAO = new EmployeeDAO(this);
    }

    @Override
    protected void onPause() {
        disconnectEmployees();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve the Clover merchant account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            //Return if the Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }

        //Connect emp connector
        connectEmployees();
        getEmployeeList();
        populateLocalEmplTableTesting();
    }

    private void connectEmployees() {
        disconnectEmployees();
        if (mAcct != null) {

            mEmpConn = new EmployeeConnector(this, mAcct, null);
            mEmpConn.connect();
        }
    }

    private void disconnectEmployees() {
        if (mEmpConn != null) {
            mEmpConn.disconnect();
            mEmpConn = null;
        }
    }

    private void getEmployeeList() {
        new AsyncTask<Void, Void, Employee>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //can show progress bar here
            }

            @Override
            protected Employee doInBackground(Void... params) {
                try {
                    List<Employee> employees = mEmpConn.getEmployees();
                    employeeList = employees;
                    return employees.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Employee employee) {

                populateEmployeeTable();
            }
        }.execute();
    }

    private void populateEmployeeTable() {
        //Clear the employee table if has content
        TableLayout employeeTable = (TableLayout) findViewById(R.id.employee_table);
        employeeTable.removeAllViews();

        TableRow workingEmployeeRow;
        TextView empIdTextview;
        TextView empNameTextview;
        TextView empNicknameTextView;
        TextView empEmailTextview;
        TextView empRoleTextview;
        //create the header row for the employee table
        //TODO this
        createEmployeeTableHeaderRow();

        if (employeeList != null) {
            for (Employee currentEmp : employeeList) {
                //Create new row to add to table
                workingEmployeeRow = new TableRow(this);
                //Create new views to populate data
                empIdTextview = new TextView(this);
                empNameTextview = new TextView(this);
                empNicknameTextView = new TextView(this);
                empEmailTextview = new TextView(this);
                empRoleTextview = new TextView(this);
                //Set the new data for the new row
                empIdTextview.setText(currentEmp.getId());
                empNameTextview.setText(currentEmp.getName());
                empNicknameTextView.setText(currentEmp.getNickname());
                empEmailTextview.setText(currentEmp.getEmail());
                empRoleTextview.setText(currentEmp.getRole().name());
                //Add the new data to the new row
                workingEmployeeRow.addView(empIdTextview);
                workingEmployeeRow.addView(empNameTextview);
                workingEmployeeRow.addView(empNicknameTextView);
                workingEmployeeRow.addView(empEmailTextview);
                workingEmployeeRow.addView(empRoleTextview);
                //Add the new row to the table
                employeeTable.addView(workingEmployeeRow);
            }
        }
    }

    private void createEmployeeTableHeaderRow() {
        //Todo this
    }

    private List<Employee> employees;

    private void populateLocalEmplTableTesting() {
        employees = new EmployeeDAO(this).getLocalEmployeeRecords();

        TableLayout localTable = (TableLayout) findViewById(R.id.local_employee_table);
        localTable.removeAllViews();

        TableRow headerRow = new TableRow(this);
        TextView headerText = new TextView(this);
        headerText.setText("TEST");
        headerRow.addView(headerText);
        localTable.addView(headerRow);
        for (final Employee employee : employees) {
            TableRow empRow = new TableRow(this);

            TextView empID = new TextView(this);
            TextView empName = new TextView(this);
            TextView empNickname = new TextView(this);
            TextView empRole = new TextView(this);
            TextView empPin = new TextView(this);
            TextView empEmail = new TextView(this);

            Button deleteEmployeeButton = new Button(this);
            deleteEmployeeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedEmployeeDelete(employee);
                }
            });

            empID.setText(employee.getId());
            empName.setText(employee.getName());
            empNickname.setText(employee.getNickname());
            empRole.setText(employee.getRole().toString());
            empPin.setText(employee.getPin());
            empEmail.setText(employee.getEmail());
            deleteEmployeeButton.setText("DELETE");

            empRow.addView(empID);
            empRow.addView(empName);
            empRow.addView(empNickname);
            empRow.addView(empRole);
            empRow.addView(empPin);
            empRow.addView(empEmail);
            empRow.addView(deleteEmployeeButton);

            localTable.addView(empRow);
        }
    }


    public void clickedEmployeeDelete(final Employee employee) {
        //TODO COMPLETE THIS .
        //Intent editCustomerIntent = new Intent(this, EditCustomerActivity.class);
        //editCustomerIntent.putExtra("customer", customer);
        //startActivity(editCustomerIntent);
        new AlertDialog.Builder(this)
                .setTitle("Delete Employee?")
                .setMessage("Delete Employee " + employee.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String confirmationToastString = "Employee " + employee.getName() + " Deleted!";
                        deleteEmployee(employee);
                        Toast.makeText(EmployeesActivity.this, confirmationToastString, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private boolean deleteEmployee(Employee employee) {
        boolean employeeSuccessfullyDeleted = false;

        
        /////LOCAL DELETION
        EmployeeDAO employeeDAO = new EmployeeDAO(this);
        //TODO delete methods
        return employeeSuccessfullyDeleted;
    }
}
