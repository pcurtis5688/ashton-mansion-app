package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.employeews.EmployeeWebService;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.List;

public class EmployeesActivity extends AppCompatActivity {
    //STATIC LOCAL VARS
    private Account mAcct;
    private EmployeeConnector mEmpConn;
    private List<Employee> employeeList;
    private EmployeeDAO employeeDAO;
    private Context employeesActivityContext;
    private TableLayout employeeTable;

    public void displayAddEmployee(View view) {
        Intent addEmployeeIntent = new Intent(this, AddEmployeeActivity.class);
        startActivity(addEmployeeIntent);
    }

    private void getEmployeeListAndPopulateTable() {
        new AsyncTask<Void, Void, Employee>() {
            ProgressDialog progressDialog = new ProgressDialog(employeesActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Loading Employees...");
                progressDialog.show();
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
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void createEmployeeTableHeaderRow() {
        TableRow employeeTableHeaderRow = new TableRow(employeesActivityContext);
        TextView employeeTableIDHeader = new TextView(employeesActivityContext);
        TextView employeeTableNameHeader = new TextView(employeesActivityContext);
        TextView employeeTableEmailHeader = new TextView(employeesActivityContext);
        TextView employeeTableRoleHeader = new TextView(employeesActivityContext);

        employeeTableIDHeader.setText(getResources().getString(R.string.employee_table_ID_header));
        employeeTableNameHeader.setText(getResources().getString(R.string.employee_table_name_header));
        employeeTableEmailHeader.setText(getResources().getString(R.string.employee_table_email_header));
        employeeTableRoleHeader.setText(getResources().getString(R.string.employee_table_role_header));

        employeeTableHeaderRow.addView(employeeTableIDHeader);
        employeeTableHeaderRow.addView(employeeTableNameHeader);
        employeeTableHeaderRow.addView(employeeTableEmailHeader);
        employeeTableHeaderRow.addView(employeeTableRoleHeader);
        employeeTable.addView(employeeTableHeaderRow);
    }

    private void populateEmployeeTable() {
        //Clear the employee table if has content
        employeeTable.removeAllViews();
        //CREATE THE TABLE HEADER
        createEmployeeTableHeaderRow();

        TableRow workingEmployeeRow;
        TextView empIdTextview;
        TextView empNameTextview;
        TextView empEmailTextview;
        TextView empRoleTextview;

        if (employeeList != null) {
            for (final Employee currentEmp : employeeList) {
                //Create new row to add to table
                workingEmployeeRow = new TableRow(this);
                //Create new views to populate data
                empIdTextview = new TextView(this);
                empNameTextview = new TextView(this);
                empEmailTextview = new TextView(this);
                empRoleTextview = new TextView(this);

                //HANDLE EDIT CLICK LISTENERS
                Button editEmployeeButton = new Button(employeesActivityContext);
                editEmployeeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editThisEmployee(currentEmp);
                    }
                });               //TODO USE STRING RESOURCE
                editEmployeeButton.setText("Edit");
                //HANDLE THE DELETE BUTTON


                Button deleteEmployeeButton = new Button(this);
                deleteEmployeeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteThisEmployee(currentEmp);
                    }
                });
                deleteEmployeeButton.setText("Delete");

                //ADD DATA TO THE COMPONENTS
                empIdTextview.setText(currentEmp.getId());
                empNameTextview.setText(currentEmp.getName());
                empEmailTextview.setText(currentEmp.getEmail());
                empRoleTextview.setText(currentEmp.getRole().name());
                //TODO FIX THIS ACCESS STRINGS.XML

                //ADD COMPONENTS TO THE NEW ROW
                workingEmployeeRow.addView(empIdTextview);
                workingEmployeeRow.addView(empNameTextview);
                workingEmployeeRow.addView(empEmailTextview);
                workingEmployeeRow.addView(empRoleTextview);
                workingEmployeeRow.addView(editEmployeeButton);
                workingEmployeeRow.addView(deleteEmployeeButton);
                //Add the new row to the table
                employeeTable.addView(workingEmployeeRow);
            }
        }
    }

    public void editThisEmployee(final Employee employee) {
        Intent editEmployeeIntent = new Intent(this, EditEmployeeActivity.class);
        editEmployeeIntent.putExtra("employee", employee);
        startActivity(editEmployeeIntent);
    }

    public void deleteThisEmployee(final Employee employee) {
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

    private void deleteEmployee(final Employee employee) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(employeesActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setMessage("Deleting...");
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                boolean employeeSuccessfullyDeletedClover;
                boolean employeeSuccessfullyDeletedDynamics = false;
                boolean employeeSuccessfullyDeletedLocal = false;

                try {
                    mEmpConn.deleteEmployee(employee.getId());
                    employeeSuccessfullyDeletedClover = true;
                } catch (ServiceException | BindingException | ClientException | RemoteException e) {
                    employeeSuccessfullyDeletedClover = false;
                    Log.e("Clover Exception: ", e.getMessage());
                } catch (Exception e2) {
                    employeeSuccessfullyDeletedClover = false;
                    Log.e("Generic Exception: ", e2.getMessage());
                }

                /////IF DELETED SUCCESSFULLY IN CLOVER
                if (employeeSuccessfullyDeletedClover) {
                    /////DYNAMICS DELETION
                    //TODO DYNAMICS DELETE METHOD
                    EmployeeWebService employeeWebService = new EmployeeWebService();
                    employeeSuccessfullyDeletedDynamics = employeeWebService.deleteEmployeeServiceCall(employee.getId());
                    if (employeeSuccessfullyDeletedDynamics) {
                        employeeDAO = new EmployeeDAO(employeesActivityContext);
                        employeeSuccessfullyDeletedLocal = employeeDAO.deleteLocalEmployeeRecord(employee);
                        if (!employeeSuccessfullyDeletedLocal) {
                            Log.e("Local deletion err: ", "See Above...");
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                progress.dismiss();
                getEmployeeListAndPopulateTable();
                //todo below is for use with testing
                //populateLocalEmplTableTesting();
            }
        }.execute();
    }

    private void getMerchantAccount() {
        //Retrieve the Clover merchant account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            //Return if the Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        employeesActivityContext = this;
        employeeDAO = new EmployeeDAO(this);
        employeeTable = (TableLayout) findViewById(R.id.employee_table);
    }

    @Override
    protected void onPause() {
        disconnectEmployees();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMerchantAccount();
        //Connect emp connector
        connectEmployees();
        getEmployeeListAndPopulateTable();
        //populateLocalEmplTableTesting();
    }
}
